import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAtestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from './atestado.service';

@Component({
  selector: 'jhi-atestado-delete-dialog',
  templateUrl: './atestado-delete-dialog.component.html'
})
export class AtestadoDeleteDialogComponent {
  atestado: IAtestado;

  constructor(protected atestadoService: AtestadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.atestadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'atestadoListModification',
        content: 'Deleted an atestado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-atestado-delete-popup',
  template: ''
})
export class AtestadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ atestado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AtestadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.atestado = atestado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/atestado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/atestado', { outlets: { popup: null } }]);
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
