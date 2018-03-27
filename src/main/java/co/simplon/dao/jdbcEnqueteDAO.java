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

import co.simplon.model.Enquete;
import co.simplon.model.Suspect;

@Repository
public class jdbcEnqueteDAO implements EnqueteDAO {
	
	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public jdbcEnqueteDAO(JdbcTemplate jdbcTemplate) {
		this.datasource = jdbcTemplate.getDataSource();
	}

	
	
	@Override
	public List<Enquete> listEnquete() throws Exception {
		Enquete enquete;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		ArrayList <Enquete> listEnquete = new ArrayList<Enquete>();
		
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
				listEnquete.add(enquete);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
	
		return listEnquete;
	}


	@Override
	public Enquete getEnquete(int numeroDossier) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs;
		Enquete enquete = null;

		try {
			// Prepare requet sql
			String sql = "SELECT * FROM enquete WHERE id_enquete = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, numeroDossier);

			// Log info
			logSQL(pstmt);

			// Run requete
			rs = pstmt.executeQuery();
			
			// gere les resultats de requete
			if (rs.next())
				enquete = getEnqueteFromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		return enquete;
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
			pstmt.setBoolean(++i,  enquete.isClassee());
			
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
	public Enquete updateEnquete(Enquete enquete) throws Exception {
		Enquete result = null;
		PreparedStatement pstmt = null;
		int i = 0;

		try {
			// Prepare the SQL query
			String sql = "UPDATE enquete SET nom_enquete = ?, type_affaire = ?, date_creation = ?, localisation = ?, statut = ?, classee = ? WHERE id_enquete = ?";
            pstmt = datasource.getConnection().prepareStatement(sql);

            pstmt.setString(++i, enquete.getNom());
            System.out.println(enquete.getNom());
            pstmt.setString(++i, enquete.getCategorie());
            System.out.println(enquete.getCategorie());
            pstmt.setDate(++i, enquete.getDateCreation());
            System.out.println(enquete.getDateCreation());
            pstmt.setString(++i, enquete.getLocalisation());
            System.out.println(enquete.getLocalisation());
            pstmt.setString(++i, enquete.getStatut());
            System.out.println(enquete.getStatut());
            pstmt.setBoolean(++i, enquete.isClassee());
            System.out.println(enquete.isClassee());
            pstmt.setInt(++i,  enquete.getNumeroDossier());
            System.out.println(enquete.getNumeroDossier());
			// Log info
			logSQL(pstmt);
			
			// Run the the update query
			int resultCount = pstmt.executeUpdate();
			if(resultCount != 1)
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
	
	private void logSQL(PreparedStatement pstmt) {
	String sql;
		
		if (pstmt == null)
			return;
		
		sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
		log.debug(sql);
		
	}

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

	

	
}
