import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDestinatario } from 'app/shared/model/destinatario.model';
import { AccountService } from 'app/core/auth/account.service';
import { DestinatarioService } from './destinatario.service';

@Component({
  selector: 'jhi-destinatario',
  templateUrl: './destinatario.component.html'
})
export class DestinatarioComponent implements OnInit, OnDestroy {
  destinatarios: IDestinatario[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected destinatarioService: DestinatarioService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.destinatarioService
      .query()
      .pipe(
        filter((res: HttpResponse<IDestinatario[]>) => res.ok),
        map((res: HttpResponse<IDestinatario[]>) => res.body)
      )
      .subscribe(
        (res: IDestinatario[]) => {
          this.destinatarios = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDestinatarios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDestinatario) {
    return item.id;
  }

  registerChangeInDestinatarios() {
    this.eventSubscriber = this.eventManager.subscribe('destinatarioListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
