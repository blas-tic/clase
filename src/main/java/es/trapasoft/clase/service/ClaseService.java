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
        return repo.findById(id);
    }

    public void save(Clase clase) {
        repo.save(clase);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

}
