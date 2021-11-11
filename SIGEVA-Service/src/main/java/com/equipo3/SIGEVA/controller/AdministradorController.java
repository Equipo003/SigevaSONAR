package com.equipo3.SIGEVA.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.dto.AdministradorDTO;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.ConfiguracionCuposDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.SanitarioDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.dto.VacunaDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CentroInvalidoException;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

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
	private ConfiguracionCuposDao configuracionCuposDao;
	@Autowired
	private VacunaDao vacunaDao;
	@Autowired
	private CupoDao cupoDao;

	@Autowired
	private WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	private WrapperDTOtoModel wrapperDTOtoModel = new WrapperDTOtoModel();

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
			centroSaludDTO.setVacuna(getVacunaByNombre("Pfizer"));
			CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findByNombreCentro(centroSalud.getNombreCentro());
			if (optCentroSalud.isPresent()) {
				throw new CentroInvalidoException("El centro de salud ya existe en la base de datos");
			}

			centroSaludDao.save(centroSalud);
			// cupoController.prepararCupos(centroSalud);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/deleteCentroSalud")
	public void borrarCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
		try {
			centroSaludDTO.setVacuna(getVacunaByNombre("Pfizer"));
			CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud.getId());
			System.out.println(cupoDao.buscarCuposOcupados(centroSalud.getId(), new Date())); 
			if (optCentroSalud.isPresent() && cupoDao.buscarCuposOcupados(centroSalud.getId(), new Date()).isEmpty()) {
				centroSaludDao.deleteById(centroSalud.getId());
			}else {
				throw new CentroInvalidoException("El centro de salud NO existe en la base de datos o NO se puede borrar por contener citas.");
			}

			
			// cupoController.prepararCuposCitas(centroSalud);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void crearRol(@RequestBody RolDTO rolDTO) {
		try {
			rolDao.save(WrapperDTOtoModel.rolDTOToRol(rolDTO));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

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
				return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAll());
			} else {
				return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAllByRol(rol));
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public PacienteDTO getPaciente(String id) {
		try {
			Optional<Usuario> optPaciente = administradorDao.findById(id);
			if (optPaciente.isPresent()) {
				return wrapperModelToDTO.pacienteToPacienteDTO(optPaciente.get());
			}
            throw new UsuarioInvalidoException("El paciente no existe en la base de datos");
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
			ConfiguracionCupos configuracionCupos = WrapperDTOtoModel
					.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTO);

			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
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
			return !configuracionCuposList.isEmpty();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	@GetMapping("/getConfCupos")
	public ConfiguracionCuposDTO getConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = this.wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());

			if (configuracionCuposDTOList.isEmpty())
				throw new IdentificadorException("No existe una configuración de cupos");

			return configuracionCuposDTOList.get(0);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	public void eliminarConfiguracionCupos() {
		try {
			List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
					.allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
			configuracionCuposDao.delete(
					WrapperDTOtoModel.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTOList.get(0)));

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping("/modificarDosisDisponibles/{centroSalud}/{vacunas}")
	public void modificarNumeroVacunasDisponibles(@PathVariable String centroSalud, @PathVariable int vacunas) {
		try {
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud);
			if (optCentroSalud.isPresent()) {
				CentroSaludDTO centroSaludDTO =
						wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
				centroSaludDTO.incrementarNumVacunasDisponibles(vacunas);
				centroSaludDao.save(wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO));
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public CentroSaludDTO getCentroById(String centroSalud) {
		try {
			Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud);
			if (optCentroSalud.isPresent()) {
				return wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
			}
            throw new IdentificadorException("Identificador Centro Salud " + centroSalud + " no existe");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public RolDTO getRolById(String rol) {
		try {
			Optional<Rol> optRol = rolDao.findById(rol);
			if (optRol.isPresent()) {
				return wrapperModelToDTO.rolToRolDTO(optRol.get());
			}
			throw new Exception();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/*
	 * @GetMapping("/getPacientesJornada") public List<PacienteDTO>
	 * getPacientesJornada(@RequestParam String fechaJornada){ SimpleDateFormat
	 * formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); try{ Date
	 * fechaJornadaFormated = formateador.parse(fechaJornada); List<CupoCitas>
	 * cupoCitasList =
	 * cupoController.cupoCitasDao.findByFechaYHoraInicio(fechaJornadaFormated); //
	 * MAL! List<Paciente> pacientesJornada = new ArrayList<Paciente>();
	 * Iterator<CupoCitas> cupoCitasIterator = cupoCitasList.iterator();
	 * while(cupoCitasIterator.hasNext()){
	 * pacientesJornada.addAll(cupoCitasIterator.next().getPacientesCitados()); }
	 * 
	 * return
	 * this.wrapperModelToDTO.pacientesJornadaToPacientesDTO(pacientesJornada);
	 * }catch (ParseException p){ throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST); }
	 * 
	 * }
	 */

	@GetMapping("/getUsuarioById")
	public UsuarioDTO getUsuarioById(@RequestParam String idUsuario) {
		try {
			Optional<Usuario> optUsuario = administradorDao.findById(idUsuario);
			if (optUsuario.isPresent()) {
				return wrapperModelToDTO.usuarioToUsuarioDTO(optUsuario.get());
			}
			throw new IdentificadorException("Identificador Usuario " + idUsuario + " no encontrado");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarUsuario(String username) {
		try {
			administradorDao.deleteByUsername(username);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarCentro(String idCentro) {
		try {
			centroSaludDao.deleteById(idCentro);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public RolDTO getRolByNombre(String nombreRol) {
		try {
			Optional<Rol> optRol = rolDao.findByNombre(nombreRol);
			if (optRol.isPresent()) {
				return wrapperModelToDTO.rolToRolDTO(optRol.get());
			}
			throw new IdentificadorException("Identificador Rol " + nombreRol + " no encontrado");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	public List<PacienteDTO> getPacientes() {
		try {
			List<Usuario> optUsuario = administradorDao.findAllByClass("com.equipo3.SIGEVA.model.Paciente");
			if (!optUsuario.isEmpty()) {
				return wrapperModelToDTO.allPacienteToPacienteDTO(optUsuario);
			}
			throw new IdentificadorException("No hay pacientes registrados");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void addVacuna(VacunaDTO vacunaDTO) {
		try {
			Vacuna vacuna = WrapperDTOtoModel.vacunaDTOToVacuna(vacunaDTO);
			vacunaDao.save(vacuna);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public VacunaDTO getVacunaByNombre(String pfizer) {
		try {
			Optional<Vacuna> optVacuna = vacunaDao.findByNombre(pfizer);
			if (optVacuna.isPresent()) {
				return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());
			}
			throw new IdentificadorException("Identificador Vacuna " + pfizer + " no encontrado");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public VacunaDTO getVacunaById(String id) {
		try {
			Optional<Vacuna> optVacuna = vacunaDao.findById(id);
			if (optVacuna.isPresent()) {
				return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());
			}
			throw new IdentificadorException("Identificador Vacuna " + id + " no encontrado");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarVacuna(String idVacuna) {
		try {
			vacunaDao.deleteById(idVacuna);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void eliminarRol(String idRol) {
		try {
			rolDao.deleteById(idRol);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
}