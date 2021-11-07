package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WrapperModelToDTO {

    @Autowired
    private CentroSaludDao centroSaludDao;
    @Autowired
    private RolDao rolDao;

    public List<CentroSaludDTO> allCentroSaludToCentroSaludDTO(List<CentroSalud> centroSaludList){
        List<CentroSaludDTO> centroSaludDTOList = new ArrayList<>();

        for (CentroSalud centroSalud: centroSaludList) {
            CentroSaludDTO centroSaludDTO = new CentroSaludDTO();
            centroSaludDTO.setId(centroSalud.getId());
            centroSaludDTO.setNombreCentro(centroSalud.getNombreCentro());
            centroSaludDTO.setDireccion(centroSalud.getDireccion());
            centroSaludDTO.setNumVacunasDisponibles(centroSalud.getNumVacunasDisponibles());
            centroSaludDTOList.add(centroSaludDTO);
        }
        return centroSaludDTOList;
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

    public List<UsuarioDTO> listUsuarioToUsuarioDTO(List<Usuario> usuarioList){
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for(Usuario usuario: usuarioList) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setRol(rolDao.findById(usuario.getRol()).get());
            usuarioDTO.setCentroSalud(centroSaludDao.findById(usuario.getCentroSalud()).get());
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
        usuarioDTO.setRol(rolDao.findById(usuario.getRol()).get());
        usuarioDTO.setCentroSalud(centroSaludDao.findById(usuario.getCentroSalud()).get());
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
}
