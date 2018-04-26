package co.simplon.dao;

import co.simplon.model.Arme;
import co.simplon.model.DataArme;
/***
 * Fonctionalité à implementer dans une V2
 * @author Utilisateur
 *
 */
public interface ArmeDAO {

	public DataArme listArme() throws Exception;
	
	public DataArme getArme(int id) throws Exception;
	
	public Arme insertArme(Arme arme) throws Exception;
	
	public Arme upDateArme(Arme arme) throws Exception;
	
	public Arme addArmeToEnquete(Arme arme) throws Exception;
    
	public int verifArmeExiste(Arme arme) throws Exception;
	
}
