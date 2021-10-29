package com.equipo3.SIGEVA.model;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CentroSalud {
	@Field
	@Id
	private String id;
	@Field
	private String nombreCentro;
	@Field 
	private int numVacunasDisponibles;
	@Field
	private String direccion;
	public CentroSalud() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreCentro() {
		return nombreCentro;
	}

	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	public String getIdCentroSalud() {
		return id;
	}

	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}

	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		if (numVacunasDisponibles < 0)
			numVacunasDisponibles = 0;
		this.numVacunasDisponibles = numVacunasDisponibles;
	}
	
	public void modificarStockVacunas(int numVacunasAgregadas) {
		this.numVacunasDisponibles += numVacunasAgregadas;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean horaCierreMayorApertura(Date horaApertura, Date horaCierre) {
		return horaCierre.after(horaApertura);
	}

	@Override
	public String toString() {
		return "CentroSalud [id=" + id + ", nombreCentro=" + nombreCentro + ", numVacunasDisponibles="
				+ numVacunasDisponibles + ", direccion=" + direccion + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(direccion, id, nombreCentro, numVacunasDisponibles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentroSalud other = (CentroSalud) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(id, other.id)
				&& Objects.equals(nombreCentro, other.nombreCentro)
				&& numVacunasDisponibles == other.numVacunasDisponibles;
	}

}
