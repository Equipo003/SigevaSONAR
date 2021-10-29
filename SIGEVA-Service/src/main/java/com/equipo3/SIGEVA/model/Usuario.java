package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Usuario {
	@Id
	private String idUsuario;
	@Field
	private String rol;
	@Field
	private String centroSalud;
	@Field
	private String username;
	@Field
	private String correo;
	@Field
	private String hashPassword;
	@Field
	private String dni;
	@Field
	private String nombre;
	@Field
	private String apellidos;
	@Field
	private Date fechaNacimiento;
	@Field
	private String imagen;

	public Usuario() {
		this.idUsuario = UUID.randomUUID().toString();
	}

	public Usuario(String rol, String centroFK, String username, String correo, String hashPassword, String dni,
			String nombre, String apellidos, Date fechaNacimiento, String imagen) {
		this.idUsuario = UUID.randomUUID().toString();
		this.rol = rol;
		this.centroSalud = centroFK;
		this.username = username;
		this.correo = correo;
		this.hashPassword = hashPassword;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.imagen = imagen;
	}

	public Usuario(ObjectId idUsuario) {

	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(String centroSalud) {
		this.centroSalud = centroSalud;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
