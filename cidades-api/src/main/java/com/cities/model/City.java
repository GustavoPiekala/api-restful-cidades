package com.cities.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class City {

	@Id
	private Long ibgeId;
	
	@NotBlank
	private String uf;
	
	@NotBlank
	private String name;
	
	private boolean capital;
	
	@NotNull
	private double longitude;
	
	@NotNull
	private double latitude;
	
	@NotBlank
	private String noAccents;
	
	private String alternativeNames;
	
	@NotBlank
	private String microregion;
	
	@NotBlank
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

	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
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
