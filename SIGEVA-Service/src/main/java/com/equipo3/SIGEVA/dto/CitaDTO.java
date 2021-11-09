package com.equipo3.SIGEVA.dto;

import java.util.Objects;
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

	public CitaDTO(String uuidCita, CupoDTO cupo, PacienteDTO paciente, int dosis) {
		this.uuidCita = uuidCita;
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
	public String toString() {
		return "CitaDTO [uuidCita=" + uuidCita + ", cupo=" + cupo + ", paciente=" + paciente + ", dosis=" + dosis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cupo, dosis, paciente, uuidCita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CitaDTO other = (CitaDTO) obj;
		return Objects.equals(cupo, other.cupo) && dosis == other.dosis && Objects.equals(paciente, other.paciente)
				&& Objects.equals(uuidCita, other.uuidCita);
	}

	@Override
	public int compareTo(CitaDTO o) {
		return this.cupo.compareTo(o.getCupo());
	}

}
