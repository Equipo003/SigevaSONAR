package com.equipo3.SIGEVA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo3.SIGEVA.dao.CupoCitasDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.CupoCitas;
import com.equipo3.SIGEVA.model.Usuario;

@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	CupoCitasDao cupoCitasDao;

	@GetMapping("/buscarCupoLibre")
	public CupoCitas buscarCupoLibre(@RequestBody CentroSalud centro) {
		// TODO
		return null;
	}

	@PutMapping("/confirmarCita")
	public void confirmarCita(@RequestBody CupoCitas cupo, @RequestBody Usuario paciente) {
		// TODO
	}

}
