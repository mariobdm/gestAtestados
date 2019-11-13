import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAtestado } from 'app/shared/model/atestado.model';

@Component({
  selector: 'jhi-atestado-detail',
  templateUrl: './atestado-detail.component.html'
})
export class AtestadoDetailComponent implements OnInit {
  atestado: IAtestado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ atestado }) => {
      this.atestado = atestado;
    });
  }

  previousState() {
    window.history.back();
  }
}
