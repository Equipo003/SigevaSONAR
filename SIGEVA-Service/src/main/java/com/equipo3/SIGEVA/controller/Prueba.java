package com.equipo3.SIGEVA.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Prueba {

	public static void main(String[] args) throws ParseException {

		String cadena = "Sun Nov 07 09:00:00 CET 2021";

		System.out.println(cadena);

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

		Date d = format.parse(cadena);

	}

}
