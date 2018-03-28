package co.simplon.model;

public class Arme {
	private int numSerie;
	private String TypeArme;
	private String modele;
	private String TypeBalistique;
	private String Marque;
	
	public Arme(int numSerie, String typeArme, String modele, String typeBalistique, String marque) {
		super();
		this.numSerie = numSerie;
		TypeArme = typeArme;
		this.modele = modele;
		TypeBalistique = typeBalistique;
		Marque = marque;
	}

	public Arme() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(int numSerie) {
		this.numSerie = numSerie;
	}
	public String getTypeArme() {
		return TypeArme;
	}
	public void setTypeArme(String typeArme) {
		TypeArme = typeArme;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getTypeBalistique() {
		return TypeBalistique;
	}
	public void setTypeBalistique(String typeBalistique) {
		TypeBalistique = typeBalistique;
	}
	public String getMarque() {
		return Marque;
	}
	public void setMarque(String marque) {
		Marque = marque;
	}
	
	
	

}
