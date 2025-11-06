package es.trapasoft.clase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.trapasoft.clase.model.Alumno;
import es.trapasoft.clase.model.Rol;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByUsuarioEmail(String email);

    List<Alumno> findByUsuarioRolesContaining(Rol rol);

    @Query("SELECT a FROM Alumno a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :cadena, '%')) OR LOWER(a.apellidos) LIKE LOWER(CONCAT('%', :cadena, '%'))")
    List<Alumno> buscarPorNombreOApellidos(@Param("cadena") String cadena);

    boolean existsByUsuarioEmail(String email);

}
