package co.simplon.model;

import java.sql.Date;

public class Agent extends Humain {
	
	private String grade;
	private String competences;
	private String statutActivite;
	private Date anciennete;
	

	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Agent(int id, String nom, String prenom, Date dateNaissance, String genre) {
		super(id, nom, prenom, dateNaissance, genre);
		// TODO Auto-generated constructor stub
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCompetences() {
		return competences;
	}
	public void setCompetences(String competences) {
		this.competences = competences;
	}
	public String getStatutActivite() {
		return statutActivite;
	}
	public void setStatutActivite(String statutActivite) {
		this.statutActivite = statutActivite;
	}
	public Date getAnciennete() {
		return anciennete;
	}
	public void setAnciennete(Date anciennete) {
		this.anciennete = anciennete;
	}
		
	
	
	
	

}
