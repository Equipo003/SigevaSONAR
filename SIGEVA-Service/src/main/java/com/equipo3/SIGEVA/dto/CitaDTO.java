package com.equipo3.SIGEVA.dto;

import java.util.UUID;

public class CitaDTO implements Comparable<CitaDTO> {

	private String uuidCita;
	private CupoDTO cupo;
	private PacienteDTO paciente;
	private int dosis;

	public CitaDTO() {
		this.uuidCita = UUID.randomUUID().toString();
	}

	public CitaDTO(CupoDTO cupo, PacienteDTO paciente, int dosis) {
		this.uuidCita = UUID.randomUUID().toString();
		this.cupo = cupo;
		this.paciente = paciente;
		this.dosis = dosis;
	}

	public String getUuidCita() {
		return uuidCita;
	}

	public void setUuidCita(String uuidCita) {
		this.uuidCita = uuidCita;
	}

	public CupoDTO getCupo() {
		return cupo;
	}

	public void setCupo(CupoDTO cupo) {
		this.cupo = cupo;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}

	public int getDosis() {
		return dosis;
	}

	public void setDosis(int dosis) {
		this.dosis = dosis;
	}

	@Override
	public int compareTo(CitaDTO o) {
		return this.cupo.compareTo(o.getCupo());
	}

}
