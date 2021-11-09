package com.equipo3.SIGEVA.dto;

import java.util.Objects;
import java.util.UUID;

public class VacunaDTO {
    private String id;
    private String nombre;
    private int diasEntreDosis;
    private int numDosis;

    public VacunaDTO(){
        this.id = UUID.randomUUID().toString();
    }

    public VacunaDTO(String nombre, int diasEntreDosis, int numDosis){
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.diasEntreDosis = diasEntreDosis;
        this.numDosis = numDosis;
    }
    
    public VacunaDTO(String id, String nombre, int diasEntreDosis, int numDosis) {
		this.id = id;
		this.nombre = nombre;
		this.diasEntreDosis = diasEntreDosis;
		this.numDosis = numDosis;
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

    public int getDiasEntreDosis() {
        return diasEntreDosis;
    }

    public void setDiasEntreDosis(int diasEntreDosis) {
        this.diasEntreDosis = diasEntreDosis;
    }

    public int getNumDosis() {
        return numDosis;
    }

    public void setNumDosis(int numDosis) {
        this.numDosis = numDosis;
    }

    @Override
    public String toString() {
        return "VacunaDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", diasEntreDosis=" + diasEntreDosis +
                ", numDosis=" + numDosis +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(diasEntreDosis, id, nombre, numDosis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VacunaDTO other = (VacunaDTO) obj;
		return diasEntreDosis == other.diasEntreDosis && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre) && numDosis == other.numDosis;
	}
    
}
