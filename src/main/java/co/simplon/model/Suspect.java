package co.simplon.model;

import java.sql.Date;

public class Suspect extends Humain {
	

	private String adresseConnues;
	private float taille;
	private int poids;
	private String signeDistinctif;
	private String nationalite;
	private boolean casierJudiciaire;
	private int condamnations;
	private String empreinte;
	private String typeCondamnation;
	private String photo;
	private String nomEnquete;
	private int numEnquete;

	
	
	public Suspect(String adresseConnues, float taille, int poids, String signeDistinctif, String nationalite,
			boolean casierJudiciaire, int condamnations, String empreinte, String typeCondamnation, String photo) {
		super();
		this.adresseConnues = adresseConnues;
		this.taille = taille;
		this.poids = poids;
		this.signeDistinctif = signeDistinctif;
		this.nationalite = nationalite;
		this.casierJudiciaire = casierJudiciaire;
		this.condamnations = condamnations;
		this.empreinte = empreinte;
		this.typeCondamnation = typeCondamnation;
		this.photo = photo;

	}
	
	
	public Suspect(int id, String nom, String prenom, Date dateNaissance, String genre) {
		super(id, nom, prenom, dateNaissance, genre);
		// TODO Auto-generated constructor stub
	}





	public String getNomEnquete() {
		return nomEnquete;
	}



	public void setNomEnquete(String nomEnquete) {
		this.nomEnquete = nomEnquete;
	}



	public int getNumEnquete() {
		return numEnquete;
	}



	public void setNumEnquete(int numEnquete) {
		this.numEnquete = numEnquete;
	}



	public Suspect() {
		super();
		// TODO Auto-generated constructor stub
	}


	//getters setters
	public String getEmpreinte() {
		return empreinte;
	}
	
	public void setEmpreinte(String empreinte) {
		this.empreinte = empreinte;
	}
	public String getAdresseConnues() {
		return adresseConnues;
	}
	public void setAdresseConnues(String adresseConnues) {
		this.adresseConnues = adresseConnues;
	}
	public float getTaille() {
		return taille;
	}
	public void setTaille(float taille) {
		this.taille = taille;
	}
	public int getPoids() {
		return poids;
	}
	public void setPoids(int poid) {
		this.poids = poid;
	}
	public String getSigneDistinctif() {
		return signeDistinctif;
	}
	public void setSigneDistinctif(String signeDistinctif) {
		this.signeDistinctif = signeDistinctif;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	public boolean isCasierJudiciaire() {
		return casierJudiciaire;
	}
	public void setCasierJudiciaire(boolean casierJudiciaire) {
		this.casierJudiciaire = casierJudiciaire;
	}
	public int getCondamnations() {
		return condamnations;
	}
	public void setCondamnations(int condamnations) {
		this.condamnations = condamnations;
	}
	public String getTypeCondamnation() {
		return typeCondamnation;
	}
	public void setTypeCondamnation(String typeCondamnation) {
		this.typeCondamnation = typeCondamnation;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	

}
