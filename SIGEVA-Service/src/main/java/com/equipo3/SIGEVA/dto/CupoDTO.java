package com.equipo3.SIGEVA.dto;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.CupoException;

public class CupoDTO implements Comparable<CupoDTO> {

	private String uuidCupo;
	private CentroSaludDTO centroSalud;
	private Date fechaYHoraInicio;
	private int tamanoActual;

	public CupoDTO() {
		this.uuidCupo = UUID.randomUUID().toString();
	}

	public CupoDTO(CentroSaludDTO centroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = UUID.randomUUID().toString();
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	public CupoDTO(String uuidCupo, CentroSaludDTO centroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = uuidCupo;
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	public String getUuidCupo() {
		return uuidCupo;
	}

	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	public CentroSaludDTO getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(CentroSaludDTO centroSalud) {
		this.centroSalud = centroSalud;
	}

	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	public int getTamanoActual() {
		return tamanoActual;
	}

	public void setTamanoActual(int tamanoActual) {
		this.tamanoActual = tamanoActual;
	}

	@Override
	public String toString() {
		return "CupoDTO [uuidCupo=" + uuidCupo + ", centroSalud=" + centroSalud + ", fechaYHoraInicio="
				+ fechaYHoraInicio + ", tamanoActual=" + tamanoActual + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(centroSalud, fechaYHoraInicio, tamanoActual, uuidCupo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupoDTO other = (CupoDTO) obj;
		return Objects.equals(centroSalud, other.centroSalud)
				&& Objects.equals(fechaYHoraInicio, other.fechaYHoraInicio) && tamanoActual == other.tamanoActual
				&& Objects.equals(uuidCupo, other.uuidCupo);
	}

	@Override
	public int compareTo(CupoDTO o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}

	public boolean estaLleno(int maximo) { // configuracionCupos.getNumeroPacientes()
		return tamanoActual == maximo;
	}

	public void incrementarTamanoActual(int maximo) throws CupoException {
		if (tamanoActual == maximo) {
			throw new CupoException("Ya no cabían más citas en este cupo.");
		} else {
			tamanoActual++;
		}
	}

	public void decrementarTamanoActual() throws CupoException {
		if (tamanoActual == 0) {
			throw new CupoException("No había contabilizada ninguna cita programada en este cupo.");
		} else {
			tamanoActual--;
		}
	}

}
