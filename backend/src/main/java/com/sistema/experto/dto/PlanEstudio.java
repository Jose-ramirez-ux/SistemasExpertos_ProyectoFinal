package com.sistema.experto.dto;

import java.util.List;

public class PlanEstudio {

    private List<String> tecnicasRecomendadas;
    private List<BloqueSesion> bloques;
    private String justificacion;

    public PlanEstudio() {}

    public PlanEstudio(List<String> tecnicasRecomendadas,
                       List<BloqueSesion> bloques,
                       String justificacion) {
        this.tecnicasRecomendadas = tecnicasRecomendadas;
        this.bloques = bloques;
        this.justificacion = justificacion;
    }

    public List<String> getTecnicasRecomendadas() { return tecnicasRecomendadas; }
    public void setTecnicasRecomendadas(List<String> tecnicasRecomendadas) { this.tecnicasRecomendadas = tecnicasRecomendadas; }

    public List<BloqueSesion> getBloques() { return bloques; }
    public void setBloques(List<BloqueSesion> bloques) { this.bloques = bloques; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
}
