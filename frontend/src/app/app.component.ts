import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';        // 👈 para *ngIf y *ngFor
import { FormsModule } from '@angular/forms';          // 👈 para [(ngModel)]
import { RouterOutlet } from '@angular/router';

import { RecomendacionesService, EntradaEstudio, PlanEstudio } from './services/recomendaciones.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],  // 👈 IMPORTANTE
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  entrada: EntradaEstudio = {
    estilo: 'VISUAL',
    dificultad: 'MEDIA',
    tiempoMinutos: 45,
    conocimientoPrevio: 'MEDIO',
    horasParaExamen: 24,
    objetivo: 'COMPRENDER'
  };

  resultado: PlanEstudio | null = null;   // 👈 mejor null que undefined
  cargando = false;
  error = '';

  constructor(private recService: RecomendacionesService) {}

  enviar() {
    this.cargando = true;
    this.error = '';
    this.recService.recomendar(this.entrada).subscribe({
      next: res => {
        this.resultado = res;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al consultar el sistema experto';
        this.cargando = false;
        console.error(err);
      }
    });
  }
}
