package co.simplon.model;

import java.sql.Date;


public class Humain {
	private int id;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String genre;
	
	
	
	public Humain( int id, String nom, String prenom, Date dateNaissance, String genre) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.genre = genre;
	}
	
	
	public Humain() {
		super();
		// TODO Auto-generated constructor stub
	}


	//getters setters
	
	
	public String getNom() {
		return nom;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	

}
