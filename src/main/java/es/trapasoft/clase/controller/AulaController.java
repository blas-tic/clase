package es.trapasoft.clase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import es.trapasoft.clase.model.Aula;
import es.trapasoft.clase.repository.AulaRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.lang.NonNull;

@Controller
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaRepository repo;

    @GetMapping
    public String listarAulas(Model model) {
        List<Aula> aulas = repo.findAll();
        model.addAttribute("aulas", aulas);
        return "aulas/lista";
    }

    @PostMapping("/guardar")
    public String guardarAula(@Valid @ModelAttribute @NonNull Aula aula, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "aulas/formulario";
        }

        repo.save(aula);
        return "redirect:/aulas";
    }

    @GetMapping("/editar/{id}")
    public String editarAula(@PathVariable @NonNull Long id, Model model) {
        Optional<Aula> aula = repo.findById(id);
        if (aula.isPresent()) {
            model.addAttribute("aula", aula.get());
            return "aulas/formulario";
        }

        return "redirect:/aulas";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("aula", new Aula());

        return "aulas/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarAula(@PathVariable @NonNull Long id) {
        repo.deleteById(id);
        return "redirect:/aulas";
    }
}
