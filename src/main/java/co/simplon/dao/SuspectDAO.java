package co.simplon.dao;

import java.util.List;

import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;

public interface SuspectDAO {
	
	public DataSuspect listSuspect() throws Exception;
	
	public Suspect  getSuspect(int id) throws Exception;
	
	public Suspect insertSuspect(Suspect suspect) throws Exception;
	
	public Suspect updateSuspect( Suspect suspect) throws Exception;
	
	public Suspect addSuspectToEnquete (Suspect suspect) throws Exception;
	
	public int verifSuspectExiste (Suspect suspect) throws Exception;

}
