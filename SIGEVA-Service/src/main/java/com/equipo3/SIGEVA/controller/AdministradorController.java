package com.equipo3.SIGEVA.controller;

import com.equipo3.SIGEVA.dao.AdministradorDao;
import com.equipo3.SIGEVA.dao.RolDao;
import com.equipo3.SIGEVA.model.Administrador;
import com.equipo3.SIGEVA.model.Rol;
import com.equipo3.SIGEVA.model.Usuario;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class AdministradorController {

	@Autowired
	private AdministradorDao adminDao;
	@Autowired
	private RolDao rolDao;

	public void crearUsuarioAdministrador(Map<String, Object> user) {
		try {

			Usuario admin = new Administrador();
			JSONObject jso = new JSONObject(user);


			admin.setRol(jso.optString("rol"));
			admin.setCentroFK(jso.optString("centro"));
			admin.setUsername(jso.optString("username"));
			admin.setCorreo(jso.optString("correo"));
			admin.setHashPassword(jso.optString("hashPassword"));
			admin.setDni(jso.optString("dni"));
			admin.setNombre(jso.optString("nombre"));
			admin.setApellidos(jso.optString("apellidos"));
			admin.setFechaNacimiento(new SimpleDateFormat("dd/MM/yyyy").parse(jso.optString("fechaNacimiento")));
			admin.setImagen(jso.optString("imagen"));

			Optional<Usuario> optUsuario = adminDao.findByUsername(jso.optString("userName"));
			if (optUsuario.isPresent()){
				throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe en la base de datos");
			}

			adminDao.save(admin);

		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	public void crearUsuarioPaciente(Map<String, Object> user) {

	}
	public void crearUsuarioSanitario(Map<String, Object> user) {

	}

	@PostMapping("/crearUsuario")
	public void registrarUsuario(@RequestBody Map<String, Object> info) {
		try {
			JSONObject jso = new JSONObject(info);
			String rol = jso.optString("rol");
			List<Rol> optRol = rolDao.findAll();
			for (Rol rol1 : optRol) {
				if (rol1.getId().equals(rol) && rol1.getNombre().equals("Administrador")) {
					crearUsuarioAdministrador(info);
					break;
				}
				else if (rol1.getNombre().equals("Paciente")){
					crearUsuarioPaciente(info);
					break;
				}
				else {
					crearUsuarioSanitario(info);
					break;
				}
			}
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping("/registrarRol")
	public void registrarRol(@RequestBody Rol rol){
		try {
			rolDao.save(rol);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}


	@GetMapping("/getRoles")
	public List<Rol> ListarRoles() {
		try {
			return rolDao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}