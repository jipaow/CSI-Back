package co.simplon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;

@Repository
public class jdbcSuspectDAO implements SuspectDAO {
	
	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
			sql = "SELECT * FROM humain INNER JOIN personne_impliquee ON personne_impliquee.humain_id = humain.id_humain WHERE personne_impliquee.status_id = 2";
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
	public Suspect getSuspect(int id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs;
		Suspect suspect = null;

		try {
			// Prepare requet sql
			String sql = "SELECT * FROM humain INNER JOIN personne_impliquee ON personne_impliquee.humain_id = humain.id_humain WHERE personne_impliquee.status_id = 2 AND humain.id_humain = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);

			// Log info
			logSQL(pstmt);

			// Run requete
			rs = pstmt.executeQuery();
			
			// gere les resultats de requete
			if (rs.next())
				suspect = getSuspectFromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		return suspect;
	
	}

	@Override
	public Suspect insertSuspect(Suspect suspect) throws Exception {
		PreparedStatement pstmt = null;
			
		Suspect result = null;
		int i = 0;
		
		// TODO
		// force auto incremente en initialisant à 0, sinon erreur sql si id
		// existant
		//suspect.setId(new int(0));

		try {
			// Prepare the SQL query
			String sql1 = "INSERT INTO humain ( nom, prenom, genre, date_naissance, nationalite, taille, poids, adresse, signe_distinctif,photo, empreinte, casier,nombre_condamnation, type_condamnation ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		 "INSERT INTO personne_impliquee (humain_id, enquete_id,status_id) VALUES ((SELECT id_humain from humain WHERE nom=?), ?,2 )";
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

}
