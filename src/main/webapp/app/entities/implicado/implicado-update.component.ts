import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IImplicado, Implicado } from 'app/shared/model/implicado.model';
import { ImplicadoService } from './implicado.service';
import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { TasaAlcoholService } from 'app/entities/tasa-alcohol/tasa-alcohol.service';
import { IAtestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from 'app/entities/atestado/atestado.service';

@Component({
  selector: 'jhi-implicado-update',
  templateUrl: './implicado-update.component.html'
})
export class ImplicadoUpdateComponent implements OnInit {
  isSaving: boolean;

  tasaalcohols: ITasaAlcohol[];

  atestados: IAtestado[];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    tipoDocumento: [],
    documento: [],
    nombre: [],
    apellido1: [],
    apellido2: [],
    fechaNacimiento: [],
    telefono: [],
    calidad: [],
    direccion: [],
    municipio: [],
    codigopostal: [],
    tasaAlcohol: [],
    atestado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected implicadoService: ImplicadoService,
    protected tasaAlcoholService: TasaAlcoholService,
    protected atestadoService: AtestadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ implicado }) => {
      this.updateForm(implicado);
    });
    this.tasaAlcoholService
      .query({ filter: 'implicado-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITasaAlcohol[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITasaAlcohol[]>) => response.body)
      )
      .subscribe(
        (res: ITasaAlcohol[]) => {
          if (!this.editForm.get('tasaAlcohol').value || !this.editForm.get('tasaAlcohol').value.id) {
            this.tasaalcohols = res;
          } else {
            this.tasaAlcoholService
              .find(this.editForm.get('tasaAlcohol').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITasaAlcohol>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITasaAlcohol>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITasaAlcohol) => (this.tasaalcohols = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.atestadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtestado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtestado[]>) => response.body)
      )
      .subscribe((res: IAtestado[]) => (this.atestados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(implicado: IImplicado) {
    this.editForm.patchValue({
      id: implicado.id,
      tipoDocumento: implicado.tipoDocumento,
      documento: implicado.documento,
      nombre: implicado.nombre,
      apellido1: implicado.apellido1,
      apellido2: implicado.apellido2,
      fechaNacimiento: implicado.fechaNacimiento,
      telefono: implicado.telefono,
      calidad: implicado.calidad,
      direccion: implicado.direccion,
      municipio: implicado.municipio,
      codigopostal: implicado.codigopostal,
      tasaAlcohol: implicado.tasaAlcohol,
      atestado: implicado.atestado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const implicado = this.createFromForm();
    if (implicado.id !== undefined) {
      this.subscribeToSaveResponse(this.implicadoService.update(implicado));
    } else {
      this.subscribeToSaveResponse(this.implicadoService.create(implicado));
    }
  }

  private createFromForm(): IImplicado {
    return {
      ...new Implicado(),
      id: this.editForm.get(['id']).value,
      tipoDocumento: this.editForm.get(['tipoDocumento']).value,
      documento: this.editForm.get(['documento']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido1: this.editForm.get(['apellido1']).value,
      apellido2: this.editForm.get(['apellido2']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      telefono: this.editForm.get(['telefono']).value,
      calidad: this.editForm.get(['calidad']).value,
      direccion: this.editForm.get(['direccion']).value,
      municipio: this.editForm.get(['municipio']).value,
      codigopostal: this.editForm.get(['codigopostal']).value,
      tasaAlcohol: this.editForm.get(['tasaAlcohol']).value,
      atestado: this.editForm.get(['atestado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImplicado>>) {
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

  trackTasaAlcoholById(index: number, item: ITasaAlcohol) {
    return item.id;
  }

  trackAtestadoById(index: number, item: IAtestado) {
    return item.id;
  }
}
