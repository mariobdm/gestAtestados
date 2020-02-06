import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImplicado } from 'app/shared/model/implicado.model';
import { AccountService } from 'app/core/auth/account.service';
import { ImplicadoService } from './implicado.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-implicado-atestado',
  templateUrl: './implicado-atestado.component.html'
})
export class ImplicadoAtestadoComponent implements OnInit, OnDestroy {
  implicados: IImplicado[];
  currentAccount: any;
  eventSubscriber: Subscription;
  idAtestado: number;

  constructor(
    protected implicadoService: ImplicadoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  /*
  loadAll() {
    this.implicadoService
      .query()
      .pipe(
        filter((res: HttpResponse<IImplicado[]>) => res.ok),
        map((res: HttpResponse<IImplicado[]>) => res.body)
      )
      .subscribe(
        (res: IImplicado[]) => {
          this.implicados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
*/
  cargarImplicados(id: number) {
    this.implicadoService.findByAtestado(id).subscribe(
      (res: HttpResponse<IImplicado[]>) => {
        this.implicados = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    // this.loadAll();
    this.eventSubscriber = this.activatedRoute.params.subscribe(params => {
      this.cargarImplicados(params['id']);
      this.idAtestado = params['id'];
    });
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImplicados();
  }
  /*
  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImplicados();
  }
*/

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImplicado) {
    return item.id;
  }

  registerChangeInImplicados() {
    this.eventSubscriber = this.eventManager.subscribe('implicadoListModification', response => this.cargarImplicados(this.idAtestado));
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
