import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

@Component({
  selector: 'jhi-tasa-alcohol-detail',
  templateUrl: './tasa-alcohol-detail.component.html'
})
export class TasaAlcoholDetailComponent implements OnInit {
  tasaAlcohol: ITasaAlcohol;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tasaAlcohol }) => {
      this.tasaAlcohol = tasaAlcohol;
    });
  }

  previousState() {
    window.history.back();
  }
}
