package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Cita {

	@Id
	private String uuidCita;

	@Field
	private String uuidCupo;

	@Field
	private String uuidPaciente;

	@Field
	private int dosis;

	public Cita() {
		this.uuidCita = UUID.randomUUID().toString();
	}

	public Cita(String uuidCupo, String uuidPaciente, int dosis) {
		super();
		this.uuidCupo = uuidCupo;
		this.uuidPaciente = uuidPaciente;
		this.dosis = dosis;
	}

	public Cita(String uuidCita, String uuidCupo, String uuidPaciente, int dosis) {
		this.uuidCita = uuidCita;
		this.uuidCupo = uuidCupo;
		this.uuidPaciente = uuidPaciente;
		this.dosis = dosis;
	}

	public String getUuidCita() {
		return uuidCita;
	}

	public void setUuidCita(String uuidCita) {
		this.uuidCita = uuidCita;
	}

	public String getUuidCupo() {
		return uuidCupo;
	}

	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	public String getUuidPaciente() {
		return uuidPaciente;
	}

	public void setUuidPaciente(String uuidPaciente) {
		this.uuidPaciente = uuidPaciente;
	}

	public int getDosis() {
		return dosis;
	}

	public void setDosis(int dosis) {
		this.dosis = dosis;
	}

	@Override
	public String toString() {
		return "Cita [uuidCita=" + uuidCita + ", uuidCupo=" + uuidCupo + ", uuidPaciente=" + uuidPaciente + ", dosis="
				+ dosis + "]";
	}

}
