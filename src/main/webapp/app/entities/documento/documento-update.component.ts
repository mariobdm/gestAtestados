import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDocumento, Documento } from 'app/shared/model/documento.model';
import { DocumentoService } from './documento.service';
import { IAtestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from 'app/entities/atestado/atestado.service';

@Component({
  selector: 'jhi-documento-update',
  templateUrl: './documento-update.component.html'
})
export class DocumentoUpdateComponent implements OnInit {
  isSaving: boolean;

  atestados: IAtestado[];

  editForm = this.fb.group({
    id: [],
    nombreDoc: [],
    descDoc: [],
    archivo: [],
    archivoContentType: [],
    archivoPdf: [],
    archivoPdfContentType: [],
    atestado: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected documentoService: DocumentoService,
    protected atestadoService: AtestadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ documento }) => {
      this.updateForm(documento);
    });
    this.atestadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtestado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtestado[]>) => response.body)
      )
      .subscribe((res: IAtestado[]) => (this.atestados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(documento: IDocumento) {
    this.editForm.patchValue({
      id: documento.id,
      nombreDoc: documento.nombreDoc,
      descDoc: documento.descDoc,
      archivo: documento.archivo,
      archivoContentType: documento.archivoContentType,
      archivoPdf: documento.archivoPdf,
      archivoPdfContentType: documento.archivoPdfContentType,
      atestado: documento.atestado
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const documento = this.createFromForm();
    if (documento.id !== undefined) {
      this.subscribeToSaveResponse(this.documentoService.update(documento));
    } else {
      this.subscribeToSaveResponse(this.documentoService.create(documento));
    }
  }

  private createFromForm(): IDocumento {
    return {
      ...new Documento(),
      id: this.editForm.get(['id']).value,
      nombreDoc: this.editForm.get(['nombreDoc']).value,
      descDoc: this.editForm.get(['descDoc']).value,
      archivoContentType: this.editForm.get(['archivoContentType']).value,
      archivo: this.editForm.get(['archivo']).value,
      archivoPdfContentType: this.editForm.get(['archivoPdfContentType']).value,
      archivoPdf: this.editForm.get(['archivoPdf']).value,
      atestado: this.editForm.get(['atestado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumento>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAtestadoById(index: number, item: IAtestado) {
    return item.id;
  }
}
