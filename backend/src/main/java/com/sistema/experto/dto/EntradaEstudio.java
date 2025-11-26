package com.sistema.experto.dto;

public class EntradaEstudio {

    // VISUAL, AUDITIVO, KINESTESICO, MIXTO
    private String estilo;

    // BAJA, MEDIA, ALTA
    private String dificultad;

    private int tiempoMinutos;          // T
    private String conocimientoPrevio;  // BAJO, MEDIO, ALTO
    private int horasParaExamen;        // E (0 si no hay)
    private String objetivo;            // MEMORIZAR, RESOLVER_PROBLEMAS, COMPRENDER

    public String getEstilo() { return estilo; }
    public void setEstilo(String estilo) { this.estilo = estilo; }

    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public int getTiempoMinutos() { return tiempoMinutos; }
    public void setTiempoMinutos(int tiempoMinutos) { this.tiempoMinutos = tiempoMinutos; }

    public String getConocimientoPrevio() { return conocimientoPrevio; }
    public void setConocimientoPrevio(String conocimientoPrevio) { this.conocimientoPrevio = conocimientoPrevio; }

    public int getHorasParaExamen() { return horasParaExamen; }
    public void setHorasParaExamen(int horasParaExamen) { this.horasParaExamen = horasParaExamen; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
}
