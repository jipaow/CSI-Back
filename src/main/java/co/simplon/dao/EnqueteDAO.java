package co.simplon.dao;

import java.util.List;

import co.simplon.model.DataEnquete;
import co.simplon.model.Enquete;


public interface EnqueteDAO {
	
	public DataEnquete listEnquete() throws Exception;
	
	public DataEnquete getEnquete(int numeroDossier) throws Exception;
	
	public Enquete insertEnquete(Enquete enquete) throws Exception;
	
	public Enquete updateEnquete(Enquete enquete) throws Exception;
	
	public Enquete addSuspectToEnquete (Enquete enquete) throws Exception;
	
	public Enquete archiverEnquete (Enquete enquete) throws Exception;
	
	public void supprimerJointureEnquete (int id) throws Exception;
	
	public void supprimerEnquete (int id) throws Exception;

}
