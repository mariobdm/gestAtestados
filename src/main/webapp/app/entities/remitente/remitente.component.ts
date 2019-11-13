import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRemitente } from 'app/shared/model/remitente.model';
import { AccountService } from 'app/core/auth/account.service';
import { RemitenteService } from './remitente.service';

@Component({
  selector: 'jhi-remitente',
  templateUrl: './remitente.component.html'
})
export class RemitenteComponent implements OnInit, OnDestroy {
  remitentes: IRemitente[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected remitenteService: RemitenteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.remitenteService
      .query()
      .pipe(
        filter((res: HttpResponse<IRemitente[]>) => res.ok),
        map((res: HttpResponse<IRemitente[]>) => res.body)
      )
      .subscribe(
        (res: IRemitente[]) => {
          this.remitentes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRemitentes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRemitente) {
    return item.id;
  }

  registerChangeInRemitentes() {
    this.eventSubscriber = this.eventManager.subscribe('remitenteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
