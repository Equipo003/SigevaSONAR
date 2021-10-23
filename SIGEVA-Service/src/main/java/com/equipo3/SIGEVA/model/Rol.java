package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Rol {
	@Id
	ObjectId id;
	@Field 
	String nombre;
	
	public Rol(String nombre) {
		this.id = new ObjectId(UUID.randomUUID().toString());
		this.nombre = nombre;
	}
	
	public Rol() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
