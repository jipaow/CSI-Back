package co.simplon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.simplon.dao.ArmeDAO;
import co.simplon.model.Arme;
import co.simplon.model.DataArme;

@Service
public class ArmeService {
	
	@Autowired
	private ArmeDAO dao;
	
	public DataArme getAllArme() throws Exception {
		return dao.listArme();	
	}
	
	public DataArme getArme(int id) throws Exception {
		return dao.getArme(id);
	}
	
	public  Arme insertArme(Arme arme) throws Exception {
		return dao.insertArme(arme);
	}
	
	public Arme upDateArme ( int id, Arme arme) throws Exception {
		return dao.upDateArme(arme);
	}
	
	public int verifArmeExiste(Arme arme) throws Exception {
		return dao.verifArmeExiste(arme);
	}

}
