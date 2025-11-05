package es.trapasoft.clase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.trapasoft.clase.model.Alumno;
import es.trapasoft.clase.repository.AsignaturaRepository;
import es.trapasoft.clase.service.AlumnoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AsignaturaRepository asigRepo;

    @GetMapping
    public String listarAlumnos(@RequestParam(required = false) String search, Model model) {
        List<Alumno> alumnos = (search == null || search.isBlank())
                ? alumnoService.findAll()
                : alumnoService.findByNombre(search);
        model.addAttribute("alumnos", alumnos);
        return "alumnos/lista";
    }

    @PostMapping("/guardar")
    public String guardarAlumno(@Valid @ModelAttribute Alumno alumno, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "alumnos/formulario";
        }
        // Validar email único
        if (alumno.getId() == null || !alumnoService.findById(alumno.getId())
                .map(a -> a.getEmail().equals(alumno.getEmail())).orElse(false)) {
            if (alumnoService.existsByEmail(alumno.getEmail())) {
                result.rejectValue("email", "error.alumno", "El email ya está en uso");
                return "alumnos/formulario";
            }
        }
        alumnoService.save(alumno);
        return "redirect:/alumnos";
    }

    @GetMapping("/editar/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Optional<Alumno> alumno = alumnoService.findById(id);
        if (alumno.isPresent()) {
            model.addAttribute("alumno", alumno.get());
            model.addAttribute("asignaturas", asigRepo.findAll());
            return "alumnos/formulario";
        }
        return "redirect:/alumnos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("alumno", new Alumno());
        model.addAttribute("asignaturas", asigRepo.findAll());
        return "alumnos/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarAlumno(@PathVariable Long id) {
        alumnoService.deleteById(id);
        return "redirect:/alumnos";
    }
}
