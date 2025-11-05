package es.trapasoft.clase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import es.trapasoft.clase.model.Asignatura;
import es.trapasoft.clase.model.Aula;
import es.trapasoft.clase.model.Clase;
import es.trapasoft.clase.model.DiaSemana;
import es.trapasoft.clase.repository.AsignaturaRepository;
import es.trapasoft.clase.repository.AulaRepository;
import es.trapasoft.clase.service.ClaseService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    @Autowired
    private ClaseService service;
    @Autowired
    AsignaturaRepository asigRepo;
    @Autowired
    AulaRepository aulaRepo;

    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = service.getAll();
        model.addAttribute("clases", clases);
        return "clases/lista";
    }

    @PostMapping("/guardar")
    public String guardarClase(@Valid @ModelAttribute Clase Clase, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "clases/formulario";
        }

        service.save(Clase);
        return "redirect:/clases";
    }

    @GetMapping("/editar/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Optional<Clase> clase = service.getById(id);
        if (clase.isPresent()) {
            model.addAttribute("clase", clase.get());
            return "clases/formulario";
        }
        List<Asignatura> asignaturas = asigRepo.findAll();
        List<Aula> aulas = aulaRepo.findAll();
        model.addAttribute("asignaturas", asignaturas);
        model.addAttribute("aulas", aulas);
        model.addAttribute("diasSemana", DiaSemana.values());
        return "redirect:/clases";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        List<Asignatura> asignaturas = asigRepo.findAll();
        List<Aula> aulas = aulaRepo.findAll();
        model.addAttribute("clase", new Clase());
        model.addAttribute("aulas", aulas);
        model.addAttribute("asignaturas", asignaturas);
        model.addAttribute("diasSemana", DiaSemana.values());

        return "clases/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/clases";
    }
}
