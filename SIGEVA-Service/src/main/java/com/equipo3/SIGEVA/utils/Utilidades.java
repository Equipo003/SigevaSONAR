package com.equipo3.SIGEVA.utils;

import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class Utilidades {

    @Autowired
    private UsuarioDao administradorDao;

    private static final String FRASE_USUARIO_EXISTENTE = "El usuario ya existe en la base de datos";


    /**
     * Recurso web para la creación de un Administrador sin restricciones.
     *
     * @param administradorDTO Datos del administrador proporcionados por otro administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */
    public void crearUsuarioAdministradorSinRestricciones(@RequestBody AdministradorDTO administradorDTO) {
        try {
            Administrador administrador = WrapperDTOtoModel.administradorDTOtoAdministrador(administradorDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(administrador.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }

            administradorDao.save(administrador);



        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la creación de un Paciente sin restricciones.
     *
     * @param pacienteDTO Datos del paciente proporcionados por un administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */
    public void crearUsuarioPacienteSinRestricciones(@RequestBody PacienteDTO pacienteDTO) {
        try {
            Paciente paciente = WrapperDTOtoModel.pacienteDTOtoPaciente(pacienteDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }

            administradorDao.save(paciente);



        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la creación de un Paciente sin restricciones.
     *
     * @param sanitarioDTO Datos del paciente proporcionados por un administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */
    public void crearUsuarioSanitarioSinRestricciones(@RequestBody SanitarioDTO sanitarioDTO) {
        try {
            Sanitario sanitario = WrapperDTOtoModel.sanitarioDTOtoSanitario(sanitarioDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }

            administradorDao.save(sanitario);



        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para conseguir la instancia de un Administrador.
     *
     * @param administradorDTO Datos del administrador proporcionados por otro administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */

    public Optional<Usuario> conseguirUsuarioAdministrador(@RequestBody AdministradorDTO administradorDTO) {
        try {

            Administrador administrador = WrapperDTOtoModel.administradorDTOtoAdministrador(administradorDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(administrador.getUsername());
            if (optUsuario.isPresent()) {
                return optUsuario;
            }else {
                return null;
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para conseguir la instancia de un Paciente.
     *
     * @param pacienteDTO Datos del paciente proporcionados por un administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */

    public Optional<Usuario> conseguirUsuarioPaciente(@RequestBody PacienteDTO pacienteDTO) {
        try {

            Paciente paciente = WrapperDTOtoModel.pacienteDTOtoPaciente(pacienteDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
            if (optUsuario.isPresent()) {
                return optUsuario;
            }else {
                return null;
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para conseguir la instancia de un Sanitario.
     *
     * @param sanitarioDTO Datos del sanitario proporcionados por un administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */

    public Optional<Usuario> conseguirUsuarioSanitario(@RequestBody SanitarioDTO sanitarioDTO) {
        try {

            Sanitario sanitario = WrapperDTOtoModel.sanitarioDTOtoSanitario(sanitarioDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
            if (optUsuario.isPresent()) {
                return optUsuario;
            }else {
                return null;
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
