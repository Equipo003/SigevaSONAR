package com.equipo3.SIGEVA.model;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

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

	public static final int TIEMPO_ENTRE_DOSIS = 21; // dias
	public static final int NUM_DOSIS = 2;
	
	public CentroSalud() {
		this.id = UUID.randomUUID().toString();
		this.vacuna = new Vacuna("Pfizer", TIEMPO_ENTRE_DOSIS, NUM_DOSIS);
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

	public void setNumVacunasDisponibles(int numVacunasDisponibles) throws NumVacunasInvalido {
		if (numVacunasDisponibles >= 0) {
			this.numVacunasDisponibles = numVacunasDisponibles;
		} else {
			throw new NumVacunasInvalido("La cantidad de stock especificada es inválida.");
		}
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

	public void modificarStockVacunas(int numVacunasAgregadas) {
		this.numVacunasDisponibles += numVacunasAgregadas;
	}

	public void incrementarNumVacunasDisponibles(int cantidad) throws NumVacunasInvalido {
		if (cantidad >= 0)
			this.setNumVacunasDisponibles(this.getNumVacunasDisponibles() + cantidad);
		else
			throw new NumVacunasInvalido("La cantidad a incrementar especificada es inválida.");
	}

	public void decrementarNumVacunasDisponibles() throws CentroSinStock {
		// No se restará stock hasta que no se confirme como vacunado por un sanitario.
		try {
			this.setNumVacunasDisponibles(numVacunasDisponibles - 1);
		} catch (NumVacunasInvalido e) {
			throw new CentroSinStock("El centro no dispone de stock.");
		}
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
