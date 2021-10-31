package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CupoSimple implements Comparable<CupoSimple> {

	@Id
	private String uuid;

	@Field
	private CentroSalud centroSalud;

	@Field
	private Date fechaYHoraInicio;

	public CupoSimple() {

	}

	public CupoSimple(CentroSalud centroSalud, Date fechaYHoraInicio) {
		this.uuid = UUID.randomUUID().toString();
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	public CupoSimple(String uuid, CentroSalud centroSalud, Date fechaYHoraInicio) {
		this.uuid = uuid;
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public CentroSalud getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(CentroSalud centroSalud) {
		this.centroSalud = centroSalud;
	}

	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	@Override
	public String toString() {
		return "CupoSimple [uuid=" + uuid + ", centroSalud=" + centroSalud + ", fechaYHoraInicio=" + fechaYHoraInicio
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(centroSalud, fechaYHoraInicio, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupoSimple other = (CupoSimple) obj;
		return Objects.equals(centroSalud, other.centroSalud)
				&& Objects.equals(fechaYHoraInicio, other.fechaYHoraInicio) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public int compareTo(CupoSimple o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}

	public static CupoSimple createCupoSimple(CupoCitas cupoCompleto) {
		return new CupoSimple(cupoCompleto.getUuid(), cupoCompleto.getCentroSalud(),
				cupoCompleto.getFechaYHoraInicio());
	}

}
