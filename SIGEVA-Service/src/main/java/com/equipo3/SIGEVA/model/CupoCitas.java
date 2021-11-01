package com.equipo3.SIGEVA.model;

import com.equipo3.SIGEVA.controller.Condicionamientos;
import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document
public class CupoCitas implements Comparable<CupoCitas> {

	@Id
	private String uuid;

	@Field
	private CentroSalud centroSalud;

	@Field
	private Date fechaYHoraInicio;

	@Field
	private List<Paciente> pacientesCitados;

	@Field
	private int tamano;

	public CupoCitas() {

	}

	public CupoCitas(CentroSalud centroSalud, Date fechaYHoraInicio, List<Paciente> pacientesCitados) {
		this.uuid = UUID.randomUUID().toString();
		this.centroSalud = centroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.pacientesCitados = pacientesCitados;
		this.tamano = 0;
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

	public List<Paciente> getPacientesCitados() {
		return pacientesCitados;
	}

	public void setPacientesCitados(List<Paciente> pacientesCitados) {
		this.pacientesCitados = pacientesCitados;
		this.tamano = pacientesCitados.size();
	}

	public int getTamano() {
		return pacientesCitados.size();
	}

	public void setTamano(int tamano) {
		if (pacientesCitados != null && tamano == pacientesCitados.size()) {
			this.tamano = tamano;
		} else {
			System.out.println("Tamaño incoherente, salvo que no haya setteado lista aún.");
		}
	}

	@Override
	public String toString() {
		return "CupoCitas [uuid=" + uuid + ", centroSalud=" + centroSalud + ", fechaYHoraInicio=" + fechaYHoraInicio
				+ ", pacientesCitados=" + pacientesCitados + ", tamano=" + tamano + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(centroSalud, fechaYHoraInicio, pacientesCitados, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupoCitas other = (CupoCitas) obj;
		return Objects.equals(centroSalud, other.centroSalud)
				&& Objects.equals(fechaYHoraInicio, other.fechaYHoraInicio)
				&& Objects.equals(pacientesCitados, other.pacientesCitados) && Objects.equals(uuid, other.uuid);
	}

	public boolean estaLleno(int maximo) {
		return pacientesCitados.size() == maximo;
	}

	public void anadirPaciente(Paciente paciente, ConfiguracionCupos configuracionCupos)
			throws UsuarioInvalidoException, CupoCitasException {

		if (pacienteEnlistado(paciente) && Condicionamientos.control()) {
			throw new UsuarioInvalidoException("El paciente ya estaba contenido.");

		} else if (estaLleno(configuracionCupos.getNumeroPacientes()) && Condicionamientos.control()) {
			throw new CupoCitasException("El cupo ya había alcanzado su máximo.");

		} else {
			pacientesCitados.add(paciente);
			this.tamano++;
		}

	}

	public boolean pacienteEnlistado(Paciente paciente) {
		return pacientesCitados.contains(paciente);
	}

	public void eliminarPaciente(Paciente paciente) throws UsuarioInvalidoException {
		if (!pacienteEnlistado(paciente)) {
			throw new UsuarioInvalidoException("El paciente no estaba contenido.");

		} else {
			pacientesCitados.remove(paciente);
			this.tamano--;
		}
	}

	@Override
	public int compareTo(CupoCitas o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}

}
