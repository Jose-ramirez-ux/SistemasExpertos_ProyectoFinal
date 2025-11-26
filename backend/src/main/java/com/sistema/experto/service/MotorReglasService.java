package com.sistema.experto.service;

import com.sistema.experto.dto.BloqueSesion;
import com.sistema.experto.dto.EntradaEstudio;
import com.sistema.experto.dto.PlanEstudio;
import com.sistema.experto.entity.ConsultaEstudio;
import com.sistema.experto.repository.ConsultaEstudioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MotorReglasService {

    private final ConsultaEstudioRepository consultaRepo;

    public MotorReglasService(ConsultaEstudioRepository consultaRepo) {
        this.consultaRepo = consultaRepo;
    }

    
    public PlanEstudio generarPlan(EntradaEstudio entrada) {

        List<String> tecnicas = new ArrayList<>();
        List<BloqueSesion> bloques = new ArrayList<>();
        StringBuilder justificacion = new StringBuilder("Justificación del plan generado:\n");

        int T = entrada.getTiempoMinutos();      // Tiempo disponible
        String D = entrada.getDificultad().toUpperCase(); // Dificultad percibida
        String S = entrada.getEstilo().toUpperCase();     // Estilo de aprendizaje
        String K = entrada.getConocimientoPrevio().toUpperCase(); // Conocimiento previo
        String O = entrada.getObjetivo().toUpperCase();   // Objetivo
        int E = entrada.getHorasParaExamen();   // Horas para el examen

        // =====================================================
        // CONFLICTO 1: Tiempo de 20–25 minutos + examen ≤ 48 h
        // =====================================================
        if (T >= 20 && T <= 25 && E <= 48) {

            tecnicas.add("Recuperación activa");
            tecnicas.add("Flashcards");
            tecnicas.add("1–2 preguntas cronometradas tipo examen");
            tecnicas.add("Microresumen de 5 líneas");
            tecnicas.add("Mini hoja de fórmulas");

            bloques.add(new BloqueSesion(
                    "Bloque único (tiempo muy limitado)",
                    T,
                    "Debido a que el estudiante dispone únicamente de 20 a 25 minutos y el examen se encuentra muy próximo, " +
                    "se recomienda una combinación de técnicas rápidas: preguntas tipo examen cronometradas, flashcards, " +
                    "recuperación activa y finalización con microresumen."
            ));

            justificacion.append("- Se detectó muy poco tiempo disponible (20-25 minutos) y un examen cercano (menos de 48 horas). " +
                    "Se aplicó una estrategia de emergencia con técnicas rápidas y alto impacto.\n");

            PlanEstudio plan = new PlanEstudio(tecnicas, bloques, justificacion.toString());
            guardarConsulta(entrada, plan);
            return plan;
        }

        // =====================================================
        // R1: TIEMPO MUY CORTO (< 25 minutos)
        // =====================================================
        if (T < 25) {

            tecnicas.add("Recuperación activa");
            tecnicas.add("Flashcards");
            tecnicas.add("Microresumen de 5 líneas");

            bloques.add(new BloqueSesion(
                    "Bloque único",
                    T,
                    "El tiempo disponible es menor a 25 minutos, considerado 'tiempo muy corto'. Por ello, " +
                    "se recomienda un único bloque intenso en recuperación activa y flashcards."
            ));

            justificacion.append("- El estudiante dispone de menos de 25 minutos (tiempo muy corto). " +
                    "Se recomienda un enfoque directo y rápido: flashcards y recuperación activa.\n");
        }

        // =====================================================
        // R2 / CONFLICTO 2: Tema difícil
        // =====================================================
        if (D.equals("ALTA")) {

            // Caso 1: T ≥ 45 → Sesión completa
            if (T >= 45) {

                tecnicas.add("Método Feynman");
                tecnicas.add("Ejercicios guiados e independientes");

                int comprension = 15;
                int practica = T - 15 - 10;
                int recuperacion = 10;

                bloques.add(new BloqueSesion(
                        "Comprensión profunda",
                        comprension,
                        "Se utiliza el método Feynman para explicar el tema en voz alta y reforzar conceptos complejos."
                ));

                bloques.add(new BloqueSesion(
                        "Ejercicios guiados e independientes",
                        practica,
                        "Se combinan ejercicios guiados con práctica autónoma para consolidar el aprendizaje."
                ));

                bloques.add(new BloqueSesion(
                        "Refuerzo final",
                        recuperacion,
                        "Preguntas de recuperación activa para evaluar el dominio del contenido."
                ));

                justificacion.append("- El estudiante reporta alta dificultad y dispone de 45 minutos o más. " +
                        "Se propone una sesión completa con Feynman, ejemplos resueltos y práctica.\n");
            }

            // Caso 2: 30 ≤ T < 45 → Versión compacta (Conflicto 2)
            else if (T >= 30) {

                int comprension = 10;
                int practica = T - comprension;

                tecnicas.add("Mini Feynman");
                tecnicas.add("Ejercicios representativos");

                bloques.add(new BloqueSesion(
                        "Comprensión rápida",
                        comprension,
                        "Versión abreviada del método Feynman para explicar solo los conceptos esenciales."
                ));
                bloques.add(new BloqueSesion(
                        "Práctica focalizada",
                        practica,
                        "Ejercicios clave representativos del tipo de problemas más comunes."
                ));

                justificacion.append("- El estudiante reporta alta dificultad, pero tiene entre 30 y 45 minutos. " +
                        "Se aplica una versión reducida de la sesión, enfocada en comprensión rápida y práctica focalizada.\n");
            }
        }

        // =====================================================
        // R3: Estilo VISUAL
        // =====================================================
        if (S.equals("VISUAL") && T >= 30) {

            tecnicas.add("Mapas conceptuales");
            tecnicas.add("Diagramas");
            tecnicas.add("Resúmenes esquemáticos");

            justificacion.append("- La preferencia de aprendizaje declarada es visual y hay suficiente tiempo (≥ 30 minutos). " +
                    "Se recomiendan mapas conceptuales, diagramas y resúmenes esquemáticos.\n");

            if (D.equals("MEDIA") || D.equals("ALTA")) {
                tecnicas.add("Video explicativo corto");
                justificacion.append("  · Debido a la dificultad media o alta, se añade un video corto para reforzar conceptos.\n");
            }
        }

        // =====================================================
        // R4: Estilo AUDITIVO
        // =====================================================
        if (S.equals("AUDITIVO")) {

            tecnicas.add("Explicación oral");
            tecnicas.add("Notas de audio");

            justificacion.append("- El estudiante prefiere aprendizaje auditivo, por lo que se recomiendan explicarse en voz alta " +
                    "y grabar notas de audio.\n");

            if (T >= 40) {
                tecnicas.add("Discusión guiada");
                justificacion.append("  · Con tiempo suficiente (≥ 40 minutos), se sugiere complementar con discusión guiada o tutoría.\n");
            }
        }

        // =====================================================
        // R5: Estilo KINESTÉSICO
        // =====================================================
        if (S.equals("KINESTESICO") || S.equals("KINESTÉSICO")) {

            tecnicas.add("Práctica en papel o pizarrón");
            tecnicas.add("Resolución paso a paso");

            justificacion.append("- El estudiante reporta preferencia kinestésica. Se recomiendan ejercicios escritos y práctica activa.\n");

            if (D.equals("ALTA")) {
                tecnicas.add("Feynman aplicado a ejercicios");
                justificacion.append("  · Debido a la dificultad alta, se integra Feynman mientras se realizan ejercicios.\n");
            }
        }

        // =====================================================
        // R6: Objetivo MEMORIZAR
        // =====================================================
        if (O.equals("MEMORIZAR")) {
            tecnicas.add("Repetición espaciada");
            tecnicas.add("Pruebas de recuperación");

            justificacion.append("- El objetivo es memorizar información, por lo que se prioriza la repetición espaciada " +
                    "y las pruebas de recuperación.\n");
        }

        // =====================================================
        // R7: Objetivo RESOLVER_PROBLEMAS
        // =====================================================
        if (O.equals("RESOLVER_PROBLEMAS")) {
            tecnicas.add("Ejemplos resueltos");
            tecnicas.add("Práctica intercalada");

            justificacion.append("- El objetivo es resolver problemas; se recomienda trabajar con ejemplos resueltos " +
                    "y aplicar práctica intercalada.\n");
        }

        // =====================================================
        // R8: Bajo conocimiento previo
        // =====================================================
        if (K.equals("BAJO") && (D.equals("MEDIA") || D.equals("ALTA"))) {
            tecnicas.add("Video introductorio");
            tecnicas.add("Analogías sencillas");

            justificacion.append("- El estudiante indica bajo conocimiento previo en un tema de dificultad media o alta. " +
                    "Se sugiere comenzar con un video introductorio y analogías.\n");
        }

        // =====================================================
        // R9: Examen cercano (≤ 48 horas)
        // =====================================================
        if (E > 0 && E <= 48) {
            tecnicas.add("Práctica cronometrada");
            tecnicas.add("Hoja de fórmulas compacta");
            tecnicas.add("Minisesiones espaciadas");

            justificacion.append("- Se detecta una evaluación cercana (menos de 48 horas). " +
                    "Se recomienda práctica cronometrada y hoja de fórmulas.\n");
        }

        // =====================================================
        // R10: Sesión completa (45–90 minutos)
        // =====================================================
        if (bloques.isEmpty() && T >= 45 && T <= 90) {

            int comprension = T / 3;
            int practica = T / 2;
            int recuperacion = T - comprension - practica;

            bloques.add(new BloqueSesion(
                    "Comprensión",
                    comprension,
                    "Lectura activa o video para entender los conceptos clave."
            ));

            bloques.add(new BloqueSesion(
                    "Práctica",
                    practica,
                    "Ejercicios guiados y práctica autónoma."
            ));

            bloques.add(new BloqueSesion(
                    "Recuperación",
                    recuperacion,
                    "Preguntas tipo examen o explicación en voz alta."
            ));

            justificacion.append("- El tiempo disponible permite una sesión completa (entre 45 y 90 minutos). " +
                    "Se divide en comprensión, práctica y recuperación.\n");
        }


        // =====================================================
        // REGRA GENÉRICA
        // =====================================================
        if (bloques.isEmpty() && T >= 30) {
            int comprension = T / 3;
            int practica = T / 2;
            int recuperacion = T - comprension - practica;

            bloques.add(new BloqueSesion(
                    "Comprensión",
                    comprension,
                    "Revisión general del tema mediante lectura activa."
            ));
            bloques.add(new BloqueSesion(
                    "Práctica",
                    practica,
                    "Aplicación del contenido mediante ejercicios."
            ));
            bloques.add(new BloqueSesion(
                    "Recuperación",
                    recuperacion,
                    "Autoevaluación mediante preguntas o explicación oral."
            ));

            justificacion.append("- Se genera una estructura estándar basada en el tiempo disponible (30 minutos o más).\n");
        }

        // =====================================================
        // CREAR PLAN Y GUARDAR
        // =====================================================
        PlanEstudio plan = new PlanEstudio(tecnicas, bloques, justificacion.toString());
        guardarConsulta(entrada, plan);
        return plan;
    }

    private void guardarConsulta(EntradaEstudio entrada, PlanEstudio plan) {
        ConsultaEstudio consulta = new ConsultaEstudio(
                entrada.getEstilo(),
                entrada.getDificultad(),
                entrada.getTiempoMinutos(),
                entrada.getConocimientoPrevio(),
                entrada.getHorasParaExamen(),
                entrada.getObjetivo(),
                plan.getJustificacion(),
                LocalDateTime.now()
        );
        consultaRepo.save(consulta);
    }
}
