import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAtestado } from 'app/shared/model/atestado.model';
import { AccountService } from 'app/core/auth/account.service';
import { AtestadoService } from './atestado.service';

@Component({
  selector: 'jhi-atestado',
  templateUrl: './atestado.component.html'
})
export class AtestadoComponent implements OnInit, OnDestroy {
  atestados: IAtestado[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected atestadoService: AtestadoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.atestadoService
      .query()
      .pipe(
        filter((res: HttpResponse<IAtestado[]>) => res.ok),
        map((res: HttpResponse<IAtestado[]>) => res.body)
      )
      .subscribe(
        (res: IAtestado[]) => {
          this.atestados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAtestados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAtestado) {
    return item.id;
  }

  registerChangeInAtestados() {
    this.eventSubscriber = this.eventManager.subscribe('atestadoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
