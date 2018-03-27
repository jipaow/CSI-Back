package co.simplon.model;

import java.util.ArrayList;
import java.util.List;

public class DataEnquete {
	private String type = "enquete";
	private List <Enquete> data = new ArrayList<>();
	
	
	
	public DataEnquete() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataEnquete(String type, List<Enquete> data) {
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

	public List<Enquete> getData() {
		return data;
	}

	public void setData(List<Enquete> data) {
		this.data = data;
	}
	
	
	
	

}
