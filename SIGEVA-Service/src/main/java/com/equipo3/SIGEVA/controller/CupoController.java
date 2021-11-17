package com.equipo3.SIGEVA.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.CupoDTO;
import com.equipo3.SIGEVA.dto.WrapperDTOtoModel;
import com.equipo3.SIGEVA.dto.WrapperModelToDTO;
import com.equipo3.SIGEVA.exception.CupoException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Cupo;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Usuario;

@CrossOrigin
@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoDao cupoDao;
	
	@Autowired
	CentroSaludDao centroSaludDao;
	
	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	CitaController citaController;

	@Autowired
	WrapperModelToDTO wrapperModelToDTO;

	@Autowired
	WrapperDTOtoModel wrapperDTOtoModel;

	@SuppressWarnings("deprecation")
	private List<CupoDTO> calcularCupos(CentroSaludDTO centroSalud) { // Terminado.
		// No requerirá tiempo de ejecución.

		List<CupoDTO> momentos = new ArrayList<>();

		ConfiguracionCupos configuracionCupos = configuracionCuposDao.findAll().get(0);

		Date fechaInicio = configuracionCupos.getFechaInicioAsDate();

		Date fechaFinAbsoluta = new Date(Condicionamientos.anyoFin() - 1900, Condicionamientos.mesFin() - 1,
				Condicionamientos.diaFin(), configuracionCupos.getHoraFin().getHours(),
				configuracionCupos.getHoraFin().getMinutes());

		int duracionTramo = configuracionCupos.getDuracionMinutos();

		Date fechaIterada = copia(fechaInicio);

		while (fechaIterada.before(fechaFinAbsoluta)) {

			Date fechaFinDiaria = copia(fechaIterada);
			fechaFinDiaria.setHours(fechaFinAbsoluta.getHours()); // Fin del día.
			fechaFinDiaria.setMinutes(fechaFinAbsoluta.getMinutes());

			while (fechaIterada.before(fechaFinDiaria)) {
				momentos.add(new CupoDTO(centroSalud, copia(fechaIterada), 0));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

		}

		return momentos;
	}

	@PostMapping("/prepararCupos")
	public List<CupoDTO> prepararCupos(@RequestBody CentroSaludDTO centroSaludDTO) { // TODO PENDIENTE
		// ¡Requerirá tiempo de ejecución!
		System.out.println("CREADOS CUPOS");
		return null;
	}

	@GetMapping("/buscarCuposLibresAPartirDeLaFecha")
	public List<CupoDTO> buscarCuposLibresAPartirDeLaFecha(@RequestBody CentroSaludDTO centroSaludDTO,
			@RequestBody Date fecha) { // Terminado.
		// Este método se utiliza para buscar los próximos cupos libres (para asignar).
		List<CupoDTO> cuposDTO = wrapperModelToDTO.allCupoToCupoDTO(cupoDao.buscarCuposLibresAPartirDe(
				centroSaludDTO.getId(), fecha, configuracionCuposDao.findAll().get(0).getNumeroPacientes()));
		Collections.sort(cuposDTO);
		return cuposDTO;
	}

	public CupoDTO buscarPrimerCupoLibreFecha(CentroSaludDTO centroSaludDTO, Date aPartirDeLaFecha) {
		// Este método se utiliza para buscar el próximo cupo libre (para asignar).
		return buscarCuposLibresAPartirDeLaFecha(centroSaludDTO, aPartirDeLaFecha).get(0);
	}

	/**
	 * Método para obtener los cupos LIBRES de ese centro de exactamente ese día.
	 * 
	 * @param centroSaludDTO
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/buscarCuposLibresFecha")
	public List<CupoDTO> buscarCuposLibresFecha(@RequestBody CentroSaludDTO centroSaludDTO, @RequestBody Date fecha) { // Terminado.
		// Este método se utiliza para buscar los cupos libres del día (para modificar).
		// (La hora de la fecha no importa, solamente importa el día)
		Date fechaInicio = CupoController.copia(fecha);
		fechaInicio.setHours(0);
		fechaInicio.setMinutes(0);
		Date fechaFin = CupoController.copia(fechaInicio);
		fechaFin.setDate(fechaFin.getDate() + 1);
		List<CupoDTO> cuposDTO = wrapperModelToDTO
				.allCupoToCupoDTO(cupoDao.buscarCuposLibresDelTramo(centroSaludDTO.getId(), fechaInicio, fechaFin,
						configuracionCuposDao.findAll().get(0).getNumeroPacientes()));
		
		for(int i = 0; i<cuposDTO.size(); i++) {
			System.out.println(cuposDTO.get(i).getFechaYHoraInicio());
		}
		
		Collections.sort(cuposDTO);
		return cuposDTO;
	}

	/**
	 * Método usado para obtener TODOS los cupos de ese centro de exactamente ese
	 * día.
	 * 
	 * @param centroSaludDTO
	 * @param fecha
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@GetMapping("/buscarTodosCuposFecha")
	public List<CupoDTO> buscarTodosCuposFecha(@RequestBody CentroSaludDTO centroSaludDTO, @RequestBody Date fecha) { // Terminado.
		// Este método se utiliza para buscar las citas del día (para vacunar).
		// (La hora de la fecha no importa, solamente importa el día)
		Date fechaInicio = CupoController.copia(fecha);
		fechaInicio.setHours(0);
		fechaInicio.setMinutes(0);
		Date fechaFin = CupoController.copia(fechaInicio);
		fechaFin.setDate(fechaFin.getDate() + 1);
		List<Cupo> cupos = cupoDao.buscarTodosCuposDelTramo(centroSaludDTO.getId(), fechaInicio, fechaFin);
		List<CupoDTO> cuposDTO = wrapperModelToDTO.allCupoToCupoDTO(cupos);
		Collections.sort(cuposDTO);
		return cuposDTO;
	}

	@SuppressWarnings("static-access")
	public void incrementarTamanoActual(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está lleno.
			cupoDTO.incrementarTamanoActual(configuracionCuposDao.findAll().get(0).getNumeroPacientes());
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	@SuppressWarnings("static-access")
	public void decrementarTamanoActualCupo(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());

			// Lanza excepción si ya está vacío.
			cupoDTO.decrementarTamanoActual();
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	@SuppressWarnings("static-access")
	public void anularTamanoActual(String uuidCupo) throws CupoException { // Terminado.
		Optional<Cupo> optCupo = cupoDao.findById(uuidCupo);
		if (optCupo.isPresent()) {
			CupoDTO cupoDTO = wrapperModelToDTO.cupoToCupoDTO(optCupo.get());
			cupoDTO.setTamanoActual(0);
			cupoDao.save(wrapperDTOtoModel.cupoDTOToCupo(cupoDTO));
		} else {
			throw new CupoException("Cupo no identificado en la base de datos.");
		}
	}

	public void eliminarCupo(String uuidCupo) { // Terminado.
		citaController.eliminarTodasLasCitasDelCupo(uuidCupo);
		cupoDao.deleteById(uuidCupo);
	}

	@PutMapping("/borrarCuposDelCentro")
	public void borrarCuposDelCentro(@RequestBody CentroSaludDTO centroSaludDTO) {
		List<Cupo> cupos = cupoDao.findAllByUuidCentroSalud(centroSaludDTO.getId());
		for (int i = 0; i < cupos.size(); i++) {
			this.eliminarCupo(cupos.get(i).getUuidCupo());
		}
	}

	@SuppressWarnings("deprecation")
	public static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia. // Terminado.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/freeDatesDay")
	public List<CupoDTO> buscarCuposLibresFecha(@RequestParam String idUsuario, @RequestParam Date fecha){
		CentroSalud cs = null;
		Paciente pacienteUsu = null;
		List<Cupo> clibday = new ArrayList();
		try {
			Optional<Usuario> paciente = usuarioDao.findById(idUsuario);
			if(paciente.isPresent()) {
			    pacienteUsu = (Paciente) paciente.get();
			    System.out.println(pacienteUsu.getNumDosisAplicadas());
			}
			
			if(centroSaludDao.findById(pacienteUsu.getCentroSalud()).isPresent()) {
				cs = centroSaludDao.findById(pacienteUsu.getCentroSalud()).get();
				System.out.println(cs.getNombreCentro());
			}
			Date fechaInicio = (Date) fecha.clone();
			Date fechaFin = (Date) fecha.clone();
			fechaFin.setHours(24);
			System.out.println(fechaFin);
			System.out.println(fechaInicio);
			clibday = cupoDao.buscarCuposLibresDelTramo(cs.getId(), fechaInicio, fechaFin, configuracionCuposDao.findAll().get(0).getNumeroPacientes());
			for(int i = 0; i < clibday.size(); i++) {
				System.out.println("identificador: "+clibday.get(i).getUuidCupo()+"Fecha "+clibday.get(i).getFechaYHoraInicio()+"Tmaño: "+clibday.get(i).getTamanoActual());
			}
			
			return wrapperModelToDTO.allCupoToCupoDTO(clibday);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	

}