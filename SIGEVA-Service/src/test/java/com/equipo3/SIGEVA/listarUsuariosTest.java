package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.UsuarioController;
import com.equipo3.SIGEVA.controller.CentroController;
import com.equipo3.SIGEVA.dto.CentroSaludDTO;
import com.equipo3.SIGEVA.dto.PacienteDTO;
import com.equipo3.SIGEVA.dto.RolDTO;
import com.equipo3.SIGEVA.utils.Utilidades;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class listarUsuariosTest {

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private CentroController centroController;

    @Autowired
    private Utilidades utilidades;

    @Test
    public void getTodosUsuarios(){
        assertNotNull(usuarioController.getUsuarioByRol("Todos"));
    }

    @Test void getUsuariosAdministradores(){
        RolDTO rolDTO = utilidades.getRolByNombre("Administrador");
        assertNotNull(usuarioController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosSanitarios(){
        RolDTO rolDTO = utilidades.getRolByNombre("Sanitario");
        assertNotNull(usuarioController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getUsuariosPacientes(){
        RolDTO rolDTO = utilidades.getRolByNombre("Paciente");
        assertNotNull(usuarioController.getUsuarioByRol(rolDTO.getId()));
    }

    @Test void getPacientes(){
    	PacienteDTO paciente = new PacienteDTO();
    	paciente.setNombre(UUID.randomUUID().toString());
    	paciente.setUsername("Test getPacientes");
    	paciente.setRol(utilidades.getRolByNombre("Paciente"));
    	
    	CentroSaludDTO csDto = new CentroSaludDTO();
		csDto.setDireccion("test getPacientes direccion");
		csDto.setNombreCentro(UUID.randomUUID().toString());
		csDto.setNumVacunasDisponibles(80);

        centroController.crearCentroSalud(csDto);
		
		paciente.setCentroSalud(csDto);
		
    	usuarioController.crearUsuarioPaciente(paciente);
        for (PacienteDTO pacienteDTO : utilidades.getPacientes()){
            assertNotNull(pacienteDTO);
            utilidades.eliminarUsuario(paciente.getUsername());
        }
        utilidades.eliminarCentro(csDto.getId());
    }
}
