package com.equipo3.SIGEVA.model;

import java.util.Objects;
import java.util.UUID;

public class Vacuna {

	private String uuid;
	private String nombre;
	private int diasEntreDosis;
	private int numDosis;

	public Vacuna() {
	}

	public Vacuna(String nombre, int diasEntreDosis, int numDosis) {
		this.uuid = UUID.randomUUID().toString();
		this.nombre = nombre; // "Pfizer"
		this.diasEntreDosis = diasEntreDosis; // 21 (3 semanas)
		this.numDosis = numDosis; // 2
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDiasEntreDosis() {
		return diasEntreDosis;
	}

	public void setDiasEntreDosis(int diasEntreDosis) {
		this.diasEntreDosis = diasEntreDosis;
	}

	public int getNumDosis() {
		return numDosis;
	}

	public void setNumDosis(int numDosis) {
		this.numDosis = numDosis;
	}

	@Override
	public String toString() {
		return "Vacuna [nombre=" + nombre + ", diasEntreDosis=" + diasEntreDosis + ", numDosis=" + numDosis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(diasEntreDosis, nombre, numDosis, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacuna other = (Vacuna) obj;
		return diasEntreDosis == other.diasEntreDosis && Objects.equals(nombre, other.nombre)
				&& numDosis == other.numDosis && Objects.equals(uuid, other.uuid);
	}

}
