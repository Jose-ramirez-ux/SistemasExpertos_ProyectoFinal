package com.sistema.experto.controller;

import com.sistema.experto.dto.EntradaEstudio;
import com.sistema.experto.dto.PlanEstudio;
import com.sistema.experto.entity.ConsultaEstudio;
import com.sistema.experto.repository.ConsultaEstudioRepository;
import com.sistema.experto.service.MotorReglasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class RecomendacionController {

    private final MotorReglasService motorReglasService;
    private final ConsultaEstudioRepository consultaRepo;

    public RecomendacionController(MotorReglasService motorReglasService,
                                   ConsultaEstudioRepository consultaRepo) {
        this.motorReglasService = motorReglasService;
        this.consultaRepo = consultaRepo;
    }

    @PostMapping
    public PlanEstudio recomendar(@RequestBody EntradaEstudio entrada) {
        return motorReglasService.generarPlan(entrada);
    }

    @GetMapping("/historial")
    public List<ConsultaEstudio> historial() {
        return consultaRepo.findAll();
    }
}
