package com.equipo3.SIGEVA.model;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Paciente extends Usuario {
	
	@Field
	private String numDosisAplicadas;

	public Paciente() {
		super();
	}

	public Paciente(String numDosisAplicadas) {
		super();
		this.numDosisAplicadas = numDosisAplicadas;
	}

	public String getNumDosisAplicadas() {
		return numDosisAplicadas;
	}

	public void setNumDosisAplicadas(String dosis) {
		this.numDosisAplicadas = dosis;
	}

	@Override
	public String toString() {
		return "Paciente [" + super.toString() + ", numDosisAplicadas=" + numDosisAplicadas + "]";
	}

}
