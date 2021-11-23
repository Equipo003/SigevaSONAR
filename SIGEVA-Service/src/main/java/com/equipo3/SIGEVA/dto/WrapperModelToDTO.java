package com.equipo3.SIGEVA.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dao.VacunaDao;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Sanitario;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.model.Vacuna;

import com.equipo3.SIGEVA.utils.Encriptador;


@Component
public class WrapperModelToDTO {
	private static  Encriptador var = new Encriptador();
	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	public ConfiguracionCuposDTO getConfiguracionCuposDTOfromUuid(String uuidConfiguracionCupos)
			throws IdentificadorException {
		Optional<ConfiguracionCupos> optConfiguracionCupos = configuracionCuposDao.findById(uuidConfiguracionCupos);

		if (optConfiguracionCupos.isPresent()) {
			return configuracionCuposToConfiguracionCuposDTO(optConfiguracionCupos.get());
		} else {
			throw new IdentificadorException(
					"Identificador ConfiguracionCupos " + uuidConfiguracionCupos + " no encontrado.");
		}
	}

	public ConfiguracionCuposDTO configuracionCuposToConfiguracionCuposDTO(ConfiguracionCupos configuracionCupos) {
		ConfiguracionCuposDTO configuracionCuposDTO = new ConfiguracionCuposDTO();

		configuracionCuposDTO.setId(configuracionCupos.getId());
		configuracionCuposDTO.setDuracionMinutos(configuracionCupos.getDuracionMinutos());
		configuracionCuposDTO.setDuracionJornadaHoras(configuracionCupos.getDuracionJornadaHoras());
		configuracionCuposDTO.setDuracionJornadaMinutos(configuracionCupos.getDuracionJornadaMinutos());
		configuracionCuposDTO.setFechaInicio(configuracionCupos.getFechaInicio());
		configuracionCuposDTO.setNumeroPacientes(configuracionCupos.getNumeroPacientes());

		return configuracionCuposDTO;
	}

	public List<ConfiguracionCuposDTO> allConfiguracionCuposToConfiguracionCuposDTO(List<ConfiguracionCupos> lista) {
		List<ConfiguracionCuposDTO> listaDTO = new ArrayList<>();
		for (ConfiguracionCupos configuracionCupos : lista) {
			listaDTO.add(configuracionCuposToConfiguracionCuposDTO(configuracionCupos));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	RolDao rolDao;

	public RolDTO getRolDTOfromUuid(String uuidRol) throws IdentificadorException {
		Optional<Rol> optRol = rolDao.findById(uuidRol);

		if (optRol.isPresent()) {
			return rolToRolDTO(optRol.get());
		} else {
			throw new IdentificadorException("Identificador Rol " + uuidRol + " no encontrado.");
		}
	}

	public RolDTO rolToRolDTO(Rol rol) {
		RolDTO rolDTO = new RolDTO();

		rolDTO.setId(rol.getId());
		rolDTO.setNombre(rol.getNombre());

		return rolDTO;
	}

	public List<RolDTO> allRolToRolDTO(List<Rol> lista) {
		List<RolDTO> listaDTO = new ArrayList<>();
		for (Rol rol : lista) {
			listaDTO.add(rolToRolDTO(rol));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	VacunaDao vacunaDao;

	public VacunaDTO getVacunaDTOfromUuid(String uuidVacuna) throws IdentificadorException {
		Optional<Vacuna> optVacuna = vacunaDao.findById(uuidVacuna);

		if (optVacuna.isPresent()) {
			return vacunaToVacunaDTO(optVacuna.get());
		} else {
			throw new IdentificadorException("Identificador Vacuna " + uuidVacuna + " no encontrado.");
		}
	}

	public VacunaDTO vacunaToVacunaDTO(Vacuna vacuna) {
		VacunaDTO vacunaDTO = new VacunaDTO();

		vacunaDTO.setId(vacuna.getId());
		vacunaDTO.setNombre(vacuna.getNombre());
		vacunaDTO.setDiasEntreDosis(vacuna.getDiasEntreDosis());
		vacunaDTO.setNumDosis(vacuna.getNumDosis());

		return vacunaDTO;
	}

	public List<VacunaDTO> allVacunaToVacunaDTO(List<Vacuna> lista) {
		List<VacunaDTO> listaDTO = new ArrayList<>();
		for (Vacuna vacuna : lista) {
			listaDTO.add(vacunaToVacunaDTO(vacuna));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	CentroSaludDao centroSaludDao;

	public CentroSaludDTO getCentroSaludDTOfromUuid(String uuidCentroSalud) throws IdentificadorException {
		Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(uuidCentroSalud);

		if (optCentroSalud.isPresent()) {
			CentroSalud centroSalud = optCentroSalud.get();

			CentroSaludDTO centroSaludDTO = new CentroSaludDTO();

			centroSaludDTO.setId(centroSalud.getId());
			centroSaludDTO.setNombreCentro(centroSalud.getNombreCentro());
			centroSaludDTO.setNumVacunasDisponibles(centroSalud.getNumVacunasDisponibles());
			centroSaludDTO.setDireccion(centroSalud.getDireccion());
			centroSaludDTO.setVacuna(getVacunaDTOfromUuid(centroSalud.getVacuna()));

			return centroSaludDTO;

		} else {
			throw new IdentificadorException("Identificador Centro Salud " + uuidCentroSalud + " no encontrado.");
		}
	}

	public CentroSaludDTO centroSaludToCentroSaludDTO(CentroSalud centroSalud) {
		try {
			return getCentroSaludDTOfromUuid(centroSalud.getId());
		} catch (IdentificadorException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CentroSaludDTO> allCentroSaludToCentroSaludDTO(List<CentroSalud> lista) {
		List<CentroSaludDTO> listaDTO = new ArrayList<>();
		for (CentroSalud centroSalud : lista) {
			listaDTO.add(centroSaludToCentroSaludDTO(centroSalud));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	CupoDao cupoDao;

	public CupoDTO getCupoDTOfromUuid(String uuidCupo) throws IdentificadorException {
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			Cupo cupo = optCupo.get();
			
			CupoDTO cupoDTO = new CupoDTO();

			cupoDTO.setUuidCupo(cupo.getUuidCupo());
			cupoDTO.setCentroSalud(getCentroSaludDTOfromUuid(cupo.getUuidCentroSalud()));
			cupoDTO.setFechaYHoraInicio(cupo.getFechaYHoraInicio());
			cupoDTO.setTamanoActual(cupo.getTamanoActual());

			return cupoDTO;

		} else {
			throw new IdentificadorException("Identificador Cupo " + uuidCupo + " no encontrado.");
		}

	}

	public CupoDTO cupoToCupoDTO(Cupo cupo) {
		try {
			return getCupoDTOfromUuid(cupo.getUuidCupo());
		} catch (IdentificadorException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CupoDTO> allCupoToCupoDTO(List<Cupo> lista) {
		List<CupoDTO> listaDTO = new ArrayList<>();
		for (Cupo cupo : lista) {
			listaDTO.add(cupoToCupoDTO(cupo));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	UsuarioDao usuarioDao;

	public UsuarioDTO getUsuarioDTOfromUuid(String uuidUsuario) throws IdentificadorException { // ¡Parsear después!
		Optional<Usuario> optUsuario = usuarioDao.findById(uuidUsuario);

		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			RolDTO rol = getRolDTOfromUuid(usuario.getRol());
			switch (rol.getNombre()) { // Patrón Factory.

			case "Administrador":

				Administrador administrador = (Administrador) usuario;

				AdministradorDTO administradorDTO = new AdministradorDTO();

				administradorDTO.setIdUsuario(administrador.getIdUsuario());
				administradorDTO.setRol(getRolDTOfromUuid(administrador.getRol()));
				administradorDTO.setCentroSalud(getCentroSaludDTOfromUuid(administrador.getCentroSalud()));
				administradorDTO.setUsername(administrador.getUsername());
				administradorDTO.setCorreo(administrador.getCorreo());
				administradorDTO.setHashPassword(administrador.getHashPassword());
				administradorDTO.setDni(administrador.getDni());
				administradorDTO.setNombre(administrador.getNombre());
				administradorDTO.setApellidos(administrador.getApellidos());
				administradorDTO.setFechaNacimiento(administrador.getFechaNacimiento());
				administradorDTO.setImagen(administrador.getImagen());

				return administradorDTO;

			case "Sanitario":

				Sanitario sanitario = (Sanitario) usuario;

				SanitarioDTO sanitarioDTO = new SanitarioDTO();

				sanitarioDTO.setIdUsuario(sanitario.getIdUsuario());
				sanitarioDTO.setRol(getRolDTOfromUuid(sanitario.getRol()));
				sanitarioDTO.setCentroSalud(getCentroSaludDTOfromUuid(sanitario.getCentroSalud()));
				sanitarioDTO.setUsername(sanitario.getUsername());
				sanitarioDTO.setCorreo(sanitario.getCorreo());
				sanitarioDTO.setHashPassword(sanitario.getHashPassword());
				sanitarioDTO.setDni(sanitario.getDni());
				sanitarioDTO.setNombre(sanitario.getNombre());
				sanitarioDTO.setApellidos(sanitario.getApellidos());
				sanitarioDTO.setFechaNacimiento(sanitario.getFechaNacimiento());
				sanitarioDTO.setImagen(sanitario.getImagen());

				return sanitarioDTO;

			case "Paciente":

				Paciente paciente = (Paciente) usuario;

				PacienteDTO pacienteDTO = new PacienteDTO();

				pacienteDTO.setIdUsuario(paciente.getIdUsuario());
				pacienteDTO.setRol(getRolDTOfromUuid(paciente.getRol()));
				pacienteDTO.setCentroSalud(getCentroSaludDTOfromUuid(paciente.getCentroSalud()));
				pacienteDTO.setUsername(paciente.getUsername());
				pacienteDTO.setCorreo(paciente.getCorreo());
				pacienteDTO.setHashPassword(paciente.getHashPassword());
				pacienteDTO.setDni(paciente.getDni());
				pacienteDTO.setNombre(paciente.getNombre());
				pacienteDTO.setApellidos(paciente.getApellidos());
				pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());
				pacienteDTO.setImagen(paciente.getImagen());
				pacienteDTO.setNumDosisAplicadas(Integer.parseInt((var.desencriptar(paciente.getNumDosisAplicadas()))));
				
				return pacienteDTO;
			}

			return null;

		} else {
			throw new IdentificadorException("Identificador Usuario " + uuidUsuario + " no encontrado.");
		}
	}

	public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
		try {
			return getUsuarioDTOfromUuid(usuario.getIdUsuario());
		} catch (IdentificadorException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<UsuarioDTO> allUsuarioToUsuarioDTO(List<Usuario> lista) {
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		for (Usuario usuario : lista) {
			listaDTO.add(usuarioToUsuarioDTO(usuario));
		}
		return listaDTO;
	}

	// ----------

	public PacienteDTO getPacienteDTOfromUuid(String uuidPaciente) throws IdentificadorException {
		Optional<Usuario> optUsuario = usuarioDao.findById(uuidPaciente);

		if (optUsuario.isPresent()) {
			Usuario usuario = optUsuario.get();
			RolDTO rol = getRolDTOfromUuid(usuario.getRol());
			if (rol.getNombre().equals("Paciente")) {

				Paciente paciente = (Paciente) usuario;

				PacienteDTO pacienteDTO = new PacienteDTO();

				pacienteDTO.setIdUsuario(paciente.getIdUsuario());
				pacienteDTO.setRol(getRolDTOfromUuid(paciente.getRol()));
				pacienteDTO.setCentroSalud(getCentroSaludDTOfromUuid(paciente.getCentroSalud()));
				pacienteDTO.setUsername(paciente.getUsername());
				pacienteDTO.setCorreo(paciente.getCorreo());
				pacienteDTO.setHashPassword(paciente.getHashPassword());
				pacienteDTO.setDni(paciente.getDni());
				pacienteDTO.setNombre(paciente.getNombre());
				pacienteDTO.setApellidos(paciente.getApellidos());
				pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());
				pacienteDTO.setImagen(paciente.getImagen());

				pacienteDTO.setNumDosisAplicadas(Integer.parseInt((var.desencriptar(paciente.getNumDosisAplicadas()))));

				return pacienteDTO;

			} else {
				throw new IdentificadorException(
						"El Usuario del UUID " + uuidPaciente + " no es Paciente, sino " + rol.getNombre() + ".");
			}
		} else {
			throw new IdentificadorException("Identificador Paciente " + uuidPaciente + " no encontrado.");
		}
	}

	public PacienteDTO pacienteToPacienteDTO(Usuario usuario) {
		try {
			return getPacienteDTOfromUuid(usuario.getIdUsuario());
		} catch (IdentificadorException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<PacienteDTO> allPacienteToPacienteDTO(List<Usuario> lista) {
		List<PacienteDTO> listaDTO = new ArrayList<>();
		for (Usuario usuario : lista) {
			listaDTO.add(pacienteToPacienteDTO(usuario));
		}
		return listaDTO;
	}

	// --------------------------------------------------

	@Autowired
	CitaDao citaDao;

	public CitaDTO getCitaDTOfromUuid(String uuidCita) throws IdentificadorException {
		Optional<Cita> optCita = citaDao.findById(uuidCita);

		if (optCita.isPresent()) {
			Cita cita = optCita.get();

			CitaDTO citaDTO = new CitaDTO();

			citaDTO.setUuidCita(cita.getUuidCita());
			citaDTO.setCupo(getCupoDTOfromUuid(cita.getUuidCupo()));
			citaDTO.setPaciente(getPacienteDTOfromUuid(cita.getUuidPaciente()));
			citaDTO.setDosis(cita.getDosis());

			return citaDTO;

		} else {
			throw new IdentificadorException("Identificador Cita " + uuidCita + " no encontrado.");
		}
	}

	public CitaDTO citaToCitaDTO(Cita cita) {
		try {
			return getCitaDTOfromUuid(cita.getUuidCita());
		} catch (IdentificadorException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CitaDTO> allCitaToCitaDTO(List<Cita> lista) {
		List<CitaDTO> listaDTO = new ArrayList<>();
		for (Cita cita : lista) {
			listaDTO.add(citaToCitaDTO(cita));
		}
		return listaDTO;
	}

}
