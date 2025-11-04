package es.trapasoft.clase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.trapasoft.clase.model.Clase;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    
}
