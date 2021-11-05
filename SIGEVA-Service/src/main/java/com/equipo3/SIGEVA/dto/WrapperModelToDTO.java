package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.dao.CentroSaludDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dao.UsuarioDao;
import com.equipo3.SIGEVA.model.CentroSalud;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class WrapperModelToDTO {

    private static CentroSaludDao centroSaludDao;
    private static RolDao rolDao;
    private static UsuarioDao usuarioDao;

    public static List<CentroSaludDTO> allcentroSaludToCentroSaludDTO(){
        List<CentroSaludDTO> centroSaludDTOList = new ArrayList<>();
        List<CentroSalud> centroSaludList = centroSaludDao.findAll();

        for (CentroSalud centroSalud: centroSaludList) {
            CentroSaludDTO centroSaludDTO = new CentroSaludDTO();
            centroSaludDTO.setId(centroSalud.getId());
            centroSaludDTO.setNombreCentro(centroSalud.getNombreCentro());
            centroSaludDTO.setDireccion(centroSalud.getDireccion());
            centroSaludDTO.setNumVacunasDisponibles(centroSalud.getNumVacunasDisponibles());
        }
        return centroSaludDTOList;
    }

    public static List<RolDTO> allRolToRolDTO(){
        List<RolDTO> rolDTOList = new ArrayList<>();
        List<Rol> rolList = rolDao.findAll();

        for (Rol rol: rolList) {
            RolDTO rolDTO = new RolDTO();
            rolDTO.setId(rol.getId());
            rolDTO.setNombre(rol.getNombre());
        }
        return rolDTOList;
    }

    public static List<UsuarioDTO> allUsuarioToUsuarioDTO(String rol){
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        List<Usuario> usuarioList;
        if (rol.equals("Todos")) {
            usuarioList = usuarioDao.findAll();
        } else {
            usuarioList = usuarioDao.findAllByRol(rol);
        }

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
        }
        return usuarioDTOList;
    }
}
