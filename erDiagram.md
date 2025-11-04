```mermaid
erDiagram
    ALUMNO {
        Long id PK
        String nombre
        String email
        Integer edad
        LocalDateTime fechaRegistro
    }
    CLASE {
        Long id PK
        String diaSemana
        String horaDesde
        String horaHasta
        Long idAsignatura
        Long idAula
    }
    ASIGNATURA {
        Long id PK
        String nombre
    }
    AULA {
        Long id PK
        String nombre
    }

    ALUMNO ||--o{ ALUMNO_CLASE : "tiene"
    CLASE  ||--o{ ALUMNO_CLASE : "incluye"
    CLASE  }o--|| ASIGNATURA : "es de"
    CLASE  }o--|| AULA : "se imparte en"
```