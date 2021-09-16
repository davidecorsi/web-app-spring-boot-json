package it.partec.webappspringbootjson.dto;

import java.io.Serializable;

public class Errore implements Serializable {
	
	private String status;
	private String error;

	public Errore() {
	}
	
	public Errore(String status, String error) {
		this.status = status;
		this.error = error;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
