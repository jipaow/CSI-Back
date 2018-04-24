package co.simplon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;
/**
 * 
 * @author jean philippe
 * cette classe expose les methodes d'interaction avec la base de donnée
 *
 */
@Repository
public class jdbcSuspectDAO implements SuspectDAO {
	
	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	Connection connection = null;
	
	@Autowired
	public jdbcSuspectDAO(JdbcTemplate jdbcTemplate) {
		this.datasource = jdbcTemplate.getDataSource();
	}

	@Override
	public DataSuspect listSuspect() throws Exception {
		Suspect suspect;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataSuspect dataSuspect = new DataSuspect();
		
		try {
			// Prepare la requete sql
			sql = "SELECT DISTINCT * FROM humain INNER JOIN personne_impliquee ON personne_impliquee.humain_id = humain.id_humain WHERE personne_impliquee.status_id = 2 GROUP BY id_humain";
			pstmt = datasource.getConnection().prepareStatement(sql);
			
			// Run la requete
			rs = pstmt.executeQuery();
			
			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				suspect = getSuspectFromResultSet(rs);
				dataSuspect.getData().add(suspect);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return dataSuspect;
	}

	@Override
	public DataSuspect getSuspect(int id) throws Exception {
		Suspect suspect;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataSuspect dataSuspect = new DataSuspect();
		
		try {
			// Prepare la requete sql
			sql = "SELECT DISTINCT * FROM humain INNER JOIN personne_impliquee ON personne_impliquee.humain_id = humain.id_humain WHERE personne_impliquee.status_id = 2 AND humain.id_humain = ? GROUP BY id_humain";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			// Run la requete
			rs = pstmt.executeQuery();
			
			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				suspect = getSuspectFromResultSet(rs);
				dataSuspect.getData().add(suspect);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return dataSuspect;
	}
	
	

	@Override
	public Suspect insertSuspect(Suspect suspect) throws Exception {
		PreparedStatement pstmt = null;
		Suspect result = null;
		int i = 0;
		
		try {
			// Prepare the SQL query
			String sql1 = "INSERT INTO humain ( nom, prenom, genre, date_naissance, nationalite, taille, poids, adresse, signe_distinctif,photo, empreinte, casier,nombre_condamnation, type_condamnation ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			pstmt = datasource.getConnection().prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(++i, suspect.getNom());
			pstmt.setString(++i, suspect.getPrenom());
			pstmt.setString(++i, suspect.getGenre());
			pstmt.setDate(++i, suspect.getDateNaissance());
			pstmt.setString(++i, suspect.getNationalite());
			pstmt.setFloat(++i, suspect.getTaille());
			pstmt.setInt(++i, suspect.getPoids());
			pstmt.setString(++i, suspect.getAdresseConnues());
			pstmt.setString(++i, suspect.getSigneDistinctif());
			pstmt.setString(++i, suspect.getPhoto());
			pstmt.setString(++i, suspect.getEmpreinte());
			pstmt.setBoolean(++i, suspect.isCasierJudiciaire());
			pstmt.setInt(++i, suspect.getCondamnations());
			pstmt.setString(++i, suspect.getTypeCondamnation());

			
			// Log info
			logSQL(pstmt);
			
			// Run the the update query	
			pstmt.executeUpdate();
			
			result = suspect;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return result;
		
	}
	
	@Override
	public Suspect addSuspectToEnquete (Suspect suspect) throws Exception{
		PreparedStatement pstmt = null;
		Suspect result = null;
		int i = 0;
		
		try {
			String sql = "INSERT INTO personne_impliquee (humain_id, enquete_id,status_id) VALUES (?, ?, 2 )";
			pstmt = datasource.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(++i, suspect.getId());
			pstmt.setInt(++i, suspect.getNumEnquete());

			
			// Log info
		    logSQL(pstmt);
		    
		    pstmt.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return result;
	}
				

	@Override
	public Suspect updateSuspect(Suspect suspect) throws Exception {
		Suspect result = null;
		PreparedStatement pstmt = null;
		int i = 0;

		try {
			// Prepare the SQL query
			String sql = "UPDATE humain SET nom = ?, prenom = ?, genre = ?, date_naissance = ?, nationalite=? ,taille= ?, poids=?, adresse=?, signe_distinctif=?, photo=?, empreinte=?, casier=?, nombre_condamnation=?, type_condamnation=? WHERE id_humain=?";
            pstmt = datasource.getConnection().prepareStatement(sql);

            pstmt.setString(++i, suspect.getNom());
            pstmt.setString(++i, suspect.getPrenom());
            pstmt.setString(++i, suspect.getGenre());
            pstmt.setDate(++i, suspect.getDateNaissance());
            pstmt.setString(++i, suspect.getNationalite());
            pstmt.setFloat(++i, suspect.getTaille());
            pstmt.setInt(++i, suspect.getPoids());
            pstmt.setString(++i, suspect.getAdresseConnues());
            pstmt.setString(++i, suspect.getSigneDistinctif());
            pstmt.setString(++i, suspect.getPhoto());
            pstmt.setString(++i, suspect.getEmpreinte());
            pstmt.setBoolean(++i, suspect.isCasierJudiciaire());
            pstmt.setInt(++i, suspect.getCondamnations());
            pstmt.setString(++i, suspect.getTypeCondamnation());
            pstmt.setInt(++i, suspect.getId());
			
			
			// Log info
			logSQL(pstmt);
			
			// Run the the update query
			int resultCount = pstmt.executeUpdate();
			if(resultCount != 1)
				throw new Exception("Suspect non trouvé !");
			
			result = suspect;

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return result;
		
	}
	
	@Override
	public int verifSuspectExiste(Suspect suspect) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs;
		int i = 0;
		int resultSuspect = 0;
		
		try {
			// Prepare the SQL query
			String sql = "SELECT * FROM humain WHERE nom = ? AND prenom = ?";
            pstmt = datasource.getConnection().prepareStatement(sql);
            
            pstmt.setString(++i, suspect.getNom());
            pstmt.setString(++i, suspect.getPrenom());
         // Log info
         	logSQL(pstmt);

         // Run requete
         	rs = pstmt.executeQuery();
         			
         // gere les resultats de requete
         	if (rs.next())
         		resultSuspect = 1;
         	} catch (SQLException e) {
         			e.printStackTrace();
         			log.error("SQL Error !:" + pstmt.toString(), e);
         			throw e;
         	} finally {
         			pstmt.close();
         		}
         		return resultSuspect;
	}
	/***
	 * Cette methode set la valeur de chaque attribut de la classe suspect
	 * en recuperant les informations de la base de donnée via le ResultSet
	 * @param rs
	 * @return suspect
	 * @throws SQLException
	 */
	private Suspect getSuspectFromResultSet(ResultSet rs) throws SQLException {
		Suspect suspect = new Suspect();
		suspect.setId(rs.getInt("id_humain"));
		suspect.setNom(rs.getString("nom"));
		suspect.setPrenom(rs.getString("prenom"));
		suspect.setGenre(rs.getString("genre"));
		suspect.setDateNaissance( rs.getDate("date_naissance"));
		suspect.setNationalite(rs.getString("nationalite"));
		suspect.setTaille(rs.getFloat("taille"));
	    suspect.setPoids(rs.getInt("poids"));
		suspect.setAdresseConnues(rs.getString("adresse"));
		suspect.setSigneDistinctif(rs.getString("signe_distinctif"));
    	suspect.setPhoto(rs.getString("photo"));
		suspect.setEmpreinte(rs.getString("empreinte"));
		suspect.setCasierJudiciaire(rs.getBoolean("casier"));
		suspect.setCondamnations(rs.getInt("nombre_condamnation"));
		suspect.setTypeCondamnation(rs.getString("type_condamnation"));
	
		
		return suspect;	
	}
	
	private void logSQL(PreparedStatement pstmt) {
		String sql;
		
		if (pstmt == null)
			return;
		
		sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
		log.debug(sql);

	}



//	@SuppressWarnings("null")
//	@Override
//	@Transactional
//	public void archiverSuspect(int id) throws Exception {
//		Connection dbConnection = null;
//		PreparedStatement pstmtInsert = null;
//		PreparedStatement pstmtDelete1 = null;
//		PreparedStatement pstmtDelete2 = null;
//		int i = 0;
//		
//		
//		String insertSql = " INSERT INTO archive_personne_impliquee (id_archive_personne_impliquee, nom , prenom , date_naissance , genre , photo , date_deces , adresse , grade , competences , date_prise_service , actif , telephone , taille , poids , signe_distinctif , empreinte , casier , nombre_condamnation , type_condamnation , nationalite , statut )  VALUES ((SELECT id_humain FROM humain WHERE id_humain = ?), (SELECT nom FROM humain WHERE id_humain = ?), (SELECT prenom FROM humain WHERE id_humain = ?), (SELECT date_naissance FROM humain WHERE id_humain = ?), (SELECT genre FROM humain WHERE id_humain = ?), (SELECT photo FROM humain WHERE id_humain = ?), (SELECT date_deces FROM humain WHERE id_humain = ?), (SELECT adresse FROM humain WHERE id_humain = ?), (SELECT grade FROM humain WHERE id_humain = ?), (SELECT competences FROM humain WHERE id_humain = ?),  (SELECT date_prise_service FROM humain WHERE id_humain = ?), (SELECT actif FROM humain WHERE id_humain = ?), (SELECT telephone FROM humain WHERE id_humain = ?), (SELECT taille FROM humain WHERE id_humain = ?), (SELECT poids FROM humain WHERE id_humain = ?), (SELECT signe_distinctif FROM humain WHERE id_humain = ?), (SELECT empreinte FROM humain WHERE id_humain = ?), (SELECT casier FROM humain WHERE id_humain = ?), (SELECT nombre_condamnation FROM humain WHERE id_humain = ?), (SELECT type_condamnation FROM humain WHERE id_humain = ?), (SELECT nationalite FROM humain WHERE id_humain = ?), (SELECT status_id FROM personne_impliquee WHERE humain_id = ?))" ;
//		String deleteSql1 = " DELETE FROM personne_impliquee WHERE humain_id = ?";
//		String deleteSql2 = "DELETE FROM humain WHERE id_humain = ?";
//			
//		
//		
//		try {
//			//String sql = " START TRANSACTION ;  INSERT INTO archive_personne_impliquee (id_archive_personne_impliquee, nom , prenom , date_naissance , genre , photo , date_deces , adresse , grade , competences , date_prise_service , actif , telephone , taille , poids , signe_distinctif , empreinte , casier , nombre_condamnation , type_condamnation , nationalite , statut )  VALUES ((SELECT id_humain FROM humain WHERE id_humain = ?), (SELECT nom FROM humain WHERE id_humain = ?), (SELECT prenom FROM humain WHERE id_humain = ?), (SELECT date_naissance FROM humain WHERE id_humain = ?), (SELECT genre FROM humain WHERE id_humain = ?), (SELECT photo FROM humain WHERE id_humain = ?), (SELECT date_deces FROM humain WHERE id_humain = ?), (SELECT adresse FROM humain WHERE id_humain = ?), (SELECT grade FROM humain WHERE id_humain = ?), (SELECT competences FROM humain WHERE id_humain = ?),  (SELECT date_prise_service FROM humain WHERE id_humain = ?), (SELECT actif FROM humain WHERE id_humain = ?), (SELECT telephone FROM humain WHERE id_humain = ?), (SELECT taille FROM humain WHERE id_humain = ?), (SELECT poids FROM humain WHERE id_humain = ?), (SELECT signe_distinctif FROM humain WHERE id_humain = ?), (SELECT empreinte FROM humain WHERE id_humain = ?), (SELECT casier FROM humain WHERE id_humain = ?), (SELECT nombre_condamnation FROM humain WHERE id_humain = ?), (SELECT type_condamnation FROM humain WHERE id_humain = ?), (SELECT nationalite FROM humain WHERE id_humain = ?), (SELECT status_id FROM personne_impliquee WHERE humain_id = ?)) ; DELETE FROM personne_impliquee WHERE humain_id = ? ; DELETE FROM humain WHERE id_humain = ?; COMMIT ; ";
//			dbConnection.setAutoCommit(false);
//			
//			pstmtInsert = datasource.getConnection().prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
//			pstmtDelete1 = datasource.getConnection().prepareStatement(deleteSql1, PreparedStatement.RETURN_GENERATED_KEYS);
//			pstmtDelete2 = datasource.getConnection().prepareStatement(deleteSql2, PreparedStatement.RETURN_GENERATED_KEYS);
//			
//			for (int j=1 ; j<25 ; j++) {
//				pstmtInsert.setInt(++i, id);
//			}
//			
//			logSQL(pstmtInsert);
//			logSQL(pstmtDelete1);
//			logSQL(pstmtDelete2);
//		
//			pstmtInsert.executeUpdate();
//			pstmtDelete1.executeUpdate();
//			pstmtDelete2.executeUpdate();
//			
//			dbConnection.commit();
//			
//			}catch (SQLException e) {
//				e.printStackTrace();
//				log.error("SQL Error !:" + pstmtInsert.toString(), e);
//				log.error("SQL Error !:" + pstmtDelete1.toString(), e);
//				log.error("SQL Error !:" + pstmtDelete2.toString(), e);
//				throw e;
//				
//			} finally {
//				pstmtInsert.close();
//				pstmtDelete1.close();
//				pstmtDelete2.close();
//				dbConnection.close();
//			}
//	
//
//	}
	
	
//	@Override
//	public void archiverSuspect(int id) throws Exception{
//		PreparedStatement pstmt = null;
//		int i =0;
//		try {
//			String sql = "INSERT INTO archive_personne_impliquee (id_archive_personne_impliquee, nom , prenom , date_naissance , genre , photo , date_deces , adresse , grade , competences , date_prise_service , actif , telephone , taille , poids , signe_distinctif , empreinte , casier , nombre_condamnation , type_condamnation , nationalite , statut )  VALUES ((SELECT id_humain FROM humain WHERE id_humain = ?), (SELECT nom FROM humain WHERE id_humain = ?), (SELECT prenom FROM humain WHERE id_humain = ?), (SELECT date_naissance FROM humain WHERE id_humain = ?), (SELECT genre FROM humain WHERE id_humain = ?), (SELECT photo FROM humain WHERE id_humain = ?), (SELECT date_deces FROM humain WHERE id_humain = ?), (SELECT adresse FROM humain WHERE id_humain = ?), (SELECT grade FROM humain WHERE id_humain = ?), (SELECT competences FROM humain WHERE id_humain = ?),  (SELECT date_prise_service FROM humain WHERE id_humain = ?), (SELECT actif FROM humain WHERE id_humain = ?), (SELECT telephone FROM humain WHERE id_humain = ?), (SELECT taille FROM humain WHERE id_humain = ?), (SELECT poids FROM humain WHERE id_humain = ?), (SELECT signe_distinctif FROM humain WHERE id_humain = ?), (SELECT empreinte FROM humain WHERE id_humain = ?), (SELECT casier FROM humain WHERE id_humain = ?), (SELECT nombre_condamnation FROM humain WHERE id_humain = ?), (SELECT type_condamnation FROM humain WHERE id_humain = ?), (SELECT nationalite FROM humain WHERE id_humain = ?), (SELECT status_id FROM personne_impliquee WHERE humain_id = ?)) ;";
//			
//			pstmt = datasource.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
//			for (int j=1 ; j<23 ; j++) {
//				pstmt.setInt(++i, id);
//			}
//				
//				logSQL(pstmt);
//				pstmt.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				log.error("SQL Error !:" + pstmt.toString(), e);
//				throw e;
//			} finally {
//				pstmt.close();
//			}
//			
//	}
	
	@Override
	public void archiverSuspect(int id) throws Exception{
		PreparedStatement pstmt = null; 
		PreparedStatement pstmt2 = null;
		Suspect suspect = null;
		int i = 0;
		ResultSet rs;
		
		try {
			String sql = "SELECT * FROM humain WHERE id_humain = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			// Run la requete
			rs = pstmt.executeQuery();
			
			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				suspect = getSuspectFromResultSet(rs);	
			}
			
		}catch(Exception e) {
				e.printStackTrace();
				log.error("SQL Error !:" + pstmt.toString(), e);
				throw e;
		}
		
		try {
		
		String insertSql = " INSERT INTO archive_personne_impliquee ( id_archive_personne_impliquee , nom, prenom, genre, date_naissance, nationalite, taille, poids, adresse, signe_distinctif,photo, empreinte, casier,nombre_condamnation, type_condamnation ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		pstmt2 = datasource.getConnection().prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt2.setInt(++i, suspect.getId());
		pstmt2.setString(++i, suspect.getNom());
		pstmt2.setString(++i, suspect.getPrenom());
		pstmt2.setString(++i, suspect.getGenre());
		pstmt2.setDate(++i, suspect.getDateNaissance());
		pstmt2.setString(++i, suspect.getNationalite());
		pstmt2.setFloat(++i,suspect.getTaille());
		pstmt2.setInt(++i, suspect.getPoids());
		pstmt2.setString(++i, suspect.getAdresseConnues());
		pstmt2.setString(++i, suspect.getSigneDistinctif());
		pstmt2.setString(++i, suspect.getPhoto());
		pstmt2.setString(++i, suspect.getEmpreinte());
		pstmt2.setBoolean(++i, suspect.isCasierJudiciaire());
		pstmt2.setInt(++i, suspect.getCondamnations());
		pstmt2.setString(++i, suspect.getTypeCondamnation());

			// Log info
		logSQL(pstmt2);
		
			// Run the the update query	
		pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt2.toString(), e);
			throw e;
		} finally {
		pstmt2.close();
		}
	}
	
	
	@Override
	public void supprimerJointureSuspect(int id) throws Exception {
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM personne_impliquee WHERE humain_id = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			logSQL(pstmt);
			
			// Run the the update query
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
			
		}
	

	@Override
	public void supprimerSuspect(int id) throws Exception {
		
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM humain where id_humain = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			logSQL(pstmt);
			
			// Run the the update query
			int result = pstmt.executeUpdate();
			if(result != 1)
				throw new Exception("Suspect non trouvé");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			
			pstmt.close();
		}
			
		}
	}




