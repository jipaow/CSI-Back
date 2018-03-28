package co.simplon.model;

import java.util.ArrayList;
import java.util.List;

public class DataAgent {
	String type = "agent";
	List <Agent> data = new ArrayList<>();
	
	
	public DataAgent(String type, List<Agent> data) {
		super();
		this.type = type;
		this.data = data;
	}
	public DataAgent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Agent> getData() {
		return data;
	}
	public void setData(List<Agent> data) {
		this.data = data;
	}
	
	
	

}
