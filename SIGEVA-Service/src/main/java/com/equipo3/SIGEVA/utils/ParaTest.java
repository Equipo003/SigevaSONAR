package com.equipo3.SIGEVA.utils;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.Usuario;

@CrossOrigin
@RestController
public class ParaTest {
	
	 @Autowired
	 private UsuarioDao administradorDao;
	 @Autowired
	 private WrapperModelToDTO wrapperModelToDTO;
	
	   /**
     * Recurso web para la obtención de un usuario a partir de su identificador.
     *
     * @param idUsuario Identificador de usuario que se quiere obtener de la bbdd.s
     * @return UsuarioDTO Usuario obtenido de la bbdd a partir de su identificador.
	 * @throws IdentificadorException 
     */
    @GetMapping("/getUsuarioById")
    public UsuarioDTO getUsuarioById(@RequestParam String idUsuario) throws IdentificadorException {
        try {
            Optional<Usuario> optUsuario = administradorDao.findById(idUsuario);
            if (optUsuario.isPresent()) {
                return wrapperModelToDTO.usuarioToUsuarioDTO(optUsuario.get());
            }
            return null;
        } catch (Exception e) {
        	throw new IdentificadorException("Identificador Usuario " + idUsuario + " no encontrado");
        }
    }
}
