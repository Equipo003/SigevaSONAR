package com.equipo3.SIGEVA.dto;

import java.util.Objects;

import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;

public class PacienteDTO extends UsuarioDTO {

	private int numDosisAplicadas;

	public PacienteDTO() {
		super();
	}

	public int getNumDosisAplicadas() {
		return numDosisAplicadas;
	}

	public void setNumDosisAplicadas(int numDosisAplicadas) {
		this.numDosisAplicadas = numDosisAplicadas;
	}

	@Override
	public String toString() {
		return "PacienteDTO [" + super.toString() + ", numDosisAplicadas=" + numDosisAplicadas + "]";
	}

	public void incrementarNumDosisAplicadas() throws PacienteYaVacunadoException {
		if (numDosisAplicadas == 2)
			throw new PacienteYaVacunadoException("El paciente ya estaba vacunado de las dos dosis.");
		else
			numDosisAplicadas++;
	}

	public boolean isTotalmenteVacunado() {
		return numDosisAplicadas == 2;
	}

}
