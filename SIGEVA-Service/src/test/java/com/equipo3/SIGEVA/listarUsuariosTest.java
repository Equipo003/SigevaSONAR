package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.AdministradorController;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.dto.UsuarioDTO;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class listarUsuariosTest {

    @Autowired
    private AdministradorController administradorController;

    @Autowired
    private Utilidades utilidades;

    @Test
    public void getTodosUsuarios(){
        assertNotNull(administradorController.getUsuarioByRol("Todos"));
    }

    @Test void getUsuariosAdministradores(){
        RolDTO rolDTO = utilidades.getRolByNombre("Administrador");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosSanitarios(){
        RolDTO rolDTO = utilidades.getRolByNombre("Sanitario");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosPacientes(){
        RolDTO rolDTO = utilidades.getRolByNombre("Paciente");
        assertNotNull(administradorController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getPacientes(){
    	PacienteDTO paciente = new PacienteDTO();
    	paciente.setNombre("Test getPacientes");
    	paciente.setUsername("Test getPacientes");
    	paciente.setRol(utilidades.getRolByNombre("Paciente"));
    	
    	CentroSaludDTO csDto = new CentroSaludDTO();
		csDto.setDireccion("test getPacientes direccion");
		csDto.setNombreCentro("CentroTest getPaciente");
		csDto.setNumVacunasDisponibles(80);
		
		administradorController.crearCentroSalud(csDto);
		
		paciente.setCentroSalud(csDto);
		
    	administradorController.crearUsuarioPaciente(paciente);
        for (PacienteDTO pacienteDTO : utilidades.getPacientes()){
            assertNotNull(pacienteDTO);
            utilidades.eliminarUsuario(paciente.getUsername());
        }
        administradorController.borrarCentroSalud(csDto);
    }
}
