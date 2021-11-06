package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.model.*;

public class WrapperDTOtoModel {

    public static Administrador administradorDTOtoAdministrador(AdministradorDTO administradorDTO){
        Administrador administrador = new Administrador();

        administrador.setIdUsuario(administradorDTO.getIdUsuario());
        administrador.setRol(administradorDTO.getRol().getId());
        administrador.setCentroSalud(administradorDTO.getCentroSalud().getId());
        administrador.setUsername(administradorDTO.getUsername());
        administrador.setCorreo(administradorDTO.getCorreo());
        administrador.setHashPassword(administradorDTO.getHashPassword());
        administrador.setDni(administradorDTO.getDni());
        administrador.setNombre(administradorDTO.getNombre());
        administrador.setApellidos(administradorDTO.getApellidos());
        administrador.setFechaNacimiento(administradorDTO.getFechaNacimiento());
        administrador.setImagen(administradorDTO.getImagen());

        return administrador;
    }

    public static Sanitario sanitarioDTOtoSanitario(SanitarioDTO sanitarioDTO){
        Sanitario sanitario = new Sanitario();

        sanitario.setIdUsuario(sanitarioDTO.getIdUsuario());
        sanitario.setRol(sanitarioDTO.getRol().getId());
        sanitario.setCentroSalud(sanitarioDTO.getCentroSalud().getId());
        sanitario.setUsername(sanitarioDTO.getUsername());
        sanitario.setCorreo(sanitarioDTO.getCorreo());
        sanitario.setHashPassword(sanitarioDTO.getHashPassword());
        sanitario.setDni(sanitarioDTO.getDni());
        sanitario.setNombre(sanitarioDTO.getNombre());
        sanitario.setApellidos(sanitarioDTO.getApellidos());
        sanitario.setFechaNacimiento(sanitarioDTO.getFechaNacimiento());
        sanitario.setImagen(sanitarioDTO.getImagen());

        return sanitario;
    }

    public static Paciente pacienteDTOtoPaciente(PacienteDTO pacienteDTO){
        Paciente paciente = new Paciente();

        paciente.setIdUsuario(pacienteDTO.getIdUsuario());
        paciente.setRol(pacienteDTO.getRol().getId());
        paciente.setCentroSalud(pacienteDTO.getCentroSalud().getId());
        paciente.setUsername(pacienteDTO.getUsername());
        paciente.setCorreo(pacienteDTO.getCorreo());
        paciente.setHashPassword(pacienteDTO.getHashPassword());
        paciente.setDni(pacienteDTO.getDni());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellidos(pacienteDTO.getApellidos());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setImagen(pacienteDTO.getImagen());

        return paciente;
    }

    public static CentroSalud centroSaludDTOtoCentroSalud(CentroSaludDTO centroSaludDTO) throws NumVacunasInvalido {
        CentroSalud centroSalud = new CentroSalud();
        centroSalud.setNombreCentro(centroSaludDTO.getNombreCentro());
        centroSalud.setDireccion(centroSaludDTO.getDireccion());
        centroSalud.setNumVacunasDisponibles(centroSaludDTO.getNumVacunasDisponibles());

        return centroSalud;
    }

    public static Rol rolDTOToRol(RolDTO rolDTO){
        Rol rol = new Rol();
        rol.setId(rolDTO.getId());
        rol.setNombre(rolDTO.getNombre());

        return rol;
    }
}
