package it.polito.tdp.artsmia.model;

public class Artist {
	int ID;
	String name;
	public Artist(int iD, String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Artist [ID=" + ID + ", name=" + name + "]";
	}
	
	
}
