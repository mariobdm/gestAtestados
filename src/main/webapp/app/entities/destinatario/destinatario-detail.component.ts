import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDestinatario } from 'app/shared/model/destinatario.model';

@Component({
  selector: 'jhi-destinatario-detail',
  templateUrl: './destinatario-detail.component.html'
})
export class DestinatarioDetailComponent implements OnInit {
  destinatario: IDestinatario;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ destinatario }) => {
      this.destinatario = destinatario;
    });
  }

  previousState() {
    window.history.back();
  }
}
