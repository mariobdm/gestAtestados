import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDestinatario, Destinatario } from 'app/shared/model/destinatario.model';
import { DestinatarioService } from './destinatario.service';
import { IAtestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from 'app/entities/atestado/atestado.service';

@Component({
  selector: 'jhi-destinatario-update',
  templateUrl: './destinatario-update.component.html'
})
export class DestinatarioUpdateComponent implements OnInit {
  isSaving: boolean;

  atestados: IAtestado[];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    nombreDestinatario: [],
    descDestinatario: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected destinatarioService: DestinatarioService,
    protected atestadoService: AtestadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ destinatario }) => {
      this.updateForm(destinatario);
    });
    this.atestadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtestado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtestado[]>) => response.body)
      )
      .subscribe((res: IAtestado[]) => (this.atestados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(destinatario: IDestinatario) {
    this.editForm.patchValue({
      id: destinatario.id,
      tipo: destinatario.tipo,
      nombreDestinatario: destinatario.nombreDestinatario,
      descDestinatario: destinatario.descDestinatario
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const destinatario = this.createFromForm();
    if (destinatario.id !== undefined) {
      this.subscribeToSaveResponse(this.destinatarioService.update(destinatario));
    } else {
      this.subscribeToSaveResponse(this.destinatarioService.create(destinatario));
    }
  }

  private createFromForm(): IDestinatario {
    return {
      ...new Destinatario(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value,
      nombreDestinatario: this.editForm.get(['nombreDestinatario']).value,
      descDestinatario: this.editForm.get(['descDestinatario']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDestinatario>>) {
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
