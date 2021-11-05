package com.equipo3.SIGEVA.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.equipo3.SIGEVA.controller.Condicionamientos;
import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;

public class Paciente extends Usuario {

	private boolean asignado;
	private List<CupoSimple> cuposAsignados;

	private boolean vacunado;
	private int numVacunas; // Dosis aplicadas. (Modificar solo al pinchar).

	public Paciente() {
		super();
		cuposAsignados = new ArrayList<>();
	}

//	public Paciente(String rol, String centroFK, String username, String correo, String hashPassword, String dni,
//			String nombre, String apellidos, Date fechaNacimiento, String imagen, int numVacunas,
//			List<CupoSimple> cuposAsignados) {
//		super(rol, centroFK, username, correo, hashPassword, dni, nombre, apellidos, fechaNacimiento, imagen);
//		this.numVacunas = numVacunas;
//		this.cuposAsignados = cuposAsignados;
//	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public List<CupoSimple> getCuposAsignados() {
		return cuposAsignados;
	}

	public void setCuposAsignados(List<CupoSimple> cuposAsignados) {
		this.cuposAsignados = cuposAsignados;
	}

	public int getNumCitasPendientes() {
		int count = 0;
		Date hoy = new Date();
		for (int i = 0; i < cuposAsignados.size(); i++) {
			if (hoy.before(cuposAsignados.get(i).getFechaYHoraInicio())) {
				count++;
			}
		}
		return count;
	}

	public void asignarCupo(CupoSimple cupo) throws CupoCitasException {
		if (cuposAsignados.contains(cupo) && Condicionamientos.control()) {
			throw new CupoCitasException("Ya tenía este cupo asignado.");
		} else {
			cuposAsignados.add(cupo);
		}
	}

	public void desasignarCupo(CupoSimple cupo) throws CupoCitasException {
		if (!cuposAsignados.contains(cupo) && Condicionamientos.control()) {
			throw new CupoCitasException("No se contemplaba este cupo.");
		} else {
			cuposAsignados.remove(cupo);
		}
	}

	public int getNumVacunas() {
		return numVacunas;
	}

	public void setNumVacunas(int numVacunas) throws PacienteYaVacunadoException, NumVacunasInvalido {
		if (numVacunas < 0 && Condicionamientos.control()) {
			throw new NumVacunasInvalido("El número de vacunas especificado es inválido.");

		} else if (numVacunas > 2 && Condicionamientos.control()) {
			throw new PacienteYaVacunadoException("El paciente ya estaba totalmente vacunado.");

		} else {
			this.numVacunas = numVacunas;
		}
	}

	public void incrementarNumVacunas() throws PacienteYaVacunadoException { // Solo al pinchar.
		try {
			this.setNumVacunas(this.getNumVacunas() + 1);
			if (this.getNumVacunas() == 2) {
				setVacunado(true);
			}
		} catch (NumVacunasInvalido e) {
			// No saltará.
		}
	}

	public boolean isVacunado() {
		return vacunado;
	}

	public void setVacunado(boolean vacunado) {
		this.vacunado = vacunado;
	}

	@Override
	public String toString() {
		return "Paciente " + super.toString() + " [asignado=" + asignado + ", numVacunas=" + numVacunas
				+ ", cuposAsignados=" + cuposAsignados + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(asignado, cuposAsignados, numVacunas);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return asignado == other.asignado && Objects.equals(cuposAsignados, other.cuposAsignados)
				&& numVacunas == other.numVacunas;
	}

}
