import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRemitente, Remitente } from 'app/shared/model/remitente.model';
import { RemitenteService } from './remitente.service';
import { IAtestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from 'app/entities/atestado/atestado.service';

@Component({
  selector: 'jhi-remitente-update',
  templateUrl: './remitente-update.component.html'
})
export class RemitenteUpdateComponent implements OnInit {
  isSaving: boolean;

  atestados: IAtestado[];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    nombreRemitente: [],
    descRemitente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected remitenteService: RemitenteService,
    protected atestadoService: AtestadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ remitente }) => {
      this.updateForm(remitente);
    });
    this.atestadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtestado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtestado[]>) => response.body)
      )
      .subscribe((res: IAtestado[]) => (this.atestados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(remitente: IRemitente) {
    this.editForm.patchValue({
      id: remitente.id,
      tipo: remitente.tipo,
      nombreRemitente: remitente.nombreRemitente,
      descRemitente: remitente.descRemitente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const remitente = this.createFromForm();
    if (remitente.id !== undefined) {
      this.subscribeToSaveResponse(this.remitenteService.update(remitente));
    } else {
      this.subscribeToSaveResponse(this.remitenteService.create(remitente));
    }
  }

  private createFromForm(): IRemitente {
    return {
      ...new Remitente(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value,
      nombreRemitente: this.editForm.get(['nombreRemitente']).value,
      descRemitente: this.editForm.get(['descRemitente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemitente>>) {
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
