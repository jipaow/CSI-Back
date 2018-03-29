package co.simplon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.simplon.dao.AgentDAO;
import co.simplon.model.Agent;
import co.simplon.model.DataAgent;

/**
 * 
 * @author Kayetan
 * Link between AgentController and AgentDAO ( and its impl jdbcAgnetDAO )
 * Expose AgentDAO interface methods
 *
 */
@Service
public class AgentService {
	
	@Autowired
	private AgentDAO dao;
	/**
	 * Retreives all rows from humain table with an agent status and populate it in a list
	 * @return dao.listAgent();
	 * @throws Exception
	 */
	public DataAgent getAllAgent() throws Exception {
		return dao.listAgent();
	}
	/**
	 * Retreives a specific row using an id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DataAgent getAgent(int id) throws Exception {
		return dao.getAgent(id);
	}
	/**
	 * Insert agent in database
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	public Agent insertAgent(Agent agent) throws Exception {
		return dao.insertAgent(agent);
	}
	/**
	 * Update datas of humain with status agent using id
	 * @param id
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	public Agent upDateAgent( int id ,Agent agent) throws Exception {
		return dao.upDateAgent(agent);
	}
	
	public Agent addAgentToEnquete (Agent agent) throws Exception {
		return dao.addAgentToEnquete(agent);
	}
	 // check for duplicate
	public int verifAgentExiste(Agent agent) throws Exception {
		return dao.verifAgentExiste(agent);
	}
	
	
	
	
	

}
