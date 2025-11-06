package es.trapasoft.clase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.trapasoft.clase.model.Persona;
import es.trapasoft.clase.service.UsuarioService;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService; // Para obtener datos de persona

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<Persona> persona = usuarioService.obtenerPersonaPorEmail(email);
        
        persona.ifPresent(p -> model.addAttribute("persona", p));
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
