package com.cidades.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cidade {

	@Id
	private Long ibgeId;
	
	private String uf;
	
	private String name;
	
	private boolean capital;
	
	private String longitude;
	
	private String latitude;
	
	private String noAccents;
	
	private String alternativeNames;
	
	private String microregion;
	
	private String mesoregion;
	
	public Long getIbgeId() {
		return ibgeId;
	}
	
	public void setIbgeId(Long ibgeId) {
		this.ibgeId = ibgeId;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isCapital() {
		return capital;
	}

	public void setCapital(boolean capital) {
		this.capital = capital;
	}

	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getNoAccents() {
		return noAccents;
	}
	
	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}
	
	public String getAlternativeNames() {
		return alternativeNames;
	}
	
	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}
	
	public String getMicroregion() {
		return microregion;
	}
	
	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}
	
	public String getMesoregion() {
		return mesoregion;
	}
	
	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}
	
	@Override
	public String toString() {
		return ibgeId+ " "+ uf + " " + name + " " + capital + " " + longitude + " " + latitude + " " + noAccents + " " + alternativeNames + " " + microregion + " " + mesoregion + " ";
	}
	
	
	
}
