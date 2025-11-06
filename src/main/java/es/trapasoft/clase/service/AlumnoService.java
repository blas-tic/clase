package es.trapasoft.clase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.trapasoft.clase.model.Alumno;
import es.trapasoft.clase.repository.AlumnoRepository;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> findAll() {
        return alumnoRepository.findAll();
    }

    public Optional<Alumno> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return alumnoRepository.findById(id);
    }

    public Alumno save(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser nulo");
        }
        return alumnoRepository.save(alumno);
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        alumnoRepository.deleteById(id);
    }

    public List<Alumno> findByNombre(String cadena) {
        return alumnoRepository.buscarPorNombreOApellidos(cadena);
    }

    public boolean existsByEmail(String email) {
        return alumnoRepository.existsByUsuarioEmail(email);
    }

}
