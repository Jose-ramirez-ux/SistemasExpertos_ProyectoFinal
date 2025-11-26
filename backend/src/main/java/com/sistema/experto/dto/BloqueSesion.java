package com.sistema.experto.dto;

public class BloqueSesion {

    private String nombre;          // "Comprensión", "Práctica", etc.
    private int duracionMinutos;
    private String descripcion;

    public BloqueSesion() {}

    public BloqueSesion(String nombre, int duracionMinutos, String descripcion) {
        this.nombre = nombre;
        this.duracionMinutos = duracionMinutos;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
