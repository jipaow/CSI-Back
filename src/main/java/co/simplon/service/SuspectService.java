package co.simplon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.simplon.dao.SuspectDAO;
import co.simplon.model.DataSuspect;
import co.simplon.model.Suspect;


/**
 * 
 * @author jean philippe
 * classe permet de faire le lien entre le controller SuspectController et la DAO 
 * ( interface SuspectDAO et son implementation jdbcSuspectDAO)
 *
 */
@Service
public class SuspectService {
	
	@Autowired
	private SuspectDAO dao;
	
	
	
	/**
	 *  recupere toutes les lignes de la table humain avec un statut suspect (cf addSuspectToEnquete) et les insert dans une list<suspect>
	 * @return list<suspect>
	 * @throws Exception
	 */
	public DataSuspect getAllSuspect() throws Exception {
		return dao.listSuspect();
	}
	
	/**
	 * Recupere les information d'un humain avec un statut suspect à partir de son id
	 * @param id
	 * @return suspect
	 * @throws Exception
	 */
	public DataSuspect getSuspect(int id) throws Exception {
		return dao.getSuspect(id);
	}
	
	/**
	 *  Insert dans la base de données un suspect
	 * @param suspect
	 * @return Suspect
	 * @throws Exception
	 */
	public Suspect insertSuspect(Suspect suspect) throws Exception {
		return dao.insertSuspect(suspect);
	}
	/**
	 * Met à jour un suspect à partr de son id
	 * @param id
	 * @param suspect
	 * @return Suspect
	 * @throws Exception
	 */
	public Suspect updateSuspect( int id ,Suspect suspect) throws Exception {
		return dao.updateSuspect(suspect);
	}
	/**
	 * Affecte un Humain avec le statut suspect à une enquete en utilisant leurs Id
	 * @param suspect
	 * @return Suspect
	 * @throws Exception
	 */
	public Suspect addSuspectToEnquete (Suspect suspect) throws Exception {
		return dao.addSuspectToEnquete(suspect);
	}
	
	// verifie qu'il n'existe pas de doublon suspect
	public int verifSuspectExiste(Suspect suspect) throws Exception {
		return dao.verifSuspectExiste(suspect);
	}
	
	/**
	 * Archive une personne impliquée en tant que suspect de la liste des suspects et l'ajoute à la table archive_suspect
	 * recupère un objet Suspect par son id (getSuspectArchivage) pour le passer en parametre de la methode archiverSuspect qui try
	 * une transaction manuelle.
	 * @param id
	 * @throws Exception
	 */	
	public void archiverSuspect ( int id ) throws Exception {
		Suspect suspect = dao.getSuspectForArchivage(id);
		dao.archiverSuspect(suspect);
	}
	
	

}
