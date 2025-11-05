package es.trapasoft.clase.model;

public enum DiaSemana {
    LUNES(1, "Lunes"),
    MARTES(2, "Martes"),
    MIERCOLES(3, "Miércoles"),
    JUEVES(4, "Jueves"),
    VIERNES(5, "Viernes"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    private final int codigo;
    private final String nombre;

    DiaSemana(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public static DiaSemana fromCodigo(int codigo) {
        for (DiaSemana dia : values()) {
            if (dia.codigo == codigo) return dia;
        }
        return null;
    }
}