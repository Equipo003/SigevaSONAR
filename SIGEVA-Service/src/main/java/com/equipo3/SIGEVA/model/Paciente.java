package com.equipo3.SIGEVA.model;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Paciente extends Usuario {
	
	@Field
	private int numDosisAplicadas;

	public Paciente() {
		super();
	}

	public Paciente(int numDosisAplicadas) {
		super();
		this.numDosisAplicadas = numDosisAplicadas;
	}

	public int getNumDosisAplicadas() {
		return numDosisAplicadas;
	}

	public void setNumDosisAplicadas(int numDosisAplicadas) {
		this.numDosisAplicadas = numDosisAplicadas;
	}

	@Override
	public String toString() {
		return "Paciente [" + super.toString() + ", numDosisAplicadas=" + numDosisAplicadas + "]";
	}

}
