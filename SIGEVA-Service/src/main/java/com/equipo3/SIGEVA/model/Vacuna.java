package com.equipo3.SIGEVA.model;

import java.util.Objects;

public class Vacuna {

	private String nombre;
	private int tiempoEntreVacuna;
	private int numDosis;

	public Vacuna() {

	}

	public Vacuna(String nombre, int tiempoEntreVacuna, int numDosis) {
		this.nombre = "Pfizer";
		this.tiempoEntreVacuna = 21;
		this.numDosis = 2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempoEntreVacuna() {
		return tiempoEntreVacuna;
	}

	public void setTiempoEntreVacuna(int tiempoEntreVacuna) {
		this.tiempoEntreVacuna = tiempoEntreVacuna;
	}

	public int getNumDosis() {
		return numDosis;
	}

	public void setNumDosis(int numDosis) {
		this.numDosis = numDosis;
	}

	@Override
	public String toString() {
		return "Vacuna [nombre=" + nombre + ", tiempoEntreVacuna=" + tiempoEntreVacuna + ", numDosis=" + numDosis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, numDosis, tiempoEntreVacuna);
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
		return Objects.equals(nombre, other.nombre) && numDosis == other.numDosis
				&& tiempoEntreVacuna == other.tiempoEntreVacuna;
	}

}
