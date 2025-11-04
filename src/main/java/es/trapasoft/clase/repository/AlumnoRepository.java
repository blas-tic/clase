package es.trapasoft.clase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.trapasoft.clase.model.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByEmail(String email);
    
}
