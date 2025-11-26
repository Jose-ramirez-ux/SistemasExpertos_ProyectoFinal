package com.sistema.experto.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas_estudio")
public class ConsultaEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estilo;
    private String dificultad;
    private int tiempoMinutos;
    private String conocimientoPrevio;
    private int horasParaExamen;
    private String objetivo;

    @Column(length = 1000)
    private String resumenJustificacion;

    private LocalDateTime fechaHora;

    public ConsultaEstudio() {}

    public ConsultaEstudio(String estilo, String dificultad, int tiempoMinutos,
                           String conocimientoPrevio, int horasParaExamen,
                           String objetivo, String resumenJustificacion,
                           LocalDateTime fechaHora) {
        this.estilo = estilo;
        this.dificultad = dificultad;
        this.tiempoMinutos = tiempoMinutos;
        this.conocimientoPrevio = conocimientoPrevio;
        this.horasParaExamen = horasParaExamen;
        this.objetivo = objetivo;
        this.resumenJustificacion = resumenJustificacion;
        this.fechaHora = fechaHora;
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }

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

    public String getResumenJustificacion() { return resumenJustificacion; }
    public void setResumenJustificacion(String resumenJustificacion) { this.resumenJustificacion = resumenJustificacion; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
