package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco>{
	Artist a1;
	Artist a2;
	Integer peso;
	public Arco(Artist a1, Artist a2, Integer peso)  {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Artist getA1() {
		return a1;
	}
	public void setA1(Artist a1) {
		this.a1 = a1;
	}
	public Artist getA2() {
		return a2;
	}
	public void setA2(Artist a2) {
		this.a2 = a2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	@Override
	public int compareTo(Arco o) {
		
		return o.peso-this.peso ;
	}
	
}
