package com.equipo3.SIGEVA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * Controlador para mapear las peticiones del front end en el back end.
 * 
 * @author Equipo3
 *
 */
@Controller
public class ViewController {

	@RequestMapping({ "/home", "/crearCS", "/cnfgCupos", "/crearUsuarios", "/indicarDosisVacunas", "/usuariosSistema",
			"/solicitarCita", "/editarUsuario/:idUsuario", "/editarCS/:idCentroSalud", "/listarPacientes", "/login",
			"/misCitas" })
	public String index() {
		return "forward:/index.html";
	}
}
