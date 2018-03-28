package co.simplon.model;

import java.util.ArrayList;
import java.util.List;

public class DataArme {
	
	private String type = "arme";
	List <Arme> data = new ArrayList<>();
	
	
	public DataArme(String type, List<Arme> data) {
		super();
		this.type = type;
		this.data = data;
	}

	public DataArme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Arme> getData() {
		return data;
	}

	public void setData(List<Arme> data) {
		this.data = data;
	}
	
	

}
