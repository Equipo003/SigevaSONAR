package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Rol;

import java.util.Date;
import java.util.UUID;

public class UsuarioDTO {
    private String idUsuario;
    private Rol rol;
    private CentroSalud centroSalud;
    private String username;
    private String correo;
    private String hashPassword;
    private String dni;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String imagen;

    public UsuarioDTO() {
        this.idUsuario = UUID.randomUUID().toString();
    }

    public UsuarioDTO(Rol rol, CentroSalud centroSalud, String username, String correo,
                      String hashPassword, String dni, String nombre, String apellidos, Date fechaNacimiento,
                      String imagen) {
        this.rol = rol;
        this.centroSalud = centroSalud;
        this.username = username;
        this.correo = correo;
        this.hashPassword = hashPassword;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public CentroSalud getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(CentroSalud centroSalud) {
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
