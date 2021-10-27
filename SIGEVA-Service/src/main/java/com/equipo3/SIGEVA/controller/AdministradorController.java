package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.*;
import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class AdministradorController {

	@Autowired
	private AdministradorDao administradorDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private ConfiguracionCuposDao configCuposDao;
	@Autowired
	private CentroSaludDao centroSaludDao;


	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioAdministrador")
	public void crearUsuarioAdministrador(@RequestBody Administrador admin) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(admin.getUsername());
			if (optUsuario.isPresent()){
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe en la base de datos");
			}

			administradorDao.save(admin);

		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioPaciente")
	public void crearUsuarioPaciente(@RequestBody Paciente paciente) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
			if (optUsuario.isPresent()){
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe en la base de datos");
			}

			administradorDao.save(paciente);

		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioSanitario")
	public void crearUsuarioSanitario(@RequestBody Sanitario sanitario) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
			if (optUsuario.isPresent()){
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe en la base de datos");
			}

			administradorDao.save(sanitario);

		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/newCentroSalud")
	public void crearCentroSalud(@RequestBody CentroSalud conf) {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

	@PostMapping("/registrarRol")
	public void registrarRol(@RequestBody Rol rol){
		try {
			rolDao.save(rol);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getRoles")
	public List<Rol> ListarRoles() {
		try {
			return rolDao.findAll();
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
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/listCentroSalud")
	public List<CentroSalud> listarCentrosSalud(){
		try {
			return centroSaludDao.findAll();
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
		}
	}
	
	@PutMapping("/modificarDosisDisponibles/{centroSalud}/{vacunas}")
	public void modificarNumeroVacunasDisponibles(@PathVariable String centroSalud, @PathVariable int vacunas) {
		try {
			Optional<CentroSalud> centroS = centroSaludDao.findById(centroSalud);
			if(centroS.isPresent()) {
				CentroSalud centroSaludDef = centroS.get();
				centroSaludDef.modificarStockVacunas(vacunas);
				centroSaludDao.save(centroSaludDef);
			}
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
}

