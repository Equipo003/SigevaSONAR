package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Administrador{
	@Id
	ObjectId idAdministrador;
	@Field 
	ObjectId idUsuario;
	
	public Administrador() {
	}
	
	public Administrador(ObjectId idUsuario) {
		this.idUsuario = idUsuario;
		this.idAdministrador = new ObjectId(UUID.randomUUID().toString());
	}
	public void setIdUsuario(ObjectId idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getIdUsuario() {
		return this.getIdUsuario();
	}
	
	public void setIdIdAdministrador(ObjectId idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	
	public ObjectId getIdAdmnistrador() {
		return this.idAdministrador;
	}
}
