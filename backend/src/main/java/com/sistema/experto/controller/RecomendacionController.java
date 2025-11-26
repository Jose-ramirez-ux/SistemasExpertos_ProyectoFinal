package com.sistema.experto.controller;

import com.sistema.experto.dto.EntradaEstudio;
import com.sistema.experto.dto.PlanEstudio;
import com.sistema.experto.service.MotorReglasService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "http://localhost:4200") // para Angular luego
public class RecomendacionController {

    private final MotorReglasService motorReglasService;

    public RecomendacionController(MotorReglasService motorReglasService) {
        this.motorReglasService = motorReglasService;
    }

    @PostMapping
    public PlanEstudio recomendar(@RequestBody EntradaEstudio entrada) {
        return motorReglasService.generarPlan(entrada);
    }
}
