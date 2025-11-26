package com.sistema.experto.service;

import com.sistema.experto.dto.BloqueSesion;
import com.sistema.experto.dto.EntradaEstudio;
import com.sistema.experto.dto.PlanEstudio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MotorReglasService {

    public PlanEstudio generarPlan(EntradaEstudio entrada) {

        List<String> tecnicas = new ArrayList<>();
        List<BloqueSesion> bloques = new ArrayList<>();
        StringBuilder justificacion = new StringBuilder("Reglas activadas:\n");

        int T = entrada.getTiempoMinutos();
        String D = entrada.getDificultad().toUpperCase();
        String S = entrada.getEstilo().toUpperCase();
        String K = entrada.getConocimientoPrevio().toUpperCase();
        String O = entrada.getObjetivo().toUpperCase();
        int E = entrada.getHorasParaExamen();

        // R1: Tiempo muy corto
        if (T < 25) {
            tecnicas.add("Recuperación activa");
            tecnicas.add("Flashcards");
            tecnicas.add("Microresumen de 5 líneas");
            bloques.add(new BloqueSesion(
                    "Bloque único",
                    T,
                    "Usa preguntas o flashcards todo el tiempo y termina con un microresumen."
            ));
            justificacion.append("- R1: T < 25, se prioriza recuperación activa y flashcards.\n");
        }

        // R2: Tema difícil y tiempo suficiente
        if (D.equals("ALTA") && T >= 45) {
            tecnicas.add("Método Feynman");
            tecnicas.add("Ejercicios guiados e independientes");
            justificacion.append("- R2: D = alta y T ≥ 45.\n");

            int comprension = 15;
            int practica = T - 15 - 10;
            int recuperacion = 10;

            bloques.add(new BloqueSesion(
                    "Comprensión",
                    comprension,
                    "Explica el tema con tus palabras y revisa ejemplos resueltos."
            ));
            bloques.add(new BloqueSesion(
                    "Práctica",
                    practica,
                    "Resuelve ejercicios primero con apoyo y luego solo."
            ));
            bloques.add(new BloqueSesion(
                    "Recuperación",
                    recuperacion,
                    "Hazte preguntas tipo examen o flashcards."
            ));
        }

        // Estilo de aprendizaje
        if (S.equals("VISUAL")) {
            tecnicas.add("Mapas conceptuales");
            tecnicas.add("Esquemas y diagramas");
            justificacion.append("- Estilo visual: mapas y esquemas.\n");
        } else if (S.equals("AUDITIVO")) {
            tecnicas.add("Explicarse en voz alta");
            tecnicas.add("Notas de audio");
            justificacion.append("- Estilo auditivo: explicación oral.\n");
        } else if (S.equals("KINESTESICO")) {
            tecnicas.add("Práctica en papel o pizarrón");
            justificacion.append("- Estilo kinestésico: práctica activa.\n");
        } else if (S.equals("MIXTO")) {
            tecnicas.add("Combinar mapas + explicación en voz alta");
            justificacion.append("- Estilo mixto: combinación visual + auditivo.\n");
        }

        // Objetivo
        if (O.equals("MEMORIZAR")) {
            tecnicas.add("Repetición espaciada");
            tecnicas.add("Autoevaluación frecuente");
            justificacion.append("- Objetivo memorizar.\n");
        }
        if (O.equals("RESOLVER_PROBLEMAS")) {
            tecnicas.add("Práctica intercalada de ejercicios");
            justificacion.append("- Objetivo resolver problemas.\n");
        }

        // Conocimiento previo bajo
        if (K.equals("BAJO") && (D.equals("MEDIA") || D.equals("ALTA"))) {
            tecnicas.add("Video o lectura introductoria");
            tecnicas.add("Analogías sencillas");
            justificacion.append("- Conocimiento bajo con dificultad media/alta.\n");
        }

        // Examen cercano
        if (E > 0 && E <= 48) {
            tecnicas.add("Simulacros cronometrados");
            tecnicas.add("Hoja de fórmulas / resumen compacto");
            justificacion.append("- Examen en menos de 48 horas.\n");
        }

        // Si no se generaron bloques y el tiempo es razonable, estructura estándar
        if (bloques.isEmpty() && T >= 30) {
            int comprension = T / 3;
            int practica = T / 2;
            int recuperacion = T - comprension - practica;

            bloques.add(new BloqueSesion(
                    "Comprensión",
                    comprension,
                    "Revisa teoría, ejemplos y haz un mapa o esquema."
            ));
            bloques.add(new BloqueSesion(
                    "Práctica",
                    practica,
                    "Resuelve ejercicios o aplica lo aprendido."
            ));
            bloques.add(new BloqueSesion(
                    "Recuperación",
                    recuperacion,
                    "Preguntas tipo examen, flashcards o explicación en voz alta."
            ));

            justificacion.append("- Estructura estándar por tiempo disponible.\n");
        }

        return new PlanEstudio(tecnicas, bloques, justificacion.toString());
    }
}
