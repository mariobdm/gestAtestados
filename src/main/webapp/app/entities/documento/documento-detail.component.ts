import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumento } from 'app/shared/model/documento.model';

@Component({
  selector: 'jhi-documento-detail',
  templateUrl: './documento-detail.component.html'
})
export class DocumentoDetailComponent implements OnInit {
  documento: IDocumento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.documento = documento;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
