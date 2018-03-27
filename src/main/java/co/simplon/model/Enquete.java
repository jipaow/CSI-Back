package co.simplon.model;

import java.sql.Date;


public class Enquete {
	private int numeroDossier;
	private String nom;
	private String categorie;
	private Date dateCreation;
	private String localisation;
	private String statut;
	private boolean classee;
	
	
	
	
	public Enquete(int numeroDossier, String nom, String categorie, Date dateCreation, String localisation,
			String statut, boolean classee) {
		super();
		this.numeroDossier = numeroDossier;
		this.nom = nom;
		this.categorie = categorie;
		this.dateCreation = dateCreation;
		this.localisation = localisation;
		this.statut = statut;
		this.classee = classee;
	}
	
	public Enquete (){
		super();
	}
	//getters setters
	public int getNumeroDossier() {
		return numeroDossier;
	}
	public void setNumeroDossier(int numeroDossier) {
		this.numeroDossier = numeroDossier;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getLocalisation() {
		return localisation;
	}
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public boolean isClassee() {
		return classee;
	}
	public void setClassee(boolean classee) {
		this.classee = classee;
	}

	
	
}
