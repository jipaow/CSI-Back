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
import co.simplon.model.DataEnquete;
import co.simplon.model.Enquete;

/**
 * 
 * @author Robin Cette classe définit les méthodes d'intéraction avec la base de
 *         données Les détails de chaque fonction peut être retrouvés dans la
 *         javaDoc de la classe EnqueteService
 *
 */
@Repository
public class jdbcEnqueteDAO implements EnqueteDAO {

	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public jdbcEnqueteDAO(JdbcTemplate jdbcTemplate) {
		this.datasource = jdbcTemplate.getDataSource();
	}

	@Override
	public DataEnquete listEnquete() throws Exception {
		Enquete enquete;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataEnquete dataEnquete = new DataEnquete();

		try {
			// Prepare la requete sql
			sql = "SELECT * FROM enquete";
			pstmt = datasource.getConnection().prepareStatement(sql);

			// Run la requete
			rs = pstmt.executeQuery();

			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				enquete = getEnqueteFromResultSet(rs);
				dataEnquete.getData().add(enquete);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return dataEnquete;
	}

	@Override
	public DataEnquete getEnquete(int numeroDossier) throws Exception {
		Enquete enquete;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataEnquete dataEnquete = new DataEnquete();

		try {
			// Prepare la requete sql
			sql = "SELECT * FROM enquete WHERE id_enquete = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, numeroDossier);
			// Run la requete
			rs = pstmt.executeQuery();

			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				enquete = getEnqueteFromResultSet(rs);
				dataEnquete.getData().add(enquete);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return dataEnquete;
	}

	@Override
	public Enquete insertEnquete(Enquete enquete) throws Exception {
		PreparedStatement pstmt = null;
		Enquete result = null;
		int i = 0;

		try {
			// Prepare the SQL query
			String sql = "INSERT INTO enquete ( nom_enquete, type_affaire, date_creation, localisation, statut, classee ) VALUES (?,?,?,?,?,?)";
			pstmt = datasource.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(++i, enquete.getNom());
			pstmt.setString(++i, enquete.getCategorie());
			pstmt.setDate(++i, enquete.getDateCreation());
			pstmt.setString(++i, enquete.getLocalisation());
			pstmt.setString(++i, enquete.getStatut());
			pstmt.setBoolean(++i, enquete.isClassee());

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

	/*
	 * methode pour recupérer une arme impliquée dans une enquete non implémentée
	 * dans le front. Ecrite à titre d'exemple de jointure "inner join "
	 * enquete/arme
	 * 
	 * @Override public DataEnquete getArmeFromEnquete(int id) throws Exception {
	 * Enquete enquete; Arme arme; PreparedStatement pstmt = null; ResultSet rs;
	 * String sql; DataEnquete dataEnquete = new DataEnquete();
	 * 
	 * try { // Prepare la requete sql sql =
	 * "SELECT * FROM  enquete e INNER JOIN arme a  ON e.id_enquete = a.enquete_id WHERE e.id_enquete = ?"
	 * ; pstmt = datasource.getConnection().prepareStatement(sql); pstmt.setInt(1,
	 * id); // Run la requete rs = pstmt.executeQuery();
	 * 
	 * // Log info logSQL(pstmt);
	 * 
	 * // gere le resultat de la requete while (rs.next()) { enquete =
	 * getEnqueteFromResultSet(rs); dataEnquete.getData().add(enquete); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); log.error("SQL Error !:" +
	 * pstmt.toString(), e); throw e; } finally { pstmt.close(); }
	 * 
	 * return dataEnquete;
	 * 
	 * 
	 * }
	 */

	@Override
	public Enquete updateEnquete(Enquete enquete) throws Exception {
		Enquete result = null;
		PreparedStatement pstmt = null;
		int i = 0;

		try {
			// Prepare the SQL query
			String sql = "UPDATE enquete SET nom_enquete = ?, type_affaire = ?, date_creation = ?, localisation = ?, statut = ?, classee = ? WHERE id_enquete = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);

			pstmt.setString(++i, enquete.getNom());

			pstmt.setString(++i, enquete.getCategorie());

			pstmt.setDate(++i, enquete.getDateCreation());

			pstmt.setString(++i, enquete.getLocalisation());

			pstmt.setString(++i, enquete.getStatut());

			pstmt.setBoolean(++i, enquete.isClassee());

			pstmt.setInt(++i, enquete.getNumeroDossier());

			// Log info
			logSQL(pstmt);

			// Run the the update query
			int resultCount = pstmt.executeUpdate();
			if (resultCount != 1)
				throw new Exception("Enquête non trouvé !");

			result = enquete;

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
	public Enquete addSuspectToEnquete(Enquete enquete) throws Exception {
		PreparedStatement pstmt = null;
		Enquete result = null;
		int i = 0;

		try {
			String sql = "INSERT INTO personne_impliquee (humain_id, enquete_id,status_id) VALUES ((select id_humain from humain order by id_humain desc limit 1), ?, 2 )";
			pstmt = datasource.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setInt(++i, enquete.getNumeroDossier());

			// Log info
			logSQL(pstmt);

			pstmt.executeUpdate();
			System.out.println("pstmt ok");

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return result;
	}

	/**
	 * recupère un objet Enquete à passer en parametre de la methode
	 * d'archiverEnquete
	 * 
	 * @param id
	 * @return Enquete
	 */
	@Override
	public Enquete getEnqueteForArchiver(int id) throws Exception {
		PreparedStatement pstmt = null;
		Enquete enquete = null;

		ResultSet rs;

		try {

			String sql = "SELECT * FROM enquete WHERE id_enquete = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);

			// Run la requete
			rs = pstmt.executeQuery();

			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				enquete = getEnqueteFromResultSet(rs);
				System.out.println("resultat getArchivage : " + enquete.getNom());
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		return enquete;
	}

	/**
	 * Transaction manuelle pour archiver une enquete, insert l'enquete dans la
	 * table archive_enquete , puis delete l'entrée dans la table de jointure et
	 * enfin delete l'entrée dans la table enquete
	 */
	@Override
	public void archiverEnquete(Enquete enquete) throws Exception {
		Connection dbconnect = null;
		try {

			dbconnect = datasource.getConnection();
			dbconnect.setAutoCommit(false);

			PreparedStatement pstmtInsert = null;
			PreparedStatement pstmtDeleteJointure = null;
			PreparedStatement pstmtDelete = null;

			int i = 0;

			try {
				String sqlInsert = " INSERT INTO archive_enquete (nom_enquete_archive, type_affaire_archive, date_creation_archive, localisation_archive, statut_archive, classee_archive, enquete_id_archive) VALUES (?,?,?,?,?,?,?)";
				pstmtInsert = dbconnect.prepareStatement(sqlInsert);
				pstmtInsert.setString(++i, enquete.getNom());
				pstmtInsert.setString(++i, enquete.getCategorie());
				pstmtInsert.setDate(++i, enquete.getDateCreation());
				pstmtInsert.setString(++i, enquete.getLocalisation());
				pstmtInsert.setString(++i, enquete.getStatut());
				pstmtInsert.setBoolean(++i, enquete.isClassee());
				pstmtInsert.setInt(++i, enquete.getNumeroDossier());

				pstmtInsert.executeUpdate();
				System.out.println("insert ok");

			} catch (SQLException e) {
				e.printStackTrace();
				dbconnect.rollback();

			} finally {
				pstmtInsert.close();
			}
			try {
				String sqlDeleteJointure = "DELETE FROM personne_impliquee WHERE enquete_id = ?";
				pstmtDeleteJointure = dbconnect.prepareStatement(sqlDeleteJointure);
				pstmtDeleteJointure.setInt(1, enquete.getNumeroDossier());
				// Run the the update query
				pstmtDeleteJointure.executeUpdate();
				System.out.println("delete jointure ok");
			} catch (SQLException e) {
				e.printStackTrace();
				dbconnect.rollback();

			} finally {
				pstmtDeleteJointure.close();
			}
			try {
				String sqlDelete = "DELETE FROM enquete WHERE id_enquete = ?";
				pstmtDelete = dbconnect.prepareStatement(sqlDelete);
				pstmtDelete.setInt(1, enquete.getNumeroDossier());
				// Run the the update query
				pstmtDelete.executeUpdate();
				System.out.println("delete ok");

			} catch (Exception e) {
				e.printStackTrace();
				dbconnect.rollback();
			} finally {
				pstmtDelete.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (dbconnect != null)
					dbconnect.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Cette méthode récupère les valeurs des champs de la table enquête et de les
	 * lier aux attributs de la classe enquête
	 * 
	 * @param rs
	 * @return enquete
	 * @throws SQLException
	 */
	private Enquete getEnqueteFromResultSet(ResultSet rs) throws SQLException {
		Enquete enquete = new Enquete();
		enquete.setNumeroDossier(rs.getInt("id_enquete"));
		enquete.setNom(rs.getString("nom_enquete"));
		enquete.setCategorie(rs.getString("type_affaire"));
		enquete.setDateCreation(rs.getDate("date_creation"));
		enquete.setLocalisation(rs.getString("localisation"));
		enquete.setStatut(rs.getString("statut"));
		enquete.setClassee(rs.getBoolean("classee"));

		return enquete;
	}

	private void logSQL(PreparedStatement pstmt) {
		String sql;

		if (pstmt == null)
			return;

		sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
		log.debug(sql);

	}

}
