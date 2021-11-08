package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Rol {
	@Id
	@Field
	String id;
	@Field 
	String nombre;
	
	public Rol() {
		this.id =UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol{" +
				"id='" + id + '\'' +
				", nombre='" + nombre + '\'' +
				'}';
	}
}
