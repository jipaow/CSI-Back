package co.simplon.model;

public class SuspectEnquete {
	
	private Enquete enquete;
	
	private Suspect suspect;
	
	

	public SuspectEnquete(Enquete enquete, Suspect suspect) {
		super();
		this.enquete = enquete;
		this.suspect = suspect;
	}

	public Enquete getEnquete() {
		return enquete;
	}

	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}

	public Suspect getSuspect() {
		return suspect;
	}

	public void setSuspect(Suspect suspect) {
		this.suspect = suspect;
	}
	
	

}
