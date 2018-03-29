package co.simplon.dao;

import co.simplon.model.Agent;
import co.simplon.model.DataAgent;
/**
 * 
 * @author Kayetan
 * Force implementing  crud methods in jdbcAgentDAO
 *
 */
public interface AgentDAO {
	
	public DataAgent listAgent() throws Exception;
	
	public DataAgent getAgent(int id) throws Exception;
	
	public Agent insertAgent(Agent agent) throws Exception;
	
	public Agent upDateAgent(Agent agent) throws Exception;
	
	public Agent addAgentToEnquete(Agent agent) throws Exception;
    
	public int verifAgentExiste(Agent agent) throws Exception;
}
