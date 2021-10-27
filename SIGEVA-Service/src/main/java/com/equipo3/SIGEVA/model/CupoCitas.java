package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

@Document
public class CupoCitas {

	@Id
	private String uuid;

	@Field
	private CentroSalud centroSalud;

	@Field
	private Date fechaYHoraInicio;

	@Field
	private List<Usuario> pacientesCitados;

	@Field
	private int tamano;

	public CupoCitas() {

	}

	public CupoCitas(CentroSalud centroSalud, Date fechaYHoraInicio, List<Usuario> pacientesCitados) {
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

	public List<Usuario> getPacientesCitados() {
		return pacientesCitados;
	}

	public void setPacientesCitados(List<Usuario> pacientesCitados) {
		this.pacientesCitados = pacientesCitados;
		this.tamano = pacientesCitados.size();
	}

	public int getTamano() {
		return pacientesCitados.size();
	}

	public void setTamano(int tamano) {
		this.tamano = pacientesCitados.size();
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

	public void anadirPaciente(Usuario paciente, ConfiguracionCupos configuracionCupos)
			throws UsuarioInvalidoException, CupoCitasException {
		if (pacientesCitados.contains(paciente)) {
			throw new UsuarioInvalidoException("El usuario ya estaba contenido.");
		} else if (estaLleno(configuracionCupos.getNumeroPacientes())) {
			throw new CupoCitasException("El cupo ya ha alcanzado su máximo.");
		} else {
			pacientesCitados.add(paciente);
		}
	}

	public boolean pacienteEnlistado(Usuario paciente) {
		return pacientesCitados.contains(paciente);
	}

}
