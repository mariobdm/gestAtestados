import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAtestado, Atestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from './atestado.service';
import { IDestinatario } from 'app/shared/model/destinatario.model';
import { DestinatarioService } from 'app/entities/destinatario/destinatario.service';
import { IRemitente } from 'app/shared/model/remitente.model';
import { RemitenteService } from 'app/entities/remitente/remitente.service';

@Component({
  selector: 'jhi-atestado-update',
  templateUrl: './atestado-update.component.html'
})
export class AtestadoUpdateComponent implements OnInit {
  isSaving: boolean;

  destinatarios: IDestinatario[];

  remitentes: IRemitente[];
  fechaAtestadoDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    tipojuicio: [],
    fechaAtestado: [],
    fechaHoraSuceso: [],
    lugar: [],
    accidente: [],
    permiso: [],
    alcoholemia: [],
    bienes: [],
    velocidad: [],
    lesiones: [],
    fallecido: [],
    instructor: [],
    secretario: [],
    destinatario: [],
    remitente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected atestadoService: AtestadoService,
    protected destinatarioService: DestinatarioService,
    protected remitenteService: RemitenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ atestado }) => {
      this.updateForm(atestado);
    });
    this.destinatarioService
      .query({ filter: 'atestado-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IDestinatario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDestinatario[]>) => response.body)
      )
      .subscribe(
        (res: IDestinatario[]) => {
          if (!this.editForm.get('destinatario').value || !this.editForm.get('destinatario').value.id) {
            this.destinatarios = res;
          } else {
            this.destinatarioService
              .find(this.editForm.get('destinatario').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IDestinatario>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IDestinatario>) => subResponse.body)
              )
              .subscribe(
                (subRes: IDestinatario) => (this.destinatarios = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.remitenteService
      .query({ filter: 'atestado-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRemitente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRemitente[]>) => response.body)
      )
      .subscribe(
        (res: IRemitente[]) => {
          if (!this.editForm.get('remitente').value || !this.editForm.get('remitente').value.id) {
            this.remitentes = res;
          } else {
            this.remitenteService
              .find(this.editForm.get('remitente').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRemitente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRemitente>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRemitente) => (this.remitentes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(atestado: IAtestado) {
    this.editForm.patchValue({
      id: atestado.id,
      numero: atestado.numero,
      tipojuicio: atestado.tipojuicio,
      fechaAtestado: atestado.fechaAtestado,
      fechaHoraSuceso: atestado.fechaHoraSuceso != null ? atestado.fechaHoraSuceso.format(DATE_TIME_FORMAT) : null,
      lugar: atestado.lugar,
      accidente: atestado.accidente,
      permiso: atestado.permiso,
      alcoholemia: atestado.alcoholemia,
      bienes: atestado.bienes,
      velocidad: atestado.velocidad,
      lesiones: atestado.lesiones,
      fallecido: atestado.fallecido,
      instructor: atestado.instructor,
      secretario: atestado.secretario,
      destinatario: atestado.destinatario,
      remitente: atestado.remitente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const atestado = this.createFromForm();
    if (atestado.id !== undefined) {
      this.subscribeToSaveResponse(this.atestadoService.update(atestado));
    } else {
      this.subscribeToSaveResponse(this.atestadoService.create(atestado));
    }
  }

  private createFromForm(): IAtestado {
    return {
      ...new Atestado(),
      id: this.editForm.get(['id']).value,
      numero: this.editForm.get(['numero']).value,
      tipojuicio: this.editForm.get(['tipojuicio']).value,
      fechaAtestado: this.editForm.get(['fechaAtestado']).value,
      fechaHoraSuceso:
        this.editForm.get(['fechaHoraSuceso']).value != null
          ? moment(this.editForm.get(['fechaHoraSuceso']).value, DATE_TIME_FORMAT)
          : undefined,
      lugar: this.editForm.get(['lugar']).value,
      accidente: this.editForm.get(['accidente']).value,
      permiso: this.editForm.get(['permiso']).value,
      alcoholemia: this.editForm.get(['alcoholemia']).value,
      bienes: this.editForm.get(['bienes']).value,
      velocidad: this.editForm.get(['velocidad']).value,
      lesiones: this.editForm.get(['lesiones']).value,
      fallecido: this.editForm.get(['fallecido']).value,
      instructor: this.editForm.get(['instructor']).value,
      secretario: this.editForm.get(['secretario']).value,
      destinatario: this.editForm.get(['destinatario']).value,
      remitente: this.editForm.get(['remitente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtestado>>) {
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

  trackDestinatarioById(index: number, item: IDestinatario) {
    return item.id;
  }

  trackRemitenteById(index: number, item: IRemitente) {
    return item.id;
  }
}
