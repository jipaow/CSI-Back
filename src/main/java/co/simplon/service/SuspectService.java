package co.simplon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.simplon.dao.SuspectDAO;
import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;



@Service
public class SuspectService {
	
	@Autowired
	private SuspectDAO dao;
	
	// Retrieve all rows from table and populate list with objects
	public DataSuspect getAllSuspect() throws Exception {
		return dao.listSuspect();
	}
	
	// Retrieves one row from table based on given id
	public DataSuspect getSuspect(int id) throws Exception {
		return dao.getSuspect(id);
	}
	
	// Inserts row into table 
	public Suspect insertSuspect(Suspect suspect) throws Exception {
		return dao.insertSuspect(suspect);
	}
	
	public Suspect updateSuspect( int id ,Suspect suspect) throws Exception {
		return dao.updateSuspect(suspect);
	}
	
	public Suspect addSuspectToEnquete (Suspect suspect) throws Exception {
		return dao.addSuspectToEnquete(suspect);
	}
	
	// verifie qu'il n'existe pas de doublon suspect
	public int verifSuspectExiste(Suspect suspect) throws Exception {
		return dao.verifSuspectExiste(suspect);
	}
	

}
