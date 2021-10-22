package com.equipo3.SIGEVA.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateWrapper {

	// Formato: dd/MM/yyyy HH:mm

	public static String parseFromDateToString(Date fecha) {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return formateador.format(fecha);
	}

	@SuppressWarnings("deprecation")
	public static Date parseFromStringToDate(String cadena) {
		return new Date(Integer.parseInt(cadena.split(" ")[0].split("/")[2]) - 1900,
				Integer.parseInt(cadena.split(" ")[0].split("/")[1]) - 1,
				Integer.parseInt(cadena.split(" ")[0].split("/")[0]),
				Integer.parseInt(cadena.split(" ")[1].split(":")[0]),
				Integer.parseInt(cadena.split(" ")[1].split(":")[1]));
	}

}
