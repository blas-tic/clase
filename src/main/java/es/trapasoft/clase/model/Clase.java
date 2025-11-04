package es.trapasoft.clase.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clases")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana;
    private String horaDesde;
    private String horaHasta;

    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @ManyToMany(mappedBy = "clases")
    private Set<Alumno> alumnos = new HashSet<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public String getHoraDesde() { return horaDesde; }
    public void setHoraDesde(String horaDesde) { this.horaDesde = horaDesde; }
    public String getHoraHasta() { return horaHasta; }
    public void setHoraHasta(String horaHasta) { this.horaHasta = horaHasta; }
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }
    public Set<Alumno> getAlumnos() { return alumnos; }
    public void setAlumnos(Set<Alumno> alumnos) { this.alumnos = alumnos; }
}