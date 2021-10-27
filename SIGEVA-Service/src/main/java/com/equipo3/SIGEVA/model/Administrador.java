package com.equipo3.SIGEVA.model;

import java.util.UUID;

public class Administrador extends Usuario{

	public Administrador() {
		super();
		this.setIdUsuario(UUID.randomUUID().toString());
	}
}
