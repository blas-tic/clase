package es.trapasoft.clase.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends Persona {
    
    private String nivelAcceso;
    private LocalDate fechaContratacion;
    
    // Constructores, getters y setters
    public Administrador() {}

    public Administrador(String nivelAcceso, LocalDate fechaContratacion) {
        this.nivelAcceso = nivelAcceso;
        this.fechaContratacion = fechaContratacion;
    }

    // ... getters y setters específicos
    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    
    
    
}
