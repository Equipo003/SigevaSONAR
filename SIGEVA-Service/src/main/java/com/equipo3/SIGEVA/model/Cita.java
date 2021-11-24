package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Cita.
 * Clase que representará la cita que tendrá un paciente en un cupo con una dosis.
 * 
 * @author Equipo3
 *
 */

@Document
public class Cita {

	@Id
	private String uuidCita;

	@Field
	private String uuidCupo;

	@Field
	private String uuidPaciente;

	@Field
	private int dosis;

	/***
	 * Constructor para la creación de citas sin pasar valores de la cita.
	 * Se crea un identificador aleatorio.
	 */
	
	public Cita() {
		this.uuidCita = UUID.randomUUID().toString();
	}

	/***
	 * Constructor para la creación de citas pasando los diferentes valores de la
	 * cita.
	 * 
	 * @param uuidCupo          	   Id del Cupo que va a tener la cita.
	 * @param uuidPaciente             Id del Paciente que va a tener esta cita.
	 * @param dosis				   	   Número de dosis que le toca al paciente en la cita.
	 */
	
	public Cita(String uuidCupo, String uuidPaciente, int dosis) {
		super();
		this.uuidCupo = uuidCupo;
		this.uuidPaciente = uuidPaciente;
		this.dosis = dosis;
	}

	/***
	 * Constructor para la creación de citas pasando los diferentes valores de la
	 * cita.
	 * 
	 * @param uuidCita          	   Id que va a tener la cita.
	 * @param uuidCupo          	   Id del Cupo que va a tener la cita.
	 * @param uuidPaciente             Id del Paciente que va a tener esta cita.
	 * @param dosis				   	   Número de dosis que le toca al paciente en la cita.
	 */
	
	public Cita(String uuidCita, String uuidCupo, String uuidPaciente, int dosis) {
		this.uuidCita = uuidCita;
		this.uuidCupo = uuidCupo;
		this.uuidPaciente = uuidPaciente;
		this.dosis = dosis;
	}

	/***
	 * Método para la devolución del identificador de la cita.
	 * 
	 * @return uuidCita; identificador.
	 */
	
	public String getUuidCita() {
		return uuidCita;
	}

	/***
	* Método para la actualización del identificador de la cita.
	* 
	* @param uuidCita; Identificador cita nuevo.
	*/
	
	public void setUuidCita(String uuidCita) {
		this.uuidCita = uuidCita;
	}

	/***
	 * Método para la devolución del id del cupo de la cita.
	 * 
	 * @return uuidCupo; identificador del cupo correspondiente a la cita.
	 */
	
	public String getUuidCupo() {
		return uuidCupo;
	}

	/***
	* Método para la actualización del id del cupo de la cita.
	* 
	* @param uuidCupo; identificador del cupo correspondiente a la cita.
	*/
	
	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	/***
	 * Método para la devolución del identificador paciente de la cita.
	 * 
	 * @return uuidPaciente; identificador del paciente correspondiente a la cita.
	 */
	
	public String getUuidPaciente() {
		return uuidPaciente;
	}

	/***
	* Método para la actualización del identificador del paciente de la cita.
	* 
	* @param uuidPaciente; identificador del paciente de la cita nuevo.
	*/
	
	public void setUuidPaciente(String uuidPaciente) {
		this.uuidPaciente = uuidPaciente;
	}

	/***
	 * Método para la devolución de la dosis en la cita.
	 * 
	 * @return dosis; dosis correspondiente a la cita.
	 */
	
	public int getDosis() {
		return dosis;
	}

	/***
	* Método para la actualización de la dosis en la cita.
	* 
	* @param dosis; dosis de la cita nueva.
	*/
	
	public void setDosis(int dosis) {
		this.dosis = dosis;
	}

	/***
	 * Método que nos da todo la información de la cita.
	 */
	
	@Override
	public String toString() {
		return "Cita [uuidCita=" + uuidCita + ", uuidCupo=" + uuidCupo + ", uuidPaciente=" + uuidPaciente + ", dosis="
				+ dosis + "]";
	}

}
