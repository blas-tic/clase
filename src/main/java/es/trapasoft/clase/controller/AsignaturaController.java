package es.trapasoft.clase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import es.trapasoft.clase.model.Asignatura;
import es.trapasoft.clase.repository.AsignaturaRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.lang.NonNull;

@Controller
@RequestMapping("/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaRepository repo;

    @GetMapping
    public String listarAsignaturas(Model model) {
        List<Asignatura> asignaturas = repo.findAll();
        model.addAttribute("asignaturas", asignaturas);
        return "asignaturas/lista";
    }

    @PostMapping("/guardar")
    public String guardarAsignatura(@Valid @ModelAttribute Asignatura asignatura, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "asignaturas/formulario";
        }

        if (asignatura != null) {
            repo.save(asignatura);
        }
        return "redirect:/asignaturas";
    }

    @GetMapping("/editar/{id}")
    public String editarAsignatura(@PathVariable @NonNull Long id, Model model) {
        Optional<Asignatura> asignatura = repo.findById(id);
        if (asignatura.isPresent()) {
            model.addAttribute("asignatura", asignatura.get());
            return "asignaturas/formulario";
        }

        return "redirect:/asignaturas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("asignatura", new Asignatura());

        return "asignaturas/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable @NonNull Long id) {
        repo.deleteById(id);
        return "redirect:/asignaturas";
    }
}
