package com.equipo3.SIGEVA.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.ConfiguracionCuposDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;

@CrossOrigin
@RestController
@RequestMapping("cupo")
public class CupoController {

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	ConfiguracionCuposDao configuracionCuposDao;

	@Autowired
	CentroSaludDao centroSaludDao;

	@SuppressWarnings("deprecation")
	private static Date copia(Date fecha) { // Desacoplaje del Paso por Referencia.
		return new Date(fecha.getYear(), fecha.getMonth(), fecha.getDate(), fecha.getHours(), fecha.getMinutes(),
				fecha.getSeconds());
	}

}
