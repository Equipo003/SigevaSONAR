package com.equipo3.SIGEVA.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Rol {

	@Id
	private String uuid;

	@Field
	private String nombre;

	public Rol() {

	}

	public Rol(String uuid, String nombre) {
		this.uuid = uuid;
		this.nombre = nombre;
	}

	public Rol(String nombre) {
		this.nombre = nombre;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Rol [nombre=" + nombre + "]";
	}

}
