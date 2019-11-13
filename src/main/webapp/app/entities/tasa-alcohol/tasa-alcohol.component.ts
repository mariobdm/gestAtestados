import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { AccountService } from 'app/core/auth/account.service';
import { TasaAlcoholService } from './tasa-alcohol.service';

@Component({
  selector: 'jhi-tasa-alcohol',
  templateUrl: './tasa-alcohol.component.html'
})
export class TasaAlcoholComponent implements OnInit, OnDestroy {
  tasaAlcohols: ITasaAlcohol[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tasaAlcoholService: TasaAlcoholService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tasaAlcoholService
      .query()
      .pipe(
        filter((res: HttpResponse<ITasaAlcohol[]>) => res.ok),
        map((res: HttpResponse<ITasaAlcohol[]>) => res.body)
      )
      .subscribe(
        (res: ITasaAlcohol[]) => {
          this.tasaAlcohols = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTasaAlcohols();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITasaAlcohol) {
    return item.id;
  }

  registerChangeInTasaAlcohols() {
    this.eventSubscriber = this.eventManager.subscribe('tasaAlcoholListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
