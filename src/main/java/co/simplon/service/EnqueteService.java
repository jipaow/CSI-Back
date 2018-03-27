package co.simplon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.simplon.dao.EnqueteDAO;
import co.simplon.model.Enquete;


@Service
public class EnqueteService {
	
	@Autowired
	private EnqueteDAO dao;
	
	// Retrieve all rows from table and populate list with objects
	public List<Enquete> getAllEnquete() throws Exception {
		return dao.listEnquete();
	}
	
	// Retrieves one row from table based on given id
	public Enquete getEnquete(int id) throws Exception {
		return dao.getEnquete(id);
	}
	
	// Inserts row into table 
	public Enquete insertEnquete(Enquete enquete) throws Exception {
		return dao.insertEnquete(enquete);
	}
	
	public Enquete updateEnquete( int id ,Enquete enquete) throws Exception {
		return dao.updateEnquete(enquete);
	}
	

}
