package com.equipo3.SIGEVA.controller;

import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.dao.*;
import com.equipo3.SIGEVA.exception.CentroInvalidoException;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("user")
public class AdministradorController {

	@Autowired
	private UsuarioDao administradorDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private ConfiguracionCuposDao configCuposDao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private CupoController cupoController;

	public void eliminarUsuario(String username){
		try {
			administradorDao.deleteByUsername(username);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/fijarCentro/{username}/{centro}")
	public void fijarPersonal(@PathVariable String username, @PathVariable String centro) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(username);
			if (optUsuario.isPresent()) {
				Usuario sanitario = optUsuario.get();
				sanitario.setCentroSalud(centro);
				administradorDao.save(sanitario);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getUsuariosByRol")
	public List<Usuario> getUsuarioByRol(@RequestParam String rol) {
		try {
			if (rol.equals("Todos")) {
				return administradorDao.findAll();
			} else {
				return administradorDao.findAllByRol(rol);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	private static final String FRASE_USUARIO_EXISTENTE = "El usuario ya existe en la base de datos";

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioAdministrador")
	public void crearUsuarioAdministrador(@RequestBody Administrador admin) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(admin.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}

			administradorDao.save(admin);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioPaciente")
	public void crearUsuarioPaciente(@RequestBody Paciente paciente) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}

			administradorDao.save(paciente);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearUsuarioSanitario")
	public void crearUsuarioSanitario(@RequestBody Sanitario sanitario) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
			if (optUsuario.isPresent()) {
				throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
			}

			administradorDao.save(sanitario);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/newCentroSalud")
	public void crearCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) throws NumVacunasInvalido {
		CentroSalud centroSalud = new CentroSalud();

		centroSalud.setNombreCentro(centroSaludDTO.getNombreCentro());
		centroSalud.setDireccion(centroSaludDTO.getDireccion());
		centroSalud.setNumVacunasDisponibles(centroSaludDTO.getNumVacunasDisponibles());

		try {

			Optional<CentroSalud> optCentroSalud = centroSaludDao.findByNombreCentro(centroSalud.getNombreCentro());
			if (optCentroSalud.isPresent()) {
				throw new CentroInvalidoException("El centro de salud ya existe en la base de datos");
			}
			cupoController.prepararCuposCitas(centroSalud);
			centroSaludDao.save(centroSalud);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping("/registrarRol")
	public void registrarRol(@RequestBody RolDTO rolDTO) {

		Rol rol = new Rol();
		rol.setNombre(rolDTO.getNombre());

		try {
			rolDao.save(rol);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getCentros")
	public List<CentroSalud> listarCentros() {
		return centroSaludDao.findAll();
		
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getRoles")
	public List<Rol> listarRoles() {
		try {
			return rolDao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/crearConfCupos")
	public void crearConfiguracionCupos(@RequestBody ConfiguracionCuposDTO confDTO) {

		ConfiguracionCupos conf = new ConfiguracionCupos();

		conf.setDuracionMinutos(confDTO.getDuracionMinutos());
		conf.setNumeroPacientes(confDTO.getNumeroPacientes());
		conf.setDuracionJornadaHoras(conf.getDuracionJornadaHoras());
		conf.setDuracionJornadaMinutos(conf.getDuracionJornadaMinutos());
		conf.setFechaInicio(conf.getFechaInicio());

		try {
			List<ConfiguracionCupos> configuracionCuposList = configCuposDao.findAll();
			if (configuracionCuposList.isEmpty())
				configCuposDao.save(conf);
			else
				throw new ConfiguracionYaExistente("Ya existía configuración.");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/existConfCupos")
	public boolean existConfiguracionCupos() {
		try {
			List<ConfiguracionCupos> configuracionCuposList = configCuposDao.findAll();
			return !configuracionCuposList.isEmpty();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
		}

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/modificarDosisDisponibles/{centroSalud}/{vacunas}")
	public void modificarNumeroVacunasDisponibles(@PathVariable String centroSalud, @PathVariable int vacunas) {
		try {
			Optional<CentroSalud> centroS = centroSaludDao.findById(centroSalud);
			if (centroS.isPresent()) {
				CentroSalud centroSaludDef = centroS.get();
				centroSaludDef.modificarStockVacunas(vacunas);
				centroSaludDao.save(centroSaludDef);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
}