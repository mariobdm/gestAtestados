import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemitente } from 'app/shared/model/remitente.model';

@Component({
  selector: 'jhi-remitente-detail',
  templateUrl: './remitente-detail.component.html'
})
export class RemitenteDetailComponent implements OnInit {
  remitente: IRemitente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remitente }) => {
      this.remitente = remitente;
    });
  }

  previousState() {
    window.history.back();
  }
}
