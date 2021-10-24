package com.equipo3.SIGEVA.model;
import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CentroSalud {
	@Field
	@Id
	private ObjectId id = new ObjectId();
	@Field
	private String nombreCentro;
	@Field
	private int numVacunasDisponibles;
	@Field
	private String direccion;
	public CentroSalud() {

	}

	public String getNombreCentro() {
		return nombreCentro;
	}

	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	public ObjectId getIdCentroSalud() {
		return id;
	}
	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}
	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		if(numVacunasDisponibles<0)
			numVacunasDisponibles=0;
		this.numVacunasDisponibles = numVacunasDisponibles;
	}
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public boolean horaCierreMayorApertura( Date horaApertura,Date horaCierre) {
		if(horaCierre.after(horaApertura))
			return true;
		return false;
	}
	
}
