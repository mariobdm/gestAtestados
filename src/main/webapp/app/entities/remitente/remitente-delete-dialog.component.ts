import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemitente } from 'app/shared/model/remitente.model';
import { RemitenteService } from './remitente.service';

@Component({
  selector: 'jhi-remitente-delete-dialog',
  templateUrl: './remitente-delete-dialog.component.html'
})
export class RemitenteDeleteDialogComponent {
  remitente: IRemitente;

  constructor(protected remitenteService: RemitenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.remitenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'remitenteListModification',
        content: 'Deleted an remitente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-remitente-delete-popup',
  template: ''
})
export class RemitenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remitente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RemitenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.remitente = remitente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/remitente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/remitente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
