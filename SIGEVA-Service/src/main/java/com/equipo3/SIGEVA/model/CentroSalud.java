package com.equipo3.SIGEVA.model;
import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CentroSalud {
	@Field
	//private ObjectId idCentroSalud =  new ObjectId(UUID.randomUUID().toString());
	private String idCentroSalud = UUID.randomUUID().toString();
	@Field
	private int numVacunasDisponibles;
	@Field
	private Date horaApertura;
	@Field
	private Date horaCierre;
	@Field
	private String direccion;
	public CentroSalud() {
		
	}
	
//	public CentroSalud(String idCentroSalud, int numVacunasDisponibles, Date horaApertura, Date horaCierre,
//			String direccion) {
//		this.idCentroSalud = idCentroSalud;
//		this.numVacunasDisponibles = numVacunasDisponibles;
//		this.horaApertura = horaApertura;
//		this.horaCierre = horaCierre;
//		this.direccion = direccion;
//	}

	public String getIdCentroSalud() {
		return idCentroSalud;
	}
	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}
	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		if(numVacunasDisponibles<0)
			numVacunasDisponibles=0;
		this.numVacunasDisponibles = numVacunasDisponibles;
	}
	public Date getHoraApertura() {
		return horaApertura;
	}
	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}
	public Date getHoraCierre() {
		return horaCierre;
	}
	public void setHoraCierre(Date horaCierre) {
		if(horaCierreMayorApertura(this.horaApertura, horaCierre))
			this.horaCierre = horaCierre;
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
