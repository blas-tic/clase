package es.trapasoft.clase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.trapasoft.clase.model.Profesor;
import es.trapasoft.clase.model.Rol;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByUsuarioEmail(String email);
    boolean existsByUsuarioEmail(String email);
    List<Profesor> findByUsuarioRolesContaining(Rol rol);
}
