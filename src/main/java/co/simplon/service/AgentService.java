package co.simplon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.simplon.dao.AgentDAO;
import co.simplon.model.Agent;
import co.simplon.model.DataAgent;


@Service
public class AgentService {
	
	@Autowired
	private AgentDAO dao;
	
	public DataAgent getAllAgent() throws Exception {
		return dao.listAgent();
	}
	
	public DataAgent getAgent(int id) throws Exception {
		return dao.getAgent(id);
	}

	public Agent insertAgent(Agent agent) throws Exception {
		return dao.insertAgent(agent);
	}
	
	public Agent upDateAgent( int id ,Agent agent) throws Exception {
		return dao.upDateAgent(agent);
	}
	
	public Agent addAgentToEnquete (Agent agent) throws Exception {
		return dao.addAgentToEnquete(agent);
	}
	 // verifie qu'il n'existe pas de doublon
	public int verifAgentExiste(Agent agent) throws Exception {
		return dao.verifAgentExiste(agent);
	}
	
	
	
	
	

}
