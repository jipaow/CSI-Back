package co.simplon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import co.simplon.model.Agent;
import co.simplon.model.DataAgent;
import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;

/**
 * 
 * @author Kayetan
 * Expose interacting methods with database
 *
 */
@Repository
public class jdbcAgentDAO implements AgentDAO {
	
	private DataSource datasource;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public jdbcAgentDAO(JdbcTemplate jdbcTemplate) {
		this.datasource = jdbcTemplate.getDataSource();
	}
	
	@Override
	public DataAgent listAgent() throws Exception {
		DataAgent dataAgent =  new DataAgent();
		Agent agent;
		String sql;
		PreparedStatement prep = null;
		ResultSet rs;
		
		try {
			sql = "SELECT DISTINCT * FROM humain WHERE grade IS NOT NULL";
			prep = datasource.getConnection().prepareStatement(sql);
			
			rs = prep.executeQuery();
			
			logSQL(prep);
			
			while (rs.next()) {
				agent = getAgentFromResultSet(rs);
				dataAgent.getData().add(agent);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("SQL ERROR requete dataAgent:" + prep.toString(),e);
			throw e;
		}finally {
			prep.close();
		}
		
		return dataAgent;
	}
	

	@Override
	public DataAgent getAgent(int id) throws Exception {
		DataAgent dataAgent =  new DataAgent();
		Agent agent;
		String sql;
		PreparedStatement prep = null;
		ResultSet rs;
		
		try {
			// Prepare requet sql
			sql = "SELECT DISTINCT * FROM humain WHERE id_humain = ? AND grade IS NOT NULL";
			prep = datasource.getConnection().prepareStatement(sql);
			prep.setInt(1, id);
			
			rs = prep.executeQuery();
			
			logSQL(prep);
			
			while (rs.next()) {
				agent = getAgentFromResultSet(rs);
				dataAgent.getData().add(agent);
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("SQL ERROR requete dataAgent:" + prep.toString(),e);
			throw e;
		}finally {
			prep.close();
		}
		
		return dataAgent;
	}
	
	/**
	 * 
	 * Alternative method to get an agent using inner join request
	 * 
	public DataSuspect getAgent (int id) throws Exception {
		Agent agent;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataAgent dataSuspect = new DataAgent();
		
		try {
			// Prepare la requete sql
			sql = "SELECT DISTINCT * FROM humain INNER JOIN personne_impliquee ON personne_impliquee.humain_id = humain.id_humain WHERE personne_impliquee.status_id = 1 AND humain.id_humain = ? GROUP BY id_humain";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			// Run la requete
			rs = pstmt.executeQuery();
			
			// Log info
			logSQL(pstmt);

			// gere le resultat de la requete
			while (rs.next()) {
				suspect = getSuspectFromResultSet(rs);
				dataAgent.getData().add(agent);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return dataAgent;
	}
	
	*/
	@Override
	public Agent insertAgent(Agent agent) throws Exception {
		PreparedStatement pstmt = null;
		Agent agentResult = null;
		int i = 0;
		
		try {
			// Prepare la requete sql
			String sql1 = "INSERT INTO humain ( nom, prenom, genre, date_naissance, grade, competences, actif, date_prise_service, telephone ) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = datasource.getConnection().prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(++i, agent.getNom());
			pstmt.setString(++i, agent.getPrenom());
			pstmt.setString(++i, agent.getGenre());
			pstmt.setDate(++i, agent.getDateNaissance());
			pstmt.setString(++i, agent.getGrade());
			pstmt.setString(++i, agent.getCompetences());
			pstmt.setString(++i, agent.getStatutActivite());
			pstmt.setDate(++i, agent.getAnciennete());
			pstmt.setString(++i, agent.getTelephone());
			// Log info
			logSQL(pstmt);
			// execute la requete	
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return agentResult;
	}

	@Override
	public Agent upDateAgent(Agent agent) throws Exception {
		Agent result = null;
		PreparedStatement pstmt = null;
		int i = 0;
		
		try {
			String sql = "UPDATE humain SET nom = ?, prenom = ?, genre = ?, date_naissance = ?, grade = ?, competences = ?, actif = ?, date_prise_service = ?, telephone = ? WHERE id_humain = ?";
			
			pstmt = datasource.getConnection().prepareStatement(sql);
			
			pstmt.setString(++i, agent.getNom());
			pstmt.setString(++i, agent.getPrenom());
			pstmt.setString(++i, agent.getGenre());
			pstmt.setDate(++i, agent.getDateNaissance());
			pstmt.setString(++i, agent.getGrade());
			pstmt.setString(++i, agent.getCompetences());
			pstmt.setString(++i, agent.getStatutActivite());
			pstmt.setDate(++i, agent.getAnciennete());
			pstmt.setString(++i, agent.getTelephone());
			pstmt.setInt(++i,  agent.getId());
			
			logSQL(pstmt);
			
			int resultCount = pstmt.executeUpdate();
			if(resultCount != 1)
				throw new Exception("Agent non trouvé");
			
			result = agent;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error : " + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		return result;
	}

	@Override
	public Agent addAgentToEnquete(Agent agent) throws Exception {
		PreparedStatement pstmt = null;
		Agent result = null;
		int i = 0;
		
		try {
			String sql = "INSERT INTO personne_impliquee (humain_id, enquete_id, status_id) VALUES (?, ?, 1)";
			pstmt = datasource.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(++i, agent.getId());
			pstmt.setInt(++i, agent.getNumEnquete());
			
			logSQL(pstmt);
			
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error addAgentToEnquete:" + pstmt.toString(),e);
		}finally {
			pstmt.close();
		}
		return result;
	}

	@Override
	public int verifAgentExiste(Agent agent) throws Exception {
		String sql;
		int i = 0;
		int resultAgent =0;
		PreparedStatement prepStat = null;
		ResultSet rs;
		
		try {
			// Prepare requete sql
			sql = "SELECT * FROM humain WHERE nom = ? AND prenom = ?";
			
			prepStat = datasource.getConnection().prepareStatement(sql);
			prepStat.setString(++i, agent.getNom());
			prepStat.setString(++i, agent.getPrenom());

			// Log info
			logSQL(prepStat);

			// Run requete
			rs = prepStat.executeQuery();
			
		
			if (rs.next())
				resultAgent = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQL Error !:" + prepStat.toString(), e);
			throw e;
		} finally {
			//ferme la connection
			prepStat.close();
		}
		return resultAgent;
	}
	
	private Agent getAgentFromResultSet( ResultSet rs) throws SQLException {
		Agent agent = new Agent();
		
		agent.setId(rs.getInt("id_humain"));
		agent.setNom(rs.getString("nom"));
		agent.setPrenom(rs.getString("prenom"));
		agent.setGenre(rs.getString("genre"));
		agent.setDateNaissance(rs.getDate("date_naissance"));
		agent.setTelephone(rs.getString("telephone"));
		agent.setGrade(rs.getString("grade"));
		agent.setCompetences(rs.getString("competences"));
		agent.setAnciennete(rs.getDate("date_prise_service"));
		agent.setStatutActivite(rs.getString("actif"));
		
		
		return agent;
	}
	
	private void logSQL(PreparedStatement prep) {
		String sql;
		
		if (prep == null)
			return;
		
		sql = prep.toString().substring(prep.toString().indexOf(":") + 2);
		log.debug(sql);
	}

	@Override
	public void archiverAgent(int id) throws Exception {
		PreparedStatement pstmt = null;
		int i =0;
		try {
			String sql = "INSERT INTO archive_personne_impliquee (id_archive_personne_impliquee, nom , prenom , date_naissance , genre , photo , date_deces , adresse , grade , competences , date_prise_service , actif , telephone , taille , poids , signe_distinctif , empreinte , casier , nombre_condamnation , type_condamnation , nationalite , statut )  VALUES ((SELECT id_humain FROM humain WHERE id_humain = ?), (SELECT nom FROM humain WHERE id_humain = ?), (SELECT prenom FROM humain WHERE id_humain = ?), (SELECT date_naissance FROM humain WHERE id_humain = ?), (SELECT genre FROM humain WHERE id_humain = ?), (SELECT photo FROM humain WHERE id_humain = ?), (SELECT date_deces FROM humain WHERE id_humain = ?), (SELECT adresse FROM humain WHERE id_humain = ?), (SELECT grade FROM humain WHERE id_humain = ?), (SELECT competences FROM humain WHERE id_humain = ?),  (SELECT date_prise_service FROM humain WHERE id_humain = ?), (SELECT actif FROM humain WHERE id_humain = ?), (SELECT telephone FROM humain WHERE id_humain = ?), (SELECT taille FROM humain WHERE id_humain = ?), (SELECT poids FROM humain WHERE id_humain = ?), (SELECT signe_distinctif FROM humain WHERE id_humain = ?), (SELECT empreinte FROM humain WHERE id_humain = ?), (SELECT casier FROM humain WHERE id_humain = ?), (SELECT nombre_condamnation FROM humain WHERE id_humain = ?), (SELECT type_condamnation FROM humain WHERE id_humain = ?), (SELECT nationalite FROM humain WHERE id_humain = ?), (SELECT status_id FROM personne_impliquee WHERE humain_id = ?)) ;";
			
			pstmt = datasource.getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			for (int j=1 ; j<23 ; j++) {
				pstmt.setInt(++i, id);
			}
				
				logSQL(pstmt);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("SQL Error !:" + pstmt.toString(), e);
				throw e;
			} finally {
				pstmt.close();
			}
		
	}

	@Override
	public void supprimerJointureAgent(int id) throws Exception {
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
	public void supprimerAgent(int id) throws Exception {
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
	
	/*	for example of inner join request 
	 * @Override
	public DataEnquete getArmeFromEnquete(int id) throws Exception {
		Enquete enquete;
		Arme arme;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		DataEnquete dataEnquete = new DataEnquete();
		
		try {
			// Prepare la requete sql
			sql = "SELECT * FROM  enquete e INNER JOIN arme a  ON e.id_enquete = a.enquete_id WHERE e.id_enquete = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
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
		
		
	}*/


}
