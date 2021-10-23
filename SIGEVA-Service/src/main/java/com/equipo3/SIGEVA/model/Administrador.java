package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Administrador {
	@Id
	ObjectId idAdministrador;
	/*@Field 
	ObjectId idUsuario;*/
	
	public Administrador() {
	}
	
	public Administrador(ObjectId idUsuario) {
		this.idAdministrador = idAdministrador;
		//this.idAdministrador = new ObjectId(UUID.randomUUID().toString());
	}
	
	public void setIdUsuario(ObjectId idUsuario) {
		this.idAdministrador = idUsuario;
	}
	
	public ObjectId getIdUsuario() {
		return this.idAdministrador;
	}
	
	/*public void setIdIdAdministrador(ObjectId idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	
	public ObjectId getIdAdmnistrador() {
		return this.idAdministrador;
	}*/
}
