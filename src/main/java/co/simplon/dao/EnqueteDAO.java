package co.simplon.dao;
import co.simplon.model.DataEnquete;
import co.simplon.model.Enquete;

/**
 * 
 * @author Robin
 *	Interface qui définit les méthodes utilisables dans la classe jdbcEnqueteDAO
 */
public interface EnqueteDAO {
	
	public DataEnquete listEnquete() throws Exception;
	
	public DataEnquete getEnquete(int numeroDossier) throws Exception;
	
	public Enquete insertEnquete(Enquete enquete) throws Exception;
	
	public Enquete updateEnquete(Enquete enquete) throws Exception;
	
	public Enquete addSuspectToEnquete (Enquete enquete) throws Exception;
	
	public Enquete getEnqueteForArchiver(int id) throws Exception;
	
	public void archiverEnquete (Enquete enquete) throws Exception;
	

}
