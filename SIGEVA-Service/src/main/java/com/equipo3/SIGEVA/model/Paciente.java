package com.equipo3.SIGEVA.model;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;

public class Paciente extends Usuario {

	private int numVacunas;

	public Paciente() {
		super();
	}

	public int getNumVacunas() {
		return numVacunas;
	}

	public void setNumVacunas(int numVacunas) throws PacienteYaVacunadoException, NumVacunasInvalido {
		if (numVacunas < 0) {
			throw new NumVacunasInvalido("El número de vacunas especificado es inválido.");

		} else if (numVacunas > 2) { // TODO // Sustituir 2 por this.getCentroSalud()(FK).getVacuna().getNumDosis()
			throw new PacienteYaVacunadoException("El paciente ya estaba totalmente vacunado.");

		} else {
			this.numVacunas = numVacunas;
		}
	}

	public void incrementarNumVacunas() throws PacienteYaVacunadoException {
		try {
			this.setNumVacunas(this.getNumVacunas() + 1);
		} catch (NumVacunasInvalido e) {
			// No saltará.
		}
	}

}
