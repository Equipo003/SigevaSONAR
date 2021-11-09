package com.equipo3.SIGEVA.dto;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class UsuarioDTO {
    private String idUsuario;
    private RolDTO rolDTO;
    private CentroSaludDTO centroSaludDTO;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public RolDTO getRol() {
        return rolDTO;
    }

    public void setRol(RolDTO rolDTO) {
        this.rolDTO = rolDTO;
    }

    public CentroSaludDTO getCentroSalud() {
        return centroSaludDTO;
    }

    public void setCentroSalud(CentroSaludDTO centroSaludDTO) {
        this.centroSaludDTO = centroSaludDTO;
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

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario='" + idUsuario + '\'' +
                ", rol=" + rolDTO.toString() +
                ", centroSalud=" + centroSaludDTO +
                ", username='" + username + '\'' +
                ", correo='" + correo + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", imagen='" + imagen + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, centroSaludDTO, correo, dni, fechaNacimiento, hashPassword, idUsuario, imagen,
				nombre, rolDTO, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(centroSaludDTO, other.centroSaludDTO)
				&& Objects.equals(correo, other.correo) && Objects.equals(dni, other.dni)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(hashPassword, other.hashPassword) && Objects.equals(idUsuario, other.idUsuario)
				&& Objects.equals(imagen, other.imagen) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(rolDTO, other.rolDTO) && Objects.equals(username, other.username);
	}
    
}
