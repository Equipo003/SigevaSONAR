package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class AdministradorController {
	
	@Autowired
    private AdministradorDao administradordao;
    @Autowired
    private UsuarioDao usuariodao;
    @Autowired
    private RolDao roldao;
    
   @PostMapping("/crearUsuario")
   public void registrarUsuario(@RequestBody Usuario usuario) {
    	try {
    		Optional<Rol> rol = roldao.findById(usuario.getRol());
    		Rol rolEleg = null;
    		if(rol.isPresent()) {
    			rolEleg = rol.get();
    		}
    		if(rolEleg.getNombre() == "Administrador") {
    			Administrador admin = new Administrador(usuario.getIdUsuario());
    			administradordao.save(admin);
    		}
    		usuariodao.save(usuario);
    	}catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    	}
    }
    
   /* @PostMapping("/crearAdministradores")
    public void registrarAdministrador(@RequestBody Administrador usuario) {
     	try {
     		administradordao.save(usuario);
     		usuariodao.save(usuario);
     	}catch (Exception e) {
     		throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
     	}
     }*/
    
    @GetMapping("/getRoles")
    public List<Rol> ListarRoles() {
    	try {
			return roldao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
    }
}
