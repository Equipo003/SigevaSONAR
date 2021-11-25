package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Cupo. Clase que representará el cupo al que estarán asociados otras
 * entidades para vacunar.
 * 
 * 
 * @author Equipo3
 *
 */

@Document
public class Cupo implements Comparable<Cupo> {

	@Id
	private String uuidCupo;

	@Field
	private String uuidCentroSalud;

	@Field
	private Date fechaYHoraInicio;

	@Field
	private int tamanoActual;

	/***
	 * Constructor para la creación de cupos sin pasar valores del cupo.
	 * Se crea un identificador aleatorio.
	 */
	
	public Cupo() {
		this.uuidCupo = UUID.randomUUID().toString();
	}

	/***
	 * Constructor para la creación de objetos cuposDTO pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param centroSalud           Centro de salud DTO al que va a estar asociado un cupo.
	 * @param fechaYHoraInicio      Fecha y hora en la que se va a iniciar el cupo.
	 * @param tamanoActual 			Tamaño que tendrá el cupo para gestionar citas de vacunas.
	 */
	
	public Cupo(String uuidCentroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = UUID.randomUUID().toString();
		this.uuidCentroSalud = uuidCentroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	/***
	 * Constructor para la creación de objetos cuposDTO pasando los diferentes valores del
	 * objeto.
	 * 
	 * @param uuidCupo           	Id del cupo.
	 * @param centroSalud           Centro de salud DTO al que va a estar asociado un cupo.
	 * @param fechaYHoraInicio      Fecha y hora en la que se va a iniciar el cupo.
	 * @param tamanoActual 			Tamaño que tendrá el cupo para gestionar citas de vacunas.
	 */
	
	public Cupo(String uuidCupo, String uuidCentroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = uuidCupo;
		this.uuidCentroSalud = uuidCentroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	/***
	 * Método para la devolución del identificador del cupo.
	 * 
	 * @return uuidCupo; identificador del cupo.
	 */
	
	public String getUuidCupo() {
		return uuidCupo;
	}

	/***
	* Método para la actualización del identificador del cupo.
	* 
	* @param uuidCupo; Identificador del cupo nuevo.
	*/
	
	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	/***
	 * Método para la devolución del identificador del centro de salud del cupo.
	 * 
	 * @return uuidCentroSalud; identificador del centro de salud del cupo.
	 */
	
	public String getUuidCentroSalud() {
		return uuidCentroSalud;
	}

	/***
	 * Método para la actualización del identificador del centro de salud del cupo.
	 * 
	 * @param uuidCentroSalud; identificador del centro de salud del cupo.
	 */
	
	public void setUuidCentroSalud(String uuidCentroSalud) {
		this.uuidCentroSalud = uuidCentroSalud;
	}

	/***
	 * Método para la devolución de la fecha y la hora de inicio del cupo.
	 * 
	 * @return fechaYHoraInicio; fecha y la hora de inicio del cupo.
	 */
	
	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	/***
	 * Método para la actualización de la fecha y la hora de inicio del cupo.
	 * 
	 * @param fechaYHoraInicio; fecha y la hora de inicio del cupo.
	 */
	
	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	/***
	 * Método para la devolución del tamaño del cupo.
	 * 
	 * @return tamanoActual; tamaño del cupo.
	 */
	
	public int getTamanoActual() {
		return tamanoActual;
	}

	/***
	 * Método para la actualización del tamaño del cupo.
	 * 
	 * @param tamanoActual; tamaño del cupo.
	 */
	
	public void setTamanoActual(int tamanoActual) {
		this.tamanoActual = tamanoActual;
	}

	/***
	 * Método que nos da todo la información de la vacuna.
	 */
	
	@Override
	public String toString() {
		return "Cupo [uuidCupo=" + uuidCupo + ", uuidCentroSalud=" + uuidCentroSalud + ", fechaYHoraInicio="
				+ fechaYHoraInicio + ", tamanoActual=" + tamanoActual + "]";
	}

	/***
   	 * Método para la comparación de cupos.
   	 * 
   	 * @param o; cupo a comparar.
   	 */
	
	@Override
	public int compareTo(Cupo o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}
	
	/***
	 * Método que cifra la información mediante el algoritmo de hash.
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(fechaYHoraInicio, tamanoActual, uuidCentroSalud, uuidCupo);
	}

	/***
   	 * Método para la comparación de objetos.
   	 * 
   	 * @param obj; objeto a comparar.
   	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cupo other = (Cupo) obj;
		return Objects.equals(fechaYHoraInicio, other.fechaYHoraInicio) && tamanoActual == other.tamanoActual
				&& Objects.equals(uuidCentroSalud, other.uuidCentroSalud) && Objects.equals(uuidCupo, other.uuidCupo);
	}

}
