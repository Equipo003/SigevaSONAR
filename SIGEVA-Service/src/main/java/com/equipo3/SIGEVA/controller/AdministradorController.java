package com.equipo3.SIGEVA.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.equipo3.SIGEVA.dao.*;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.exception.CentroInvalidoException;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("user")
public class AdministradorController {

	@Autowired
	private UsuarioDao administradorDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private CentroSaludDao centroSaludDao;
	@Autowired
	private CupoController cupoController;
	@Autowired
	private ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;

	private WrapperDTOtoModel wrapperDTOtoModel;

	private static final String FRASE_USUARIO_EXISTENTE = "El usuario ya existe en la base de datos";

	@PostMapping("/crearUsuarioAdministrador")
	public void crearUsuarioAdministrador(@RequestBody AdministradorDTO administradorDTO) {
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


	@PostMapping("/crearUsuarioPaciente")
	public void crearUsuarioPaciente(@RequestBody PacienteDTO pacienteDTO) {
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


	@PostMapping("/crearUsuarioSanitario")
	public void crearUsuarioSanitario(@RequestBody SanitarioDTO sanitarioDTO) {
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


	@PostMapping("/newCentroSalud")
	public void crearCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
		try {
			CentroSalud centroSalud = WrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findByNombreCentro(centroSalud.getNombreCentro());
			if (optCentroSalud.isPresent()) {
				throw new CentroInvalidoException("El centro de salud ya existe en la base de datos");
			}

			centroSaludDao.save(centroSalud);
			cupoController.prepararCuposCitas(centroSalud);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

//	@PostMapping("/registrarRol")
//	public void registrarRol(@RequestBody RolDTO rolDTO) {
//
//		Rol rol = new Rol();
//		rol.setNombre(rolDTO.getNombre());
//
//		try {
//			rolDao.save(rol);
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
//		}
//	}


	@GetMapping("/getCentros")
	public List<CentroSaludDTO> listarCentros() {
		return wrapperModelToDTO.allCentroSaludToCentroSaludDTO(centroSaludDao.findAll());
	}


	@GetMapping("/getRoles")
	public List<RolDTO> listarRoles() {
		try {
			return wrapperModelToDTO.allRolToRolDTO(rolDao.findAll());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}


	@GetMapping("/getUsuariosByRol")
	public List<UsuarioDTO> getUsuarioByRol(@RequestParam String rol) {
		try {
			if (rol.equals("Todos")) {
				return wrapperModelToDTO.listUsuarioToUsuarioDTO(administradorDao.findAll());
			} else {
				return wrapperModelToDTO.listUsuarioToUsuarioDTO(administradorDao.findAllByRol(rol));
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}


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


	@PostMapping("/crearConfCupos")
	public void crearConfiguracionCupos(@RequestBody ConfiguracionCuposDTO configuracionCuposDTO) {

		try {
			ConfiguracionCupos configuracionCupos = wrapperDTOtoModel.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTO);


			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO.configuracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
			if (configuracionCuposDTOList.isEmpty())
				configuracionCuposDao.save(configuracionCupos);
			else
				throw new ConfiguracionYaExistente("Ya existe una configuración de cupos");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
		}

	}


	@GetMapping("/existConfCupos")
	public boolean existConfiguracionCupos() {
		try {
			List<ConfiguracionCupos> configuracionCuposList = configuracionCuposDao.findAll();
			if (configuracionCuposList.size() == 0){
				return false;
			} else
				return true;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}


	@GetMapping("/getConfCupos")
	public ConfiguracionCuposDTO getConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = this.wrapperModelToDTO.configuracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());

			if(configuracionCuposDTOList.isEmpty())
				throw new Exception();

			return configuracionCuposDTOList.get(0);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	public void eliminarConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO.configuracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
			configuracionCuposDao.delete(wrapperDTOtoModel.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTOList.get(0)));


		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}


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

	public CentroSalud getCentroById(String centroSalud) {
		try {
			Optional<CentroSalud> centroS = centroSaludDao.findById(centroSalud);
			return centroS.get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public Rol getRolById(String rol) {
		try {
			Optional<Rol> rolOptional = rolDao.findById(rol);
			return rolOptional.get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@GetMapping("/getPacientesJornada")
	public List<PacienteDTO> getPacientesJornada(@RequestParam String fechaJornada){
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try{
			Date fechaJornadaFormated = formateador.parse(fechaJornada);
			List<CupoCitas> cupoCitasList = cupoController.cupoCitasDao.findByFechaYHoraInicio(fechaJornadaFormated);
			List<Paciente> pacientesJornada = new ArrayList<Paciente>();
			Iterator<CupoCitas> cupoCitasIterator = cupoCitasList.iterator();
			while(cupoCitasIterator.hasNext()){
				pacientesJornada.addAll(cupoCitasIterator.next().getPacientesCitados());
			}

			return this.wrapperModelToDTO.pacientesJornadaToPacientesDTO(pacientesJornada);
		}catch (ParseException p){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

	}

	public UsuarioDTO getUsuarioById(String idUsuario) {
		try {
			Optional<Usuario> usuarioOptional = administradorDao.findById(idUsuario);
			return this.wrapperModelToDTO.usuarioToUsuarioDTO(administradorDao.findById(idUsuario).get());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarUsuario(String username){
		try {
			administradorDao.deleteByUsername(username);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarCentro(String idCentro){
		try {
			centroSaludDao.deleteById(idCentro);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public Rol getRolByNombre(String nombreRol) {
        try {
            Optional<Rol> rolOptional = rolDao.findAllByNombre(nombreRol);
            if (rolOptional.isPresent())
                return rolOptional.get();
			else
				throw new Exception();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}