package com.equipo3.SIGEVA.dto;

public class TokenDTO {
    private String idUsuario;
    private String rol;

    public TokenDTO() {
    }

    public TokenDTO(String idUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.rol = rol;
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
}
