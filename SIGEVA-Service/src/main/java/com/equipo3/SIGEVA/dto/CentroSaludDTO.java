package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

import java.util.Objects;
import java.util.UUID;

public class CentroSaludDTO {
	private String id;
	private String nombreCentro;
	private int numVacunasDisponibles;
	private String direccion;
	private VacunaDTO vacuna;

	public CentroSaludDTO() {
		this.id = UUID.randomUUID().toString();
	}

	public CentroSaludDTO(String nombreCentro, String direccion, int numVacunasDisponibles) {
		this.id = UUID.randomUUID().toString();
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;
		this.direccion = direccion;
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
		this.numVacunasDisponibles = numVacunasDisponibles;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public VacunaDTO getVacuna() {
		return vacuna;
	}

	public void setVacuna(VacunaDTO vacunaDTO) {
		this.vacuna = vacunaDTO;
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
			if (this.getNumVacunasDisponibles() == 0) {
				throw new CentroSinStock(
						"El centro de salud " + this.getNombreCentro() + " no tiene stock de vacunas.");
			}
			this.setNumVacunasDisponibles(numVacunasDisponibles - 1);
		} catch (CentroSinStock e) {
			throw new CentroSinStock("El centro no dispone de stock.");
		}
	}

	@Override
	public String toString() {
		return "CentroSaludDTO{" + "id='" + id + '\'' + ", nombreCentro='" + nombreCentro + '\''
				+ ", numVacunasDisponibles=" + numVacunasDisponibles + ", direccion='" + direccion + '\'' + ", vacuna="
				+ vacuna.toString() + '}';
	}
}
