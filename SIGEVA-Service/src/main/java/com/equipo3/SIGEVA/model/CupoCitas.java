package com.equipo3.SIGEVA.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

public class CupoCitas {

	private String uuid;
	private CentroSalud centroSalud;
	private Date fechaYHoraInicio;
	private List<Usuario> pacientesCitados;

	public CupoCitas() {

	}

	public CupoCitas(String uuid, CentroSalud centroSalud, Date fechaYHoraInicio, List<Usuario> pacientesCitados) {
		this.uuid = uuid;
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.pacientesCitados = pacientesCitados;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public CentroSalud getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(CentroSalud centroSalud) {
		this.centroSalud = centroSalud;
	}

	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	public List<Usuario> getPacientesCitados() {
		return pacientesCitados;
	}

	public void setPacientesCitados(List<Usuario> pacientesCitados) {
		this.pacientesCitados = pacientesCitados;
	}

	@Override
	public String toString() {
		return "CupoCitas [uuid=" + uuid + ", centroSalud=" + centroSalud + ", fechaYHoraInicio=" + fechaYHoraInicio
				+ ", pacientesCitados=" + pacientesCitados + "]";
	}

	public void anadirPaciente(Usuario paciente) throws UsuarioInvalidoException, CupoCitasException {
		if (pacientesCitados.contains(paciente)) {
			throw new UsuarioInvalidoException("El usuario ya estaba contenido.");
		} else if (pacientesCitados.size() + 1 > centroSalud.getConfiguracionCupos().getNumPacientesMaximo()) {
			throw new CupoCitasException("El cupo ya ha alcanzado su máximo.");
		} else {
			pacientesCitados.add(paciente);
		}
	}

	public boolean pacienteEnlistado(Usuario paciente) {
		return pacientesCitados.contains(paciente);
	}

	public static void main(String[] args) {

		List<CupoCitas> lista = prepararCuposCitas(new CentroSalud("", 0, null, new ConfiguracionCupos("", 30, 0),
				DateWrapper.parseFromStringToDate("01/01/2000 08:00"),
				DateWrapper.parseFromStringToDate("01/01/2000 20:00"), ""));

		for (int i = 0; i < lista.size(); i++)
			System.out.println(lista.get(i).toString());

	}

	private static final String FECHA_FIN = "31/12/2021";

	@SuppressWarnings("deprecation")
	public static List<CupoCitas> prepararCuposCitas(CentroSalud centroSalud) {

		List<CupoCitas> momentos = new ArrayList<>();

		Date fechaInicio = new Date(); // A contar desde hoy.
		fechaInicio.setHours(centroSalud.getHoraApertura().getHours());
		fechaInicio.setMinutes(centroSalud.getHoraApertura().getMinutes());
		fechaInicio.setSeconds(0);

		Date fechaFinAbsoluta = new Date(Integer.parseInt(FECHA_FIN.split("/")[2]) - 1900,
				Integer.parseInt(FECHA_FIN.split("/")[1]) - 1, Integer.parseInt(FECHA_FIN.split("/")[0]),
				centroSalud.getHoraCierre().getHours(), centroSalud.getHoraCierre().getMinutes());

		int duracionTramo = centroSalud.getConfiguracionCupos().getDuracionMinutos();

		Date fechaIterada = copia(fechaInicio);

		while (fechaIterada.before(fechaFinAbsoluta)) {
			fechaIterada.setHours(fechaInicio.getHours()); // Reinicio del día.
			fechaIterada.setMinutes(fechaInicio.getMinutes());

			Date fechaFinDiaria = copia(fechaIterada);
			fechaFinDiaria.setHours(fechaFinAbsoluta.getHours()); // Fin del día.
			fechaFinDiaria.setMinutes(fechaFinAbsoluta.getMinutes());

			while (fechaIterada.before(fechaFinDiaria)) {
				momentos.add(new CupoCitas(UUID.randomUUID().toString(), centroSalud, copia(fechaIterada),
						new ArrayList<>()));
				fechaIterada.setMinutes(fechaIterada.getMinutes() + duracionTramo);
			}
			fechaIterada.setDate(fechaIterada.getDate() + 1); // Cambio de día.

		}

		return momentos;
	}

	@SuppressWarnings("deprecation")
	public static Date copia(Date f1) {
		return new Date(f1.getYear(), f1.getMonth(), f1.getDate(), f1.getHours(), f1.getMinutes(), f1.getSeconds());
	}

}
