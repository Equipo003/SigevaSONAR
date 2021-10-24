package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.Administrador;

import com.equipo3.SIGEVA.model.CentroSalud;

import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Date;
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

    @Autowired
    private CentroSaludDao centroSaludDao;

	@Autowired
	private ConfiguracionCuposDao configCuposDao;

    
   @PostMapping("/crearUsuario")
   public void registrarUsuario(@RequestBody Usuario usuario) {
    	try {
    		System.out.println(usuario.getRol());
    		Optional<Rol> rol = roldao.findById(usuario.getRol());
    		Rol rolEleg = null;

    		if(rol.isPresent()) {
    			rolEleg = rol.get();
    		}
    		
    		usuariodao.save(usuario);
    		System.out.println(usuario.getIdUsuario());
    		if(rolEleg.getNombre().replace(" ", "").equals("Administrador")) {
    			Administrador admin = new Administrador();
    			admin.setIdUsuario(usuario.getIdUsuario());
    			administradordao.save(admin);
    		}
    		
    	}catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    	}
    }
   
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/newCentroSalud")
    public CentroSalud crearCentroSalud(@RequestBody CentroSalud conf) {
    //	boolean coincide=false;

        try {
//        	CentroSalud centroSalud = new CentroSalud();
//        	centroSalud.setDireccion("calle");
//    		centroSalud.setNumVacunasDisponibles(2);
    		centroSaludDao.save(conf);
//    		List<CentroSalud> centrosSaludList = centroSaludDao.findAll();
//    		for(int i =0; i<centrosSaludList.size();i++) {
//    			if(centrosSaludList.get(i).getIdCentroSalud().equals(centroSalud.getIdCentroSalud())) {
//    				System.out.println("COINCIDE");
//        			coincide = true;
//    			}
//    		}
//    		if(!coincide) {
//    			centroSaludDao.save(centroSalud);
//    		}else {
//    			throw new RuntimeException("MISMO ID");
//    		}
    		return conf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
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

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearConfCupos")
	public ConfiguracionCupos crearConfiguracionCupos(@RequestBody ConfiguracionCupos conf){
		List<ConfiguracionCupos> configuracionCuposList = configCuposDao.findAll();
		if(configuracionCuposList.size() == 0)
			configCuposDao.save(conf);
		else
			System.out.println("Ya existe una configuracion");
		return conf;
	}
}
