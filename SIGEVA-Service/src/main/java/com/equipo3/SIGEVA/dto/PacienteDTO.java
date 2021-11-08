package com.equipo3.SIGEVA.dto;

import java.util.Objects;

import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;

public class PacienteDTO extends UsuarioDTO {

	private int numDosisAplicadas;

	public PacienteDTO() {
		super();
	}

	public PacienteDTO(int numDosisAplicadas) {
		super();
		this.numDosisAplicadas = numDosisAplicadas;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(numDosisAplicadas);
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
		PacienteDTO other = (PacienteDTO) obj;
		return numDosisAplicadas == other.numDosisAplicadas;
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
