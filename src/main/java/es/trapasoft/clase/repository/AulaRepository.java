package es.trapasoft.clase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.trapasoft.clase.model.Asignatura;
import es.trapasoft.clase.model.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}
