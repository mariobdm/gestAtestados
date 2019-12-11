import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IDocumento } from 'app/shared/model/documento.model';
import { AccountService } from 'app/core/auth/account.service';
import { DocumentoService } from './documento.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-documento-atestado',
  templateUrl: './documento-atestado.component.html'
})
export class DocumentoAtestadoComponent implements OnInit, OnDestroy {
  documentos: IDocumento[];
  currentAccount: any;
  eventSubscriber: Subscription;
  idAtestado: number;

  constructor(
    protected documentoService: DocumentoService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  /*
  loadAll() {
    this.documentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IDocumento[]>) => res.ok),
        map((res: HttpResponse<IDocumento[]>) => res.body)
      )
      .subscribe(
        (res: IDocumento[]) => {
          this.documentos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
*/
  cargarDocumentos(id: number) {
    this.documentoService.findByAtestado(id).subscribe(
      (res: HttpResponse<IDocumento[]>) => {
        this.documentos = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  /*
  cargarDocsByAtestado() {
    this.idAtestado = this.activatedRoute.params.pipe(map(p => p.id));
    this.documentoService.findByAtestado(this.idAtestado)
    .pipe(
      filter((res: HttpResponse<IDocumento[]>) => res.ok),
      map((res: HttpResponse<IDocumento[]>) => res.body)
    )
    .subscribe(
      (res: IDocumento[]) => {
        this.documentos = res;
      },
      (res:HttpErrorResponse) => this.onError(res.message)
    );
  }
*/

  ngOnInit() {
    // this.loadAll();
    this.eventSubscriber = this.activatedRoute.params.subscribe(params => {
      this.cargarDocumentos(params['id']);
      this.idAtestado = params['id'];
    });
    // this.cargarDocsByAtestado();
    // this.accountService.identity().then(account => {
    //  this.currentAccount = account;
    // });
    // this.registerChangeInDocumentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDocumento) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  /*
  registerChangeInDocumentos() {
    this.eventSubscriber = this.eventManager.subscribe('documentoListModification', response => this.loadAll());
  }
*/
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
