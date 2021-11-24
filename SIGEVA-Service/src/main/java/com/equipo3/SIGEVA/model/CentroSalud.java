package com.equipo3.SIGEVA.model;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.equipo3.SIGEVA.exception.CentroSinStock;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;

/***
 * Entidad centro de salud.
 * 
 * @author Equipo3
 *
 */
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
	private String vacuna;

	/***
	 * Constructor del objeto
	 */
	public CentroSalud() {
		this.id = UUID.randomUUID().toString();
	}

	/***
	 * Constructor del objeto con sus respectivos atributos
	 * 
	 * @param nombreCentro          Nombre que tendra el centro de salud.
	 * @param direccion             Dirección que tendrá el centro de salud.
	 * @param numVacunasDisponibles Número de vacunas disponibles que tiene el
	 *                              centro de salud.
	 */
	public CentroSalud(String nombreCentro, String direccion, int numVacunasDisponibles) {
		this.id = UUID.randomUUID().toString();
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;
		this.direccion = direccion;
	}

	/***
	 * Método para la devolución del identificador.
	 * 
	 * @return Id identificador.
	 */
	public String getId() {
		return id;
	}

	/***
	 * Método para la actualización del identificador.
	 * 
	 * @param Id Identificador nuevo.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/***
	 * Método para la devolución del nombre de centro de salud.
	 * 
	 * @return nombreCentro Devolución del nombre de centro de salud.
	 */
	public String getNombreCentro() {
		return nombreCentro;
	}

	/***
	 * Método para la actualización del nombre de centro.
	 * 
	 * @param nombreCentro Nombre nuevo del centro de salud.
	 */
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	/***
	 * Método para la devolución de las dosis disponibles.
	 * 
	 * @return numVacunasDisponibles Dosis disponibles que tiene el centro de salud.
	 */
	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}

	/***
	 * Método para la actualización de dosis disponibles.
	 * 
	 * @param numVacunasDisponibles Nuevas dosis de vacunas que va a tener
	 *                              disponibles el centro de salud.
	 */
	public void setNumVacunasDisponibles(int numVacunasDisponibles) throws NumVacunasInvalido {
		if (numVacunasDisponibles >= 0) {
			this.numVacunasDisponibles = numVacunasDisponibles;
		} else {
			throw new NumVacunasInvalido("La cantidad de stock especificada es inválida.");
		}
	}

	/***
	 * Método para la devolución de las dirección del centro de salud.
	 * 
	 * @return direccion Devuelve la dirección del centro de salud.
	 */
	public String getDireccion() {
		return direccion;
	}

	/***
	 * Método para la actualización de la dirección del centro de salud
	 * 
	 * @param direccion Nueva dirección que va a tener el centro de salud.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/***
	 * Metodo para la devolución del objeto vacuna
	 * 
	 * @return Vacuna que tiene asignada el centro de salud.
	 */
	public String getVacuna() {
		return vacuna;
	}

	/***
	 * Método para la actualización del objeto vacuna en el centro de salud.
	 * 
	 * @param vacunaDTO Vacuna nueva que va tener el centro de salud.
	 */
	public void setVacuna(String vacuna) {
		this.vacuna = vacuna;
	}

	/***
	 * Incrementación del número de dosis de vacunas disponibles que tiene el centro
	 * de salud.
	 * 
	 * @throws CentroSinStock Excepción que salta si el centro de salud no tiene
	 *                        dosis disponibles de vacunas.
	 */
	public void decrementarNumVacunasDisponibles() throws CentroSinStock {
		// No se restará stock hasta que no se confirme como vacunado por un sanitario.
		try {
			this.setNumVacunasDisponibles(numVacunasDisponibles - 1);
		} catch (NumVacunasInvalido e) {
			throw new CentroSinStock("El centro no dispone de stock.");
		}
	}

	/***
	 * Método que nos da todo la información del centro de salud
	 */
	@Override
	public String toString() {
		return "CentroSaludDTO{" + "id='" + id + '\'' + ", nombreCentro='" + nombreCentro + '\''
				+ ", numVacunasDisponibles=" + numVacunasDisponibles + ", direccion='" + direccion + '\'' + ", vacuna="
				+ vacuna + '}';
	}

	/***
	 * Comparador para los centros de salud
	 */
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
