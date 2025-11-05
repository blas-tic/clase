package es.trapasoft.clase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.trapasoft.clase.model.Clase;
import es.trapasoft.clase.repository.ClaseRepository;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository repo;

    public List<Clase> getAll() {
        return repo.findAll();
    }

    public Optional<Clase> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return repo.findById(id);
    }

    public void save(Clase clase) {
        if (clase == null) {
            throw new IllegalArgumentException("La clase no puede ser nula");
        }
        repo.save(clase);
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        repo.deleteById(id);
    }

}
