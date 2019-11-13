import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImplicado } from 'app/shared/model/implicado.model';

@Component({
  selector: 'jhi-implicado-detail',
  templateUrl: './implicado-detail.component.html'
})
export class ImplicadoDetailComponent implements OnInit {
  implicado: IImplicado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ implicado }) => {
      this.implicado = implicado;
    });
  }

  previousState() {
    window.history.back();
  }
}
