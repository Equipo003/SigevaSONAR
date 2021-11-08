package com.equipo3.SIGEVA.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.ConfiguracionCupos;
import com.equipo3.SIGEVA.model.Paciente;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;
import com.equipo3.SIGEVA.model.Vacuna;

@Component
public class WrapperModelToDTO {
	
    @Autowired
    private AdministradorController administradorController;

    public List<CentroSaludDTO> allCentroSaludToCentroSaludDTO(List<CentroSalud> centroSaludList){
        List<CentroSaludDTO> centroSaludDTOList = new ArrayList<>();

        for (CentroSalud centroSalud: centroSaludList) {
            CentroSaludDTO centroSaludDTO = new CentroSaludDTO();
            centroSaludDTO.setId(centroSalud.getId());
            centroSaludDTO.setNombreCentro(centroSalud.getNombreCentro());
            centroSaludDTO.setDireccion(centroSalud.getDireccion());
            centroSaludDTO.setNumVacunasDisponibles(centroSalud.getNumVacunasDisponibles());
            centroSaludDTO.setVacuna(administradorController.getVacunaById(centroSalud.getVacuna()));
            centroSaludDTOList.add(centroSaludDTO);
        }
        return centroSaludDTOList;
    }

    public CentroSaludDTO centroSaludToCentroSaludDTO(CentroSalud centroSalud){
            CentroSaludDTO centroSaludDTO = new CentroSaludDTO();
            centroSaludDTO.setId(centroSalud.getId());
            centroSaludDTO.setNombreCentro(centroSalud.getNombreCentro());
            centroSaludDTO.setDireccion(centroSalud.getDireccion());
            centroSaludDTO.setNumVacunasDisponibles(centroSalud.getNumVacunasDisponibles());
            centroSaludDTO.setVacuna(administradorController.getVacunaById(centroSalud.getVacuna()));

        return centroSaludDTO;
    }

    public List<RolDTO> allRolToRolDTO(List<Rol> rolList){
        List<RolDTO> rolDTOList = new ArrayList<>();

        for (Rol rol: rolList) {
            RolDTO rolDTO = new RolDTO();
            rolDTO.setId(rol.getId());
            rolDTO.setNombre(rol.getNombre());
            rolDTOList.add(rolDTO);
        }
        return rolDTOList;
    }

    public RolDTO rolToRolDTO(Rol rol){
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setNombre(rol.getNombre());

        return rolDTO;
    }

    public List<UsuarioDTO> listUsuarioToUsuarioDTO(List<Usuario> usuarioList){
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for(Usuario usuario: usuarioList) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setRol(administradorController.getRolById(usuario.getRol()));
            usuarioDTO.setCentroSalud(administradorController.getCentroById(usuario.getCentroSalud()));
            usuarioDTO.setUsername(usuario.getUsername());
            usuarioDTO.setCorreo(usuario.getCorreo());
            usuarioDTO.setHashPassword(usuario.getHashPassword());
            usuarioDTO.setDni(usuario.getDni());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setApellidos(usuario.getApellidos());
            usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioDTO.setImagen(usuario.getImagen());

            usuarioDTOList.add(usuarioDTO);
        }
        return usuarioDTOList;
    }

    public List<ConfiguracionCuposDTO> configuracionCuposToConfiguracionCuposDTO(List<ConfiguracionCupos> configuracionCuposList){

        List<ConfiguracionCuposDTO> configuracionCuposDTOList = new ArrayList<>();

        for (ConfiguracionCupos configuracionCupos: configuracionCuposList) {
            ConfiguracionCuposDTO configuracionCuposDTO = new ConfiguracionCuposDTO();

            configuracionCuposDTO.setId(configuracionCupos.getId());
            configuracionCuposDTO.setDuracionMinutos(configuracionCupos.getDuracionMinutos());
            configuracionCuposDTO.setDuracionJornadaHoras(configuracionCupos.getDuracionJornadaHoras());
            configuracionCuposDTO.setDuracionJornadaMinutos(configuracionCupos.getDuracionJornadaMinutos());
            configuracionCuposDTO.setFechaInicio(configuracionCupos.getFechaInicio());
            configuracionCuposDTO.setNumeroPacientes(configuracionCupos.getNumeroPacientes());

            configuracionCuposDTOList.add(configuracionCuposDTO);
        }

        return configuracionCuposDTOList;
    }

    public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setRol(administradorController.getRolById(usuario.getRol()));
        usuarioDTO.setCentroSalud(administradorController.getCentroById(usuario.getCentroSalud()));
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setHashPassword(usuario.getHashPassword());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setImagen(usuario.getImagen());

        return usuarioDTO;
    }

    public List<PacienteDTO> pacientesJornadaToPacientesDTO (List<Paciente> pacientesJornadas){
        return new ArrayList<PacienteDTO>();
    }

    public List<PacienteDTO> listPacienteToPacienteDTO(List<Usuario> pacienteList) throws PacienteYaVacunadoException, NumVacunasInvalido {

        List<PacienteDTO> pacienteDTOList = new ArrayList<>();

        for (Usuario paciente: pacienteList) {

            PacienteDTO pacienteDTO = new PacienteDTO();

            Paciente pacienteCast = (Paciente) paciente;

            pacienteDTO.setIdUsuario(pacienteCast.getIdUsuario());
            pacienteDTO.setRol(administradorController.getRolById(pacienteCast.getRol()));
            pacienteDTO.setCentroSalud(administradorController.getCentroById(pacienteCast.getCentroSalud()));
            pacienteDTO.setUsername(pacienteCast.getUsername());
            pacienteDTO.setCorreo(pacienteCast.getCorreo());
            pacienteDTO.setHashPassword(pacienteCast.getHashPassword());
            pacienteDTO.setDni(pacienteCast.getDni());
            pacienteDTO.setNombre(pacienteCast.getNombre());
            pacienteDTO.setApellidos(pacienteCast.getApellidos());
            pacienteDTO.setFechaNacimiento(pacienteCast.getFechaNacimiento());
            pacienteDTO.setImagen(pacienteCast.getImagen());

            pacienteDTO.setNumDosisAplicadas(pacienteCast.getNumDosisAplicadas());

            pacienteDTOList.add(pacienteDTO);
        }

        return pacienteDTOList;
    }

    public PacienteDTO pacienteToPacienteDTO(Usuario paciente) throws PacienteYaVacunadoException, NumVacunasInvalido {

            PacienteDTO pacienteDTO = new PacienteDTO();

            Paciente pacienteCast = (Paciente) paciente;

            pacienteDTO.setIdUsuario(pacienteCast.getIdUsuario());
            pacienteDTO.setRol(administradorController.getRolById(pacienteCast.getRol()));
            pacienteDTO.setCentroSalud(administradorController.getCentroById(pacienteCast.getCentroSalud()));
            pacienteDTO.setUsername(pacienteCast.getUsername());
            pacienteDTO.setCorreo(pacienteCast.getCorreo());
            pacienteDTO.setHashPassword(pacienteCast.getHashPassword());
            pacienteDTO.setDni(pacienteCast.getDni());
            pacienteDTO.setNombre(pacienteCast.getNombre());
            pacienteDTO.setApellidos(pacienteCast.getApellidos());
            pacienteDTO.setFechaNacimiento(pacienteCast.getFechaNacimiento());
            pacienteDTO.setImagen(pacienteCast.getImagen());

            pacienteDTO.setNumDosisAplicadas(pacienteCast.getNumDosisAplicadas());

        return pacienteDTO;
    }

    public VacunaDTO vacunaToVacunaDTO(Vacuna vacuna){
        VacunaDTO vacunaDTO = new VacunaDTO();

        vacunaDTO.setId(vacuna.getId());
        vacunaDTO.setNombre(vacuna.getNombre());
        vacunaDTO.setDiasEntreDosis(vacuna.getDiasEntreDosis());
        vacunaDTO.setNumDosis(vacuna.getNumDosis());

        return vacunaDTO;
    }
}
