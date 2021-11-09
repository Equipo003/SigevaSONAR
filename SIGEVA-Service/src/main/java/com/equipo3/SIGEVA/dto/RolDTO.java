package com.equipo3.SIGEVA.dto;

import java.util.Objects;
import java.util.UUID;

public class RolDTO {

    String id;
    String nombre;

    public RolDTO() {
        this.id = UUID.randomUUID().toString();
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
        return "RolDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolDTO other = (RolDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}
    
}
