package com.equipo3.SIGEVA.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ConfiguracionCupos {

	private int duracionMinutos;
	private int numeroPacientes;
	private int duracionJornadaHoras;
	private int duracionJornadaMinutos;
	private String fechaInicio;

	public ConfiguracionCupos() {
	}

	public ConfiguracionCupos(int duracionMinutos, int numeroPacientes, int duracionJornadaHoras,
			int duracionJornadaMinutos, String fechaInicio) {
		this.duracionMinutos = duracionMinutos;
		this.numeroPacientes = numeroPacientes;
		this.duracionJornadaHoras = duracionJornadaHoras;
		this.duracionJornadaMinutos = duracionJornadaMinutos;
		this.fechaInicio = fechaInicio;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public void setNumeroPacientes(int numeroPacientes) {
		this.numeroPacientes = numeroPacientes;
	}

	public int getDuracionMinutos() {
		return this.duracionMinutos;
	}

	public int getNumeroPacientes() {
		return numeroPacientes;
	}

	public int getDuracionJornadaHoras() {
		return duracionJornadaHoras;
	}

	public void setDuracionJornadaHoras(int duracionJornadaHoras) {
		this.duracionJornadaHoras = duracionJornadaHoras;
	}

	public int getDuracionJornadaMinutos() {
		return duracionJornadaMinutos;
	}

	public void setDuracionJornadaMinutos(int duracionJornadaMinutos) {
		this.duracionJornadaMinutos = duracionJornadaMinutos;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaInicioAsDate() {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			return formateador.parse(fechaInicio);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}




	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@SuppressWarnings("deprecation")
	public Date getHoraFin() {
		Date fechaFin = getFechaInicioAsDate();
		fechaFin.setHours(fechaFin.getHours() + this.duracionJornadaHoras);
		fechaFin.setMinutes(fechaFin.getMinutes() + this.duracionJornadaMinutos);

		return fechaFin;
	}
}
