package co.simplon.model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author jean philippe
 * Cette classe permet de typer les objets json et les arraylist envoyées au front
 * Modele pour la vue.
 *
 */
public class DataSuspect {
	private String type = "suspect";
	
	private List <Suspect> data = new ArrayList<>();
	
	public DataSuspect() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataSuspect(String type, List<Suspect> data) {
		super();
		this.type = type;
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Suspect> getData() {
		return data;
	}
	public void setData(List<Suspect> data) {
		this.data = data;
	}

	

	

	
	
	
	
	

}
