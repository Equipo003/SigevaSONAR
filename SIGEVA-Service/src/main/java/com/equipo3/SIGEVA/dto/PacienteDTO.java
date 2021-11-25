package com.equipo3.SIGEVA.dto;

import java.util.Objects;

import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;

/***
 * Objeto Paciente data transfer object, es el que se usa en las peticiones
 * entre front end y back end hereda de usuario.
 * 
 * @author Equipo3
 *
 */
public class PacienteDTO extends UsuarioDTO {

	private int numDosisAplicadas;

	/***
	 * Constructor del objeto paciente.
	 */
	public PacienteDTO() {
		super();
	}

	/***
	 * Método que devuelve el número de dosis que tiene un paciente aplicadas.
	 * 
	 * @return numDosisAplicadas Número de dosis que tiene un paciente.
	 */
	public int getNumDosisAplicadas() {
		return numDosisAplicadas;
	}

	/***
	 * Método para la modificación de las dosis que tiene un paciente.
	 * 
	 * @param numDosisAplicadas Nuevo número de dosis que tiene el paciente.
	 */
	public void setNumDosisAplicadas(int numDosisAplicadas) {
		this.numDosisAplicadas = numDosisAplicadas;
	}

	/***
	 * Obtención de toda la información del paciente.
	 */
	@Override
	public String toString() {
		return "PacienteDTO [" + super.toString() + ", numDosisAplicadas=" + numDosisAplicadas + "]";
	}

	/***
	 * Incrementación de las dosis que tiene el paciente.
	 * 
	 * @throws PacienteYaVacunadoException Excepción que salta si el paciente esta
	 *                                     vacunasdo y se intenta a aumnetar las
	 *                                     dosis que tiene.
	 */
	public void incrementarNumDosisAplicadas() throws PacienteYaVacunadoException {
		if (numDosisAplicadas == 2)
			throw new PacienteYaVacunadoException("El paciente ya estaba vacunado de las dos dosis.");
		else
			numDosisAplicadas++;
	}

	/***
	 * Método que devuelve si un paciente esta vacunado
	 * 
	 * @return Boolean si ya tiene las dos dosis.
	 */
	public boolean isTotalmenteVacunado() {
		return numDosisAplicadas == 2;
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

}
