package com.equipo3.SIGEVA.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.equipo3.SIGEVA.dao.*;
import com.equipo3.SIGEVA.dto.*;
import com.equipo3.SIGEVA.exception.IdentificadorException;
import com.equipo3.SIGEVA.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.equipo3.SIGEVA.exception.CentroInvalidoException;
import com.equipo3.SIGEVA.exception.ConfiguracionYaExistente;
import com.equipo3.SIGEVA.exception.UsuarioInvalidoException;

/**
 * Controlador perteneciente al sistema web, sobre la gestión de vacunas COVID
 * SIGEVA, en el se especifican funcionalidades las cuales las puede realizar
 * solo el administrador.S
 *
 * @author Equipo3
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class AdministradorController {
    @Autowired
    private UsuarioDao administradorDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private CentroSaludDao centroSaludDao;
    @Autowired
    private ConfiguracionCuposDao configuracionCuposDao;
    @Autowired
    private VacunaDao vacunaDao;
    @Autowired
    private CupoDao cupoDao;
    @Autowired
    private CupoController cupoController;
    @Autowired
    private WrapperModelToDTO wrapperModelToDTO;

    @Autowired
    private CitaDao citaDao;


    @Autowired
    private CitaController citaController;

    @Autowired
    private WrapperDTOtoModel wrapperDTOtoModel = new WrapperDTOtoModel();

    private static final String FRASE_USUARIO_EXISTENTE = "El usuario ya existe en la base de datos";

    /**
     * Recurso web para la creación de un Administrador.
     *
     * @param administradorDTO Datos del administrador proporcionados por otro administrador
     *                         (usuario), a través de la interfaz gráfica del front end.
     */
    @PostMapping("/crearUsuarioAdministrador")
    public void crearUsuarioAdministrador(@RequestBody AdministradorDTO administradorDTO) {
        try {
            Administrador administrador = WrapperDTOtoModel.administradorDTOtoAdministrador(administradorDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(administrador.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }

            administradorDao.save(administrador);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la creación de un Paciente.
     *
     * @param pacienteDTO Datos del paciente proporcionados por el administrador
     *                    (usuario), a través de la interfaz gráfica del front end.
     */
    @PostMapping("/crearUsuarioPaciente")
    public void crearUsuarioPaciente(@RequestBody PacienteDTO pacienteDTO) {
        try {
            Paciente paciente = WrapperDTOtoModel.pacienteDTOtoPaciente(pacienteDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(paciente.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }
            administradorDao.save(paciente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    /**
     * Recurso web para la creación de un Sanitario.
     *
     * @param sanitarioDTO Datos del sanitario proporcionados por el administrador
     *                     (usuario), a través de la interfaz gráfica del front end.
     */
    @PostMapping("/crearUsuarioSanitario")
    public void crearUsuarioSanitario(@RequestBody SanitarioDTO sanitarioDTO) {
        try {
            Sanitario sanitario = WrapperDTOtoModel.sanitarioDTOtoSanitario(sanitarioDTO);
            Optional<Usuario> optUsuario = administradorDao.findByUsername(sanitario.getUsername());
            if (optUsuario.isPresent()) {
                throw new UsuarioInvalidoException(FRASE_USUARIO_EXISTENTE);
            }

            administradorDao.save(sanitario);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la creación de un nuevo centro de salud en el sistema.
     *
     * @param centroSaludDTO Centro de salud que viene del front end y que se crea a
     *                       partir de los datos proporcionados por el administrador
     *                       (usuario).
     */
    @PostMapping("/newCentroSalud")
    public String crearCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
        try {
        	System.out.println("centro "+ centroSaludDTO.getNombreCentro());
            centroSaludDTO.setVacuna(getVacunaByNombre("Pfizer"));
            CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
            Optional<CentroSalud> optCentroSalud = centroSaludDao.findByNombreCentro(centroSalud.getNombreCentro());
            if (optCentroSalud.isPresent()) {
                throw new CentroInvalidoException("El centro de salud ya existe en la base de datos");
            }
            centroSaludDao.save(centroSalud);

            return centroSalud.getId();

        } catch (Exception e) {
            System.out.println("He petado");
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la eliminación de un centro de salud registrador en el
     * sistema.
     *
     * @param centroSaludDTO Centro de salud que se quiere eliminar y que viene del
     *                       front end.
     */
    @PostMapping("/deleteCentroSalud")
    public void borrarCentroSalud(@RequestBody CentroSaludDTO centroSaludDTO) {
        try {
            centroSaludDTO.setVacuna(getVacunaByNombre("Pfizer"));
            CentroSalud centroSalud = this.wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO);
            Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud.getId());
            if (optCentroSalud.isPresent()) {
                if (cupoDao.buscarCuposOcupados(centroSalud.getId(), new Date()).isEmpty()) {
                    if (usuarioDao.findAllByCentroSalud(centroSalud.getId()).isEmpty()) {
                        centroSaludDao.deleteById(centroSalud.getId());
                    } else {
                        throw new CentroInvalidoException("El centro de salud NO se puede borrar por contener usuarios.");
                    }

                } else {
                    throw new CentroInvalidoException("El centro de salud NO se puede borrar por contener citas.");
                }
            } else {
                throw new CentroInvalidoException("El centro de salud NO existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    /**
     * Método para la creación de un rol.
     *
     * @param rolDTO Rol el cual se quiere crear en la bbdd.
     */
    public void crearRol(@RequestBody RolDTO rolDTO) {
        try {
            rolDao.save(WrapperDTOtoModel.rolDTOToRol(rolDTO));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la obtención de todos los centros de salud que tiene el
     * sistema registrados.
     *
     * @return List<CentroSaludDTO> Lista que contiene todos los centros de salud
     * que tiene el sistema.
     */
    @GetMapping("/getCentros")
    public List<CentroSaludDTO> listarCentros() {
        return wrapperModelToDTO.allCentroSaludToCentroSaludDTO(centroSaludDao.findAll());
    }

    /**
     * Recurso web para la obtención de todos los roles que se encuentran en la
     * bbdd.
     *
     * @return List<RolDTO> Roles que se encuentran en la bbdd.
     */
    @GetMapping("/getRoles")
    public List<RolDTO> listarRoles() {
        try {
            return wrapperModelToDTO.allRolToRolDTO(rolDao.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Recurso para la obtención de todos los usuario por Rol, teniendo la opción de
     * devolver todos (rol=Todos)
     *
     * @param rol Identificador del rol que van a tener los usuarios, los cuales se
     *            quieren obtener.
     * @return List<UsuarioDTO> Lista de usuarios que tiene el rol especificado.
     */
    @GetMapping("/getUsuariosByRol")
    public List<UsuarioDTO> getUsuarioByRol(@RequestParam String rol) {
        try {
            if (rol.equals("Todos")) {
                return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAll());
            } else {
                return wrapperModelToDTO.allUsuarioToUsuarioDTO(administradorDao.findAllByRol(rol));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Método para la obtención de un paciente a partir de su identificador.
     *
     * @param id Identificador del paciente que se quiere obtener de la bbdd.
     * @return PacienteDTO Paciente obtenido de la bbdd a partir de su
     * identificador.
     */
    @GetMapping("/paciente")
    public PacienteDTO getPaciente(@RequestParam String id) {
        try {
            Optional<Usuario> optPaciente = administradorDao.findById(id);
            if (optPaciente.isPresent()) {
                return wrapperModelToDTO.pacienteToPacienteDTO(optPaciente.get());
            }
            throw new UsuarioInvalidoException("El paciente no existe en la base de datos");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la asiganción de centro de salud a los sanitarios.
     *
     * @param username Nombre de usuario del sanitario al que se le va a asignar el
     *                 centro de salud.
     * @param centro   Identificador del centro de salud que se va a asignar al
     *                 sanitario.
     */
    @PutMapping("/fijarCentro/{username}/{centro}")
    public void fijarPersonal(@PathVariable String username, @PathVariable String centro) {
        try {
            Optional<Usuario> optUsuario = administradorDao.findByUsername(username);
            if (optUsuario.isPresent()) {
                Usuario sanitario = optUsuario.get();
                sanitario.setCentroSalud(centro);
                administradorDao.save(sanitario);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la creción de la configuración de cupos que va a tener
     * activa el sistema.
     *
     * @param configuracionCuposDTO Configuración de cupos que le llega desde el
     *                              front end.
     */
    @PostMapping("/crearConfCupos")
    public void crearConfiguracionCupos(@RequestBody ConfiguracionCuposDTO configuracionCuposDTO) {

        try {
            ConfiguracionCupos configuracionCupos = WrapperDTOtoModel
                    .configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTO);

            List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
                    .allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
            if (configuracionCuposDTOList.isEmpty())
                configuracionCuposDao.save(configuracionCupos);
            else
                throw new ConfiguracionYaExistente("Ya existe una configuración de cupos");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }

    }

    /**
     * Método que comprueba si hay o no una configuración de cupos.
     *
     * @return boolean Si tiene el sistema una configuración activa o no.
     */
    @GetMapping("/existConfCupos")
    public boolean existConfiguracionCupos() {
        try {
            List<ConfiguracionCupos> configuracionCuposList = configuracionCuposDao.findAll();
            return !configuracionCuposList.isEmpty();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    /**
     * Obtención de la cofiguración de cupos que tiene configurada el sistema.
     *
     * @return ConfiguracionCuposDTO Configuración que tiene activada el sistema.
     */
    @GetMapping("/getConfCupos")
    public ConfiguracionCuposDTO getConfiguracionCupos() {
        try {
            List<ConfiguracionCuposDTO> configuracionCuposDTOList = this.wrapperModelToDTO
                    .allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());

            if (configuracionCuposDTOList.isEmpty())
                throw new IdentificadorException("No existe una configuración de cupos");

            return configuracionCuposDTOList.get(0);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Método para la eliminación de la configuración de cupos que tiene activada el
     * sistema
     */
    public void eliminarConfiguracionCupos() {
        try {
            List<ConfiguracionCuposDTO> configuracionCuposDTOList = wrapperModelToDTO
                    .allConfiguracionCuposToConfiguracionCuposDTO(configuracionCuposDao.findAll());
            configuracionCuposDao.delete(
                    WrapperDTOtoModel.configuracionCuposDTOtoConfiguracionCupos(configuracionCuposDTOList.get(0)));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Recurso web para la modificación de la dosis disponibles que tiene un centro
     * de salud.
     *
     * @param centroSalud Identificador del centro de salud de cual se quieren
     *                    modificar las dosis de vacunas disponibles.
     * @param vacunas     Número de vacunas que se quieren añadir a las disponibles
     *                    del centro de salud.S
     */
    @PutMapping("/modificarDosisDisponibles/{centroSalud}/{vacunas}")
    public void modificarNumeroVacunasDisponibles(@PathVariable String centroSalud, @PathVariable int vacunas) {
        try {
            Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(centroSalud);
            if (optCentroSalud.isPresent()) {
                CentroSaludDTO centroSaludDTO = wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
                centroSaludDTO.incrementarNumVacunasDisponibles(vacunas);
                centroSaludDao.save(wrapperDTOtoModel.centroSaludDTOtoCentroSalud(centroSaludDTO));
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso para la obtención de un centro de salud a partir de su identificador.
     *
     * @param idCentroSalud Identificador del centro de salud, el cual queremos
     *                      obtener de la bbdd.
     * @return CentroSaludDTO Centro de salud obtenido de la bbdd.
     */
    @GetMapping("/getCentroSaludById")
    public CentroSaludDTO getCentroById(@RequestParam String idCentroSalud) {
        try {
            Optional<CentroSalud> optCentroSalud = centroSaludDao.findById(idCentroSalud);
            if (optCentroSalud.isPresent()) {
                return wrapperModelToDTO.centroSaludToCentroSaludDTO(optCentroSalud.get());
            }
            throw new IdentificadorException("Identificador Centro Salud " + idCentroSalud + " no existe");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Obtención del rol a partir de su identificador.
     *
     * @param rol Identificador del rol, el cual se quiere obtener de la bbdd.
     * @return RolDTO Rol obtenido de la bbdd.
     */
    public RolDTO getRolById(String rol) {
        try {
            Optional<Rol> optRol = rolDao.findById(rol);
            if (optRol.isPresent()) {
                return wrapperModelToDTO.rolToRolDTO(optRol.get());
            }
            throw new Exception();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /*
     * @GetMapping("/getPacientesJornada") public List<PacienteDTO>
     * getPacientesJornada(@RequestParam String fechaJornada){ SimpleDateFormat
     * formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); try{ Date
     * fechaJornadaFormated = formateador.parse(fechaJornada); List<CupoCitas>
     * cupoCitasList =
     * cupoController.cupoCitasDao.findByFechaYHoraInicio(fechaJornadaFormated); //
     * MAL! List<Paciente> pacientesJornada = new ArrayList<Paciente>();
     * Iterator<CupoCitas> cupoCitasIterator = cupoCitasList.iterator();
     * while(cupoCitasIterator.hasNext()){
     * pacientesJornada.addAll(cupoCitasIterator.next().getPacientesCitados()); }
     *
     * return
     * this.wrapperModelToDTO.pacientesJornadaToPacientesDTO(pacientesJornada);
     * }catch (ParseException p){ throw new
     * ResponseStatusException(HttpStatus.BAD_REQUEST); }
     *
     * }
     */

    /**
     * Recurso web para la obtención de un usuario a partir de su identificador.
     *
     * @param idUsuario Identificador de usuario que se quiere obtener de la bbdd.s
     * @return UsuarioDTO Usuario obtenido de la bbdd a partir de su identificador.
     */
    @GetMapping("/getUsuarioById")
    public UsuarioDTO getUsuarioById(@RequestParam String idUsuario) {
        try {
            Optional<Usuario> optUsuario = administradorDao.findById(idUsuario);
            if (optUsuario.isPresent()) {
                return wrapperModelToDTO.usuarioToUsuarioDTO(optUsuario.get());
            }
            throw new IdentificadorException("Identificador Usuario " + idUsuario + " no encontrado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Eliminación de usuario a partir del nombre de usuario.
     *
     * @param username Nombre de usuario que tiene dicho usuario.
     */
    public void eliminarUsuario(String username) {
        try {
            administradorDao.deleteByUsername(username);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Eliminación de Centro de Salud a partir del identificador de él.
     *
     * @param idCentro Identificador del centro de salud.
     */
    public void eliminarCentro(String idCentro) {
        try {
            centroSaludDao.deleteById(idCentro);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Obtención de un rol de la bbdd, a partir del nombre del rol.
     *
     * @param nombreRol Nombre del rol que queremos obtener.
     * @return RolDTO Rol obtenido de la bbdd.
     */
    public RolDTO getRolByNombre(String nombreRol) {
        try {
            Optional<Rol> optRol = rolDao.findByNombre(nombreRol);
            if (optRol.isPresent()) {
                return wrapperModelToDTO.rolToRolDTO(optRol.get());
            }
            throw new IdentificadorException("Identificador Rol " + nombreRol + " no encontrado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Obtención de los pacientes que están gestionados a partir de SIGEVA.
     *
     * @return List<PacienteDTO> Lista de pacientes que contiene todos los Pacientes
     * que se encuentran en el sistema.
     */
    public List<PacienteDTO> getPacientes() {
        try {
            List<Usuario> optUsuario = administradorDao.findAllByClass("com.equipo3.SIGEVA.model.Paciente");
            if (!optUsuario.isEmpty()) {
                return wrapperModelToDTO.allPacienteToPacienteDTO(optUsuario);
            }
            throw new IdentificadorException("No hay pacientes registrados");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Método para la inserción de una vacuna a la bbdd´.
     *
     * @param vacunaDTO Vacuna que se va a crear en la bbdd.
     */
    public void addVacuna(VacunaDTO vacunaDTO) {
        try {
            Vacuna vacuna = WrapperDTOtoModel.vacunaDTOToVacuna(vacunaDTO);
            vacunaDao.save(vacuna);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Método para la obtención de la vacuna a partir del nombre de ella.
     *
     * @param pfizer Nombre que tiene la vacuna.
     * @return VacunaDTO Vacuna obtenida de la BBDD a partir de su nombre.
     */
    public VacunaDTO getVacunaByNombre(String pfizer) {
        try {
            Optional<Vacuna> optVacuna = vacunaDao.findByNombre(pfizer);
            if (optVacuna.isPresent()) {
                return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());
               
            }
            throw new IdentificadorException("Identificador Vacuna " + pfizer + " no encontrado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Método para la obtención de la vacuna a partir del identificador de ella.
     *
     * @param id Identificador de la vacuna que queremos encontrar en la BBDD.
     * @return VacunaDTO Vacuna obtenida de la BBDD a partir de su identificador.
     */
    public VacunaDTO getVacunaById(String id) {
        try {
            Optional<Vacuna> optVacuna = vacunaDao.findById(id);
            if (optVacuna.isPresent()) {
                return wrapperModelToDTO.vacunaToVacunaDTO(optVacuna.get());
            }
            throw new IdentificadorException("Identificador Vacuna " + id + " no encontrado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Método para la eliminación de una vacuna, la cual esta disponible.
     *
     * @param idVacuna Identificador de la vacuna que se quiere eliminar.s
     */
    public void eliminarVacuna(String idVacuna) {
        try {
            vacunaDao.deleteById(idVacuna);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Metodo para la eliminación de un rol.
     *
     * @param idRol Identificador del rol que se quiere eliminar.
     */
    public void eliminarRol(String idRol) {
        try {
            rolDao.deleteById(idRol);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Recurso web para la modificación de los centros de salud por parte de los
     * administradores.
     *
     * @param csDto Centro de salud modificado
     */
    @PostMapping("/updateCS")
    public void modificarCentroSalud(@RequestBody CentroSaludDTO csDto) {
        try {

            Optional<CentroSalud> optCentro = centroSaludDao.findById(csDto.getId());
            if (!optCentro.isPresent()) {
                throw new CentroInvalidoException("El centro de salud no existe");
            }
            centroSaludDao.save(wrapperDTOtoModel.centroSaludDTOtoCentroSalud(csDto));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/updateUsuario")
    public void editarUsuario(@RequestBody UsuarioDTO newUsuarioDTO) {
        try {
            boolean updateValido = true;
            Optional<Usuario> usuarioOpt = administradorDao.findById(newUsuarioDTO.getIdUsuario());

            if (!usuarioOpt.isPresent()) {
                throw new UsuarioInvalidoException("Usuario no existe en el sistema");
            }
            UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
            
            if (usuariodto.getRol().getNombre().equals("Paciente")) {
                PacienteDTO pacienteDTO = getPaciente(usuariodto.getIdUsuario());
                if (pacienteDTO.getNumDosisAplicadas() != 0) {
                    throw new UsuarioInvalidoException("No puedes modificar el centro de un usuario que ya ha aplicado una dosis");
                } else {
                    citaController.eliminarCitasFuturasDelPaciente(pacienteDTO);
                }
            }

            UsuarioDTO oldUsuario = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
            oldUsuario.setCentroSalud(newUsuarioDTO.getCentroSalud());
            oldUsuario.setUsername(newUsuarioDTO.getUsername());
            oldUsuario.setCorreo(newUsuarioDTO.getCorreo());
            oldUsuario.setHashPassword(newUsuarioDTO.getHashPassword());
            oldUsuario.setDni(newUsuarioDTO.getDni());
            oldUsuario.setNombre(newUsuarioDTO.getNombre());
            oldUsuario.setApellidos(newUsuarioDTO.getApellidos());
            oldUsuario.setFechaNacimiento(newUsuarioDTO.getFechaNacimiento());
            oldUsuario.setImagen(newUsuarioDTO.getImagen());

            switch (newUsuarioDTO.getRol().getNombre()) {
                case "Administrador":
                    administradorDao.save(wrapperDTOtoModel.administradorDTOtoAdministrador((AdministradorDTO) oldUsuario));
                    break;
                case "Paciente":
                    administradorDao.save(wrapperDTOtoModel.pacienteDTOtoPaciente((PacienteDTO) oldUsuario));
                    break;
                case "Sanitario":
                    administradorDao.save(wrapperDTOtoModel.sanitarioDTOtoSanitario((SanitarioDTO) oldUsuario));
                    break;
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/deleteUsuario/{idUsuario}")
    public void deleteUsuarioById(@PathVariable String idUsuario) {
        try {
            Optional<Usuario> usuarioOpt = administradorDao.findById(idUsuario);
            if (!usuarioOpt.isPresent()) {
                throw new UsuarioInvalidoException("Usuario no existe en el sistema");
            }
            UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
            if (usuariodto.getRol().getNombre().equals("Paciente")) {
                PacienteDTO pacienteDTO = getPaciente(usuariodto.getIdUsuario());
                if (pacienteDTO.getNumDosisAplicadas() != 0) {
                    throw new UsuarioInvalidoException("No puedes eliminar el usuario porque ya tiene aplicada 1 o más dosis");
                } else {
                    citaController.eliminarCitasFuturasDelPaciente(pacienteDTO);
                }
            }else if(usuariodto.getRol().getNombre().equals("Administrador")) {
            	throw new UsuarioInvalidoException("No puedes eliminar el usuario porque es administrador.");
            }
            administradorDao.deleteById(idUsuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        try {
            Optional<Usuario> usuarioOpt = administradorDao.findByUsername(usuarioLoginDTO.getUsername());
            if (!usuarioOpt.isPresent()) {
                throw new UsuarioInvalidoException("Usuario no existe en el sistema");
            }
            UsuarioDTO usuariodto = wrapperModelToDTO.usuarioToUsuarioDTO(usuarioOpt.get());
            if (!usuariodto.getHashPassword().equals(usuarioLoginDTO.getHashPassword())) {
                throw new UsuarioInvalidoException("Contraseña incorrecta");
            }

            return new TokenDTO(usuariodto.getIdUsuario(), usuariodto.getRol().getNombre());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/getCentroSanitario")
    public CentroSaludDTO getCentroSanitario(@RequestParam String idUsuario){
        try{
            Optional<Usuario> optUsuario = this.usuarioDao.findById(idUsuario);
            if(!optUsuario.isPresent()){
                throw new UsuarioInvalidoException("Usuario no existe en el sistema");
            }
            Usuario usuario = optUsuario.get();
            CentroSaludDTO centroSaludDTO = this.wrapperModelToDTO.centroSaludToCentroSaludDTO(this.centroSaludDao.findById(usuario.getCentroSalud()).get());

            return centroSaludDTO;

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}