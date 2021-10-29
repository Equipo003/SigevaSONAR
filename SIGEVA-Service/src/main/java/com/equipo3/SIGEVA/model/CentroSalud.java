package com.equipo3.SIGEVA.model;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
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
	@Field
	private Vacuna vacuna;
	public CentroSalud() {
		this.id = UUID.randomUUID().toString();
		this.vacuna = new Vacuna("Pfizer", 21, 2);
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

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	@Override
	public String toString() {
		return "CentroSalud [id=" + id + ", nombreCentro=" + nombreCentro + ", numVacunasDisponibles="
				+ numVacunasDisponibles + ", direccion=" + direccion + ", vacuna=" + vacuna + "]";
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
