package es.trapasoft.clase.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Email(message = "El email no puede estar vacío")
    @Column(name = "email", nullable = false)
    private String email;

    private Integer edad;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Constructor
    public Alumno() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // asignaturas
    @ManyToMany
    @JoinTable(name = "alumno_asignatura", joinColumns = @JoinColumn(name = "alumno_id"), inverseJoinColumns = @JoinColumn(name = "asignatura_id"))
    private Set<Asignatura> asignaturas = new HashSet<>();

    // clases concretas
    @ManyToMany
    @JoinTable(name = "alumno_clase", joinColumns = @JoinColumn(name = "alumno_id"), inverseJoinColumns = @JoinColumn(name = "clase_id"))
    private Set<Clase> clases = new HashSet<>();

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Set<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Set<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

}
