package es.trapasoft.clase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.trapasoft.clase.model.Rol;
import es.trapasoft.clase.repository.UsuarioRepository;
import es.trapasoft.clase.service.UsuarioService;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String telefono,
            @RequestParam Rol rol,
            @RequestParam(required = false) String numeroMatricula,
            @RequestParam(required = false) String numeroEmpleado,
            @RequestParam(required = false) String departamento,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Validaciones adicionales
            if (usuarioRepository.existsById(email)) {
                redirectAttributes.addFlashAttribute("error", "El email ya está registrado");
                return "redirect:/registro";
            }
            
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
                return "redirect:/registro";
            }
            
            if (password.length() < 6) {
                redirectAttributes.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/registro";
            }

            // Crear usuario según el rol
            switch (rol) {
                case ALUMNO:
                    if (numeroMatricula == null || numeroMatricula.trim().isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "El número de matrícula es obligatorio");
                        return "redirect:/registro";
                    }
                    usuarioService.crearAlumno(email, password, nombre, apellidos, telefono, numeroMatricula);
                    break;
                    
                case PROFESOR:
                    if (numeroEmpleado == null || numeroEmpleado.trim().isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "El número de empleado es obligatorio");
                        return "redirect:/registro";
                    }
                    usuarioService.crearProfesor(email, password, nombre, apellidos, telefono, numeroEmpleado, departamento);
                    break;
                    
                default:
                    redirectAttributes.addFlashAttribute("error", "Rol no válido");
                    return "redirect:/registro";
            }
            
            redirectAttributes.addFlashAttribute("success", "Cuenta creada exitosamente");
            return "redirect:/login?registroExitoso";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la cuenta: " + e.getMessage());
            return "redirect:/registro";
        }
    }
}