package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.dao.CitaDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.CupoDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CitaDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import com.equipo3.SIGEVA.model.Cita;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;

@CrossOrigin
@RestController
@RequestMapping("cita")
public class CitaController {

	@Autowired
	CitaDao citaDao;

	@Autowired
	CupoDao cupoDao;

	@Autowired
	CupoController cupoController;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	WrapperDTOtoModel wrapperDTOtoModel;

	@Autowired
	UsuarioDao usuarioDao;

	private static final int PRIMERA_DOSIS = 1;
	private static final int SEGUNDA_DOSIS = 2;

	@SuppressWarnings("deprecation")
	@GetMapping("/buscarYAsignarCitas")
	public List<CitaDTO> buscarYAsignarCitas(@RequestBody String uuidPaciente) {
		if (uuidPaciente == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UUID no contemplado.");
		}

		PacienteDTO pacienteDTO = null;
		try {
			pacienteDTO = wrapperModelToDTO.getPacienteDTOfromUuid(uuidPaciente);
		} catch (IdentificadorException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no contemplado en BBDD.");
		}

		if (new Date().after(Condicionamientos.fechaFin())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"A partir del " + Condicionamientos.fechaFin() + " ya no se vacunaba gente.");
		}

		int dosisAplicadas = pacienteDTO.getNumDosisAplicadas();

		if (dosisAplicadas == 2) { // CASO 1.

			throw new ResponseStatusException(HttpStatus.CONFLICT, "¡El paciente ya estaba vacunado de las dos dosis!");

		} else if (dosisAplicadas == 1) { // CASO 2.

			// Objetivo: Comprobar con respecto a la segunda cita.

			List<CitaDTO> citasFuturasDTO = null;
			try {
				citasFuturasDTO = obtenerCitasFuturasDelPaciente(pacienteDTO);
			} catch (UsuarioInvalidoException e) {
				// Controlado con anterioridad.
				e.printStackTrace();
			}

			int numCitasFuturasAsignadas = citasFuturasDTO.size();

			if (numCitasFuturasAsignadas == 1) { // CASO 2.1. Segunda cita ya asignada.
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"El paciente ya está vacunado de la primera, y ya tiene programada la segunda cita para el "
								+ citasFuturasDTO.get(0).getCupo().getFechaYHoraInicio() + ".");

			} else { // CASO 2.2. numCitasFuturasAsignadas = 0
				// Objetivo: Asignar la segunda 21 días después de la primera.

				// CASO 2.2.1.
				if (pacienteDTO.getCentroSalud().getNumVacunasDisponibles() < 1) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El centro de salud no tiene stock (1).");
				}

				CitaDTO citaPrimerPinchazo = buscarUltimaCitaPinchazo(pacienteDTO, PRIMERA_DOSIS);

				Date fechaPrimerPinchazo = citaPrimerPinchazo.getCupo().getFechaYHoraInicio();
				Date fechaMinimaSegundoPinchazo = (Date) fechaPrimerPinchazo.clone();
				fechaMinimaSegundoPinchazo
						.setDate(fechaMinimaSegundoPinchazo.getDate() + Condicionamientos.tiempoEntreDosis());

				// CASO 2.2.2.
				if (fechaMinimaSegundoPinchazo.after(Condicionamientos.fechaFin())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,
							"Respetando los " + Condicionamientos.tiempoEntreDosis() + " días desde la primera dosis ("
									+ citaPrimerPinchazo.getCupo().getFechaYHoraInicio() + "), a partir del "
									+ Condicionamientos.fechaFin() + " no habrán vacunaciones.");
				}

				// CASO 2.2.3.
				CupoDTO cupoLibre = cupoController.buscarPrimerCupoLibreAPartirDe(pacienteDTO.getCentroSalud(),
						fechaMinimaSegundoPinchazo);
				// ¡Lanzará exception avisando de que no hay hueco en ese caso!

				// CASO 2.2.4.
				CitaDTO citaProgramadaSegundaDosis = confirmarCita(cupoLibre, pacienteDTO, SEGUNDA_DOSIS);
				List<CitaDTO> lista = new ArrayList<>();
				lista.add(citaProgramadaSegundaDosis);
				// El paciente ya tenía aplicada la primera dosis.
				return lista;

			}
		} else { // CASO 3. dosisAplicadas == 0
			// Objetivo: Asegurarse de asignar la primera y segunda cita.

			List<CitaDTO> citasFuturasDTO = null;
			try {
				citasFuturasDTO = obtenerCitasFuturasDelPaciente(pacienteDTO);
			} catch (UsuarioInvalidoException e) {
				// Controlado con anterioridad.
				e.printStackTrace();
			}
			int numCitasFuturasAsignadas = citasFuturasDTO.size();

			if (numCitasFuturasAsignadas == 2) { // CASO 3.1.
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Sus dos citas ya están programadas: " + citasFuturasDTO.get(0).getCupo().getFechaYHoraInicio()
								+ " y " + citasFuturasDTO.get(1).getCupo().getFechaYHoraInicio() + ".");

			} else if (numCitasFuturasAsignadas == 1) { // CASO 3.2.

				// CASO 3.2.1.
				if (pacienteDTO.getCentroSalud().getNumVacunasDisponibles() < 1) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "El centro de salud no tiene stock (1).");
				}

				Date fechaPrimeraDosis = citasFuturasDTO.get(1).getCupo().getFechaYHoraInicio();
				Date fechaSegundaDosis = (Date) fechaPrimeraDosis.clone();

				// CASO 3.2.2.
				if (fechaSegundaDosis.after(Condicionamientos.fechaFin())) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,
							"Respetando los " + Condicionamientos.tiempoEntreDosis() + " días desde la primera dosis ("
									+ fechaPrimeraDosis + "), a partir del " + Condicionamientos.fechaFin()
									+ " no habrán vacunaciones.");
				}

				// CASO 3.2.3.
				CupoDTO cupoLibre = cupoController.buscarPrimerCupoLibreAPartirDe(pacienteDTO.getCentroSalud(),
						fechaSegundaDosis);
				// ¡Lanzará exception avisando de que no hay hueco en ese caso!

				// CASO 3.2.4.
				CitaDTO citaProgramadaSegundaDosis = confirmarCita(cupoLibre, pacienteDTO, SEGUNDA_DOSIS);
				List<CitaDTO> lista = new ArrayList<>();
				lista.add(citaProgramadaSegundaDosis);
				// El paciente no tiene dosis aplicadas, pero ya tenía programada la cita futura
				// para su primera dosis.
				return lista;

			} else { // CASO 3.3. numCitasFuturasAsignadas == 0.

				// CASO 3.3.1.
				if (pacienteDTO.getCentroSalud().getNumVacunasDisponibles() < 2) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,
							"El centro de salud no tiene suficiente stock (2).");
				}

				// 3.3.2. Automático

				// 3.3.3.
				CupoDTO primerCupoLibreDTO = cupoController.buscarPrimerCupoLibreAPartirDe(pacienteDTO.getCentroSalud(),
						new Date());
				// 3.3.3.1. Si no se encuentra hueco, ya lanza ResponseStatusException.

				// 3.3.3.2.
				CitaDTO primeraCitaDTO = confirmarCita(primerCupoLibreDTO, pacienteDTO, 1);
				List<CitaDTO> lista = new ArrayList<>();
				lista.add(primeraCitaDTO);

				// 3.3.4.
				Date fechaPrimeraCita = primeraCitaDTO.getCupo().getFechaYHoraInicio();
				Date fechaSegundaCita = (Date) fechaPrimeraCita.clone();
				fechaSegundaCita.setDate(fechaSegundaCita.getDate() + Condicionamientos.tiempoEntreDosis());
				if (fechaSegundaCita.after(Condicionamientos.fechaFin())) {
					// La fecha de la segunda cita supera la fecha máxima.
					return lista; // Con solo la primera dosis.
				}
				
				// 3.3.5.
				CupoDTO cupoLibreDTOSegundaCita = cupoController.buscarPrimerCupoLibreAPartirDe(pacienteDTO.getCentroSalud(), fechaSegundaCita);
				// 3.3.5.1. Si no se encuentra hueco, ya lanza ResponseStatusException.
				
				// 3.3.5.2.
				CitaDTO segundaCitaDTO = confirmarCita(cupoLibreDTOSegundaCita, pacienteDTO, 2);
				lista.add(segundaCitaDTO);
				return lista; // Con las dos citas.

			}

		}

		// Revisar con respecto al código de confirmación del primer sprint.
	}

	@SuppressWarnings("static-access")
	public CitaDTO confirmarCita(CupoDTO cupoDTO, PacienteDTO pacienteDTO, int dosis) {
		// TODO PENDIENTE

		CitaDTO citaDTO = new CitaDTO(cupoDTO, pacienteDTO, dosis);

		ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);
		try {
			cupoDTO.incrementarTamanoActual(configuracionCupos.getNumeroPacientes());
		} catch (CupoException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error del servidor. " + e);
			// No debería saltar la excepción, salvo por cuestiones de concurrencia, porque
			// solamente traemos cupos libres.
		}

		cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		citaDao.save(wrapperDTOtoModel.citaDTOToCita(citaDTO));

		return citaDTO;
	}

	public CitaDTO buscarUltimaCitaPinchazo(PacienteDTO pacienteDTO, int dosis) {
		List<CitaDTO> citasAntiguasDTO = obtenerCitasAntiguasPaciente(pacienteDTO);
		/*
		 * Ya que el paciente está vacunado de la primera (al estarlo, no puede
		 * programar de nuevo la primera), entendemos que la cita del pinchazo fue la
		 * última cita de esa dosis.
		 */
		CitaDTO citaPinchazo = null;
		for (int i = citasAntiguasDTO.size() - 1; i >= 0 && citaPinchazo == null; i--) {
			// Empezando desde el final.
			CitaDTO cita = citasAntiguasDTO.get(i);
			if (cita.getDosis() == PRIMERA_DOSIS) {
				citaPinchazo = cita;
			}
		}
		return citaPinchazo;
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/buscarDiasModificacionCita")
	public List<Date> buscarDiasModificacionCita(String uuidCita) {
		if (uuidCita != null) {
			List<Date> lista = new ArrayList<>();
			CitaDTO citaDTO = null;
			try {
				citaDTO = wrapperModelToDTO.getCitaDTOfromUuid(uuidCita);
			} catch (IdentificadorException e) {
				e.printStackTrace();
			}
			if (citaDTO.getDosis() == PRIMERA_DOSIS) { // En caso de ser la primera, da igual.

				lista.add(new Date()); // Desde hoy

				// Si tiene segunda cita, es hasta el día anterior a la segunda;
				// y si no, fecha fin:
				List<CitaDTO> citasDTO = null;
				try {
					citasDTO = obtenerCitasFuturasDelPaciente(citaDTO.getPaciente());
				} catch (UsuarioInvalidoException e) {
					e.printStackTrace();
				}
				if (citasDTO.size() == 2) {
					Date fechaSegundaCita = citasDTO.get(1).getCupo().getFechaYHoraInicio();
					Date fechaMaxima = (Date) fechaSegundaCita.clone();
					fechaMaxima.setDate(fechaMaxima.getDate() - 1);
					lista.add(fechaMaxima); // Hasta el día antes de la segunda cita
				} else {
					lista.add(Condicionamientos.fechaFin()); // Hasta el día tope
				}

			} else { // SEGUNDA_DOSIS // En el caso de ser la segunda, +21 días.
				CitaDTO citaPrimerPinchazo = buscarUltimaCitaPinchazo(citaDTO.getPaciente(), PRIMERA_DOSIS);
				Date fechaPrimerPinchazo = citaPrimerPinchazo.getCupo().getFechaYHoraInicio();
				Date fechaMinimaSegundoPinchazo = (Date) fechaPrimerPinchazo.clone();
				fechaMinimaSegundoPinchazo
						.setDate(fechaMinimaSegundoPinchazo.getDate() + Condicionamientos.tiempoEntreDosis());
				lista.add(fechaMinimaSegundoPinchazo);
				lista.add(Condicionamientos.fechaFin());
			}
			return lista;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "UUID no contemplado.");
		}
	}

	@GetMapping("/obtenerCitasFecha")
	public List<CitaDTO> obtenerCitasFecha(@RequestBody CentroSaludDTO centroSaludDTO, @RequestBody Date fecha) { // Terminado.
		if (centroSaludDTO != null && fecha != null) {

			List<CupoDTO> cuposDTO = cupoController.buscarTodosCuposFecha(centroSaludDTO, fecha);

			List<Cita> citas = new ArrayList<>();
			for (int i = 0; i < cuposDTO.size(); i++) {
				citas.addAll(citaDao.buscarCitasDelCupo(cuposDTO.get(i).getUuidCupo()));
			}

			List<CitaDTO> citasDTO = wrapperModelToDTO.allCitaToCitaDTO(citas);
			Collections.sort(citasDTO); // Ordenación

			return citasDTO;

		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Centro de salud o fecha no contemplada en el parámetro.");
		}
	}

	@GetMapping("/obtenerCitasFuturasDelPaciente")
	public List<CitaDTO> obtenerCitasFuturasDelPaciente(@RequestBody PacienteDTO pacienteDTO)
			throws UsuarioInvalidoException { // Terminado.
		if (pacienteDTO != null) {
			List<CitaDTO> citasDTO = wrapperModelToDTO
					.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario()));
			for (int i = 0; i < citasDTO.size(); i++) {
				if (citasDTO.get(i).getCupo().getFechaYHoraInicio().before(new Date())) { // ¿Es antigua?
					citasDTO.remove(i--);
				}
			}
			Collections.sort(citasDTO);
			return citasDTO;
		} else {
			throw new UsuarioInvalidoException("Paciente no contemplado en el parámetro.");
		}
	}

	public List<CitaDTO> obtenerCitasAntiguasPaciente(PacienteDTO pacienteDTO) {
		List<CitaDTO> citasDTO = wrapperModelToDTO
				.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario()));
		for (int i = 0; i < citasDTO.size(); i++) {
			if (citasDTO.get(i).getCupo().getFechaYHoraInicio().after(new Date())) { // ¿Es futura?
				citasDTO.remove(i--);
			}
		}
		Collections.sort(citasDTO);
		return citasDTO;
	}

	@SuppressWarnings("static-access")
	@PutMapping("/eliminarCita")
	public void eliminarCita(@RequestBody CitaDTO citaDTO) { // Terminado

		// ¿Era primera o segunda?
		int dosis = citaDTO.getDosis();

		citaDao.deleteById(citaDTO.getUuidCita());
		try {
			cupoController.decrementarTamanoActualCupo(citaDTO.getCupo().getUuidCupo());
		} catch (CupoException e) {
			// Cupo no existente en la BD.
			e.printStackTrace();
		}

		// Si se elimina la primera dosis, la segunda pasa a la primera.
		// En caso de ser primera, y haber segunda cita programada, ésta pasa a primera:
		if (dosis == 1) {
			PacienteDTO pacienteDTO = citaDTO.getPaciente();

			List<CitaDTO> citasDTO = null;
			try {
				citasDTO = obtenerCitasFuturasDelPaciente(pacienteDTO); // Nueva consulta BD
			} catch (UsuarioInvalidoException e) {
				e.printStackTrace();
			}
			if (citasDTO.size() == 1) { // La primera se acaba de borrar y esta es la segunda.
				CitaDTO segundaCitaDTO = citasDTO.get(0);
				if (segundaCitaDTO.getDosis() == 2) { // Precaución - Seguridad
					segundaCitaDTO.setDosis(1);
					Cita segundaCita = wrapperDTOtoModel.citaDTOToCita(segundaCitaDTO);
					citaDao.save(segundaCita); // Modificación
				}
			}
		}

	}

	public void eliminarCitas(List<CitaDTO> citasDTO) { // Terminado
		for (int i = 0; i < citasDTO.size(); i++) {
			eliminarCita(citasDTO.get(i));
		}
	}

	public void eliminarCitasFuturasDelPaciente(PacienteDTO paciente) throws UsuarioInvalidoException { // Terminado
		eliminarCitas(obtenerCitasFuturasDelPaciente(paciente));
	}

	public void eliminarTodasLasCitasDelPaciente(PacienteDTO pacienteDTO) { // Terminado
		eliminarCitas(wrapperModelToDTO.allCitaToCitaDTO(citaDao.buscarCitasDelPaciente(pacienteDTO.getIdUsuario())));
	}

	public void eliminarTodasLasCitasDelCupo(String uuidCupo) { // Terminado
		citaDao.deleteAllByUuidCupo(uuidCupo);
		try {
			cupoController.anularTamanoActual(uuidCupo);
		} catch (CupoException e) {
			e.printStackTrace();
		}
	}

}
