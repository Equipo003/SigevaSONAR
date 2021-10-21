package com.isoEquipo3.SIGEVA.Controllers;

import com.isoEquipo3.SIGEVA.Dao.AdministradorDao;
import com.isoEquipo3.SIGEVA.Model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AdministradorController {
	@Autowired
	AdministradorDao administradorDao;
	
	public void registrarUsuario(Usuario administrador) {
        try {
        	administradorDao.save(administrador);
        	System.out.print("Hola");

        } catch (Exception e) {
        	e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }

    }
}
