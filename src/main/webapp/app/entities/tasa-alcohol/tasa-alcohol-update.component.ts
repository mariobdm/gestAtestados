import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITasaAlcohol, TasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { TasaAlcoholService } from './tasa-alcohol.service';
import { IImplicado } from 'app/shared/model/implicado.model';
import { ImplicadoService } from 'app/entities/implicado/implicado.service';

@Component({
  selector: 'jhi-tasa-alcohol-update',
  templateUrl: './tasa-alcohol-update.component.html'
})
export class TasaAlcoholUpdateComponent implements OnInit {
  isSaving: boolean;

  implicados: IImplicado[];

  editForm = this.fb.group({
    id: [],
    tasaNoEvidencial: [],
    tasaEvidencial1: [],
    tasaEvidencial2: [],
    tasaEnSangre: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tasaAlcoholService: TasaAlcoholService,
    protected implicadoService: ImplicadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tasaAlcohol }) => {
      this.updateForm(tasaAlcohol);
    });
    this.implicadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImplicado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImplicado[]>) => response.body)
      )
      .subscribe((res: IImplicado[]) => (this.implicados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tasaAlcohol: ITasaAlcohol) {
    this.editForm.patchValue({
      id: tasaAlcohol.id,
      tasaNoEvidencial: tasaAlcohol.tasaNoEvidencial,
      tasaEvidencial1: tasaAlcohol.tasaEvidencial1,
      tasaEvidencial2: tasaAlcohol.tasaEvidencial2,
      tasaEnSangre: tasaAlcohol.tasaEnSangre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tasaAlcohol = this.createFromForm();
    if (tasaAlcohol.id !== undefined) {
      this.subscribeToSaveResponse(this.tasaAlcoholService.update(tasaAlcohol));
    } else {
      this.subscribeToSaveResponse(this.tasaAlcoholService.create(tasaAlcohol));
    }
  }

  private createFromForm(): ITasaAlcohol {
    return {
      ...new TasaAlcohol(),
      id: this.editForm.get(['id']).value,
      tasaNoEvidencial: this.editForm.get(['tasaNoEvidencial']).value,
      tasaEvidencial1: this.editForm.get(['tasaEvidencial1']).value,
      tasaEvidencial2: this.editForm.get(['tasaEvidencial2']).value,
      tasaEnSangre: this.editForm.get(['tasaEnSangre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITasaAlcohol>>) {
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

  trackImplicadoById(index: number, item: IImplicado) {
    return item.id;
  }
}
