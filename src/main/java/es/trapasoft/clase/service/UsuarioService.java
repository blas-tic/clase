package es.trapasoft.clase.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.trapasoft.clase.model.Administrador;
import es.trapasoft.clase.model.Alumno;
import es.trapasoft.clase.model.Persona;
import es.trapasoft.clase.model.Profesor;
import es.trapasoft.clase.model.Rol;
import es.trapasoft.clase.model.Usuario;
import es.trapasoft.clase.repository.AdministradorRepository;
import es.trapasoft.clase.repository.AlumnoRepository;
import es.trapasoft.clase.repository.ProfesorRepository;
import es.trapasoft.clase.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario crearUsuario(String email, String password, List<Rol> roles) {
        if (email == null || password == null || roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Email, password y roles son obligatorios");
        }
        if (usuarioRepository.existsById(email)) {
            throw new RuntimeException("El usuario con email " + email + " ya existe");
        }
        
        // Validar fortaleza de contraseña
        if (password.length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        Usuario usuario = new Usuario(email, passwordEncoder.encode(password), roles);
        return usuarioRepository.save(usuario);
    }

    public Alumno crearAlumno(String email, String password, String nombre,
            String apellidos, String telefono, String numeroMatricula) {

        // Crear usuario primero
        Usuario usuario = crearUsuario(email, password, Arrays.asList(Rol.ALUMNO));

        // Crear alumno
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellidos(apellidos);
        alumno.setTelefono(telefono);
        alumno.setNumMatricula(numeroMatricula);
        alumno.setFechaRegistro(LocalDate.now());
        alumno.setUsuario(usuario);

        return alumnoRepository.save(alumno);
    }

    public Profesor crearProfesor(String email, String password, String nombre,
            String apellidos, String telefono, String numeroEmpleado, String departamento) {

        Usuario usuario = crearUsuario(email, password, Arrays.asList(Rol.PROFESOR));

        Profesor profesor = new Profesor();
        profesor.setNombre(nombre);
        profesor.setApellidos(apellidos);
        profesor.setTelefono(telefono);
        profesor.setNumeroEmpleado(numeroEmpleado);
        profesor.setDepartamento(departamento);
        profesor.setUsuario(usuario);

        return profesorRepository.save(profesor);
    }

    public Administrador crearAdministrador(String email, String password, String nombre,
            String apellidos, String telefono, String nivelAcceso) {

        Usuario usuario = crearUsuario(email, password, Arrays.asList(Rol.ADMIN));

        Administrador admin = new Administrador();
        admin.setNombre(nombre);
        admin.setApellidos(apellidos);
        admin.setTelefono(telefono);
        admin.setNivelAcceso(nivelAcceso);
        admin.setFechaContratacion(LocalDate.now());
        admin.setUsuario(usuario);

        return administradorRepository.save(admin);
    }

    public Optional<Persona> obtenerPersonaPorEmail(String email) {
        // Intentar encontrar en cada tipo de persona
        Optional<Alumno> alumno = alumnoRepository.findByUsuarioEmail(email);
        if (alumno.isPresent())
            return Optional.of(alumno.get());

        Optional<Profesor> profesor = profesorRepository.findByUsuarioEmail(email);
        if (profesor.isPresent())
            return Optional.of(profesor.get());

        Optional<Administrador> admin = administradorRepository.findByUsuarioEmail(email);
        return admin.map(a -> (Persona) a);
    }

    // Método para verificar si existe una persona con un email
    public boolean existePersonaConEmail(String email) {
        return alumnoRepository.existsByUsuarioEmail(email) ||
                profesorRepository.existsByUsuarioEmail(email) ||
                administradorRepository.existsByUsuarioEmail(email);
    }
}
