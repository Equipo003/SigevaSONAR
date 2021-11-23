package com.equipo3.SIGEVA.dto;

public class UsuarioLoginDTO {
    private String username;
    private String hasPassword;

    public UsuarioLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hasPassword;
    }

    public void setHashPassword(String hasPassword) {
        this.hasPassword = hasPassword;
    }


}
