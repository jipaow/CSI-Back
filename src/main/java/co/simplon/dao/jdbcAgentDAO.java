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
import co.simplon.model.Agent;
import co.simplon.model.DataAgent;


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
	 * keeped as example
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
	
    @Override
	public void archiverAgent(Agent agent) {
		Connection dbconnect = null;
		try {
		
	    dbconnect = datasource.getConnection();
		dbconnect.setAutoCommit(false);
		
		
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtDeleteJointure = null;
		PreparedStatement pstmtDelete = null ;
		
		int i = 0;
		
		try {
		String sqlInsert = " INSERT INTO archive_personne_impliquee ( id_archive_personne_impliquee , nom, prenom, genre, date_naissance, grade, competences , actif, date_prise_service, telephone) VALUES (?,?,?,?,?,?,?,?,?,?)";
		    pstmtInsert = dbconnect.prepareStatement(sqlInsert);
			pstmtInsert.setInt(++i, agent.getId());
			pstmtInsert.setString(++i, agent.getNom());
			pstmtInsert.setString(++i, agent.getPrenom());
			pstmtInsert.setString(++i, agent.getGenre());
			pstmtInsert.setDate(++i, agent.getDateNaissance());
			pstmtInsert.setString(++i, agent.getGrade());
			pstmtInsert.setString(++i,agent.getCompetences());
			pstmtInsert.setString(++i, agent.getStatutActivite());
			pstmtInsert.setDate(++i, agent.getAnciennete());
			pstmtInsert.setString(++i, agent.getTelephone());
		
			pstmtInsert.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			dbconnect.rollback();
			
		}finally {
			pstmtInsert.close();
		}
		 try {
			 String sqlDeleteJointure = "DELETE FROM personne_impliquee WHERE humain_id = ?";
			    pstmtDeleteJointure = dbconnect.prepareStatement(sqlDeleteJointure);
				pstmtDeleteJointure.setInt(1, agent.getId());
				// Run the the update query
				pstmtDeleteJointure.executeUpdate();
			 
		 }catch (SQLException e) {
				e.printStackTrace();
				dbconnect.rollback();
				
			}finally {
				pstmtDeleteJointure.close();
			}
		 try {
				String sqlDelete = "DELETE FROM humain where id_humain = ?";
				pstmtDelete = dbconnect.prepareStatement(sqlDelete);
				pstmtDelete.setInt(1, agent.getId());
				// Run the the update query
				pstmtDelete.executeUpdate();

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

	@Override
	public Agent getAgentForArchivage(int id) throws Exception {
		PreparedStatement pstmt = null;
		Agent agent = null;
		ResultSet rs;
		try {
			String sql = "SELECT * FROM humain WHERE id_humain = ?";
			pstmt = datasource.getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			logSQL(pstmt);
			
			while (rs.next()) {
				agent = getAgentFromResultSet(rs);
				System.out.println("agent : "+ agent.getId());
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
	}finally {
		pstmt.close();
	}
			
		return agent;
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


}
