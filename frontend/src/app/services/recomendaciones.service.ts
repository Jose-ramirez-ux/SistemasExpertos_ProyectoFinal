import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EntradaEstudio {
  estilo: string;
  dificultad: string;
  tiempoMinutos: number;
  conocimientoPrevio: string;
  horasParaExamen: number;
  objetivo: string;
}

export interface BloqueSesion {
  nombre: string;
  duracionMinutos: number;
  descripcion: string;
}

export interface PlanEstudio {
  tecnicasRecomendadas: string[];
  bloques: BloqueSesion[];
  justificacion: string;
}

@Injectable({ providedIn: 'root' })
export class RecomendacionesService {

  private apiUrl = 'http://localhost:8080/api/recomendaciones';

  constructor(private http: HttpClient) {}

  recomendar(entrada: EntradaEstudio): Observable<PlanEstudio> {
    return this.http.post<PlanEstudio>(this.apiUrl, entrada);
  }
}
