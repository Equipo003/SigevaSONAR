package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Administrador {
	@Id
	String idAdministrador;
	@Field 
	String idUsuario;
	public Administrador(String idUsuario) {
		this.idAdministrador = UUID.randomUUID().toString();
		this.idUsuario = idUsuario;
	}
	
}
