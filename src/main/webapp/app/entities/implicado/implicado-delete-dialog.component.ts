import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImplicado } from 'app/shared/model/implicado.model';
import { ImplicadoService } from './implicado.service';

@Component({
  selector: 'jhi-implicado-delete-dialog',
  templateUrl: './implicado-delete-dialog.component.html'
})
export class ImplicadoDeleteDialogComponent {
  implicado: IImplicado;

  constructor(protected implicadoService: ImplicadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.implicadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'implicadoListModification',
        content: 'Deleted an implicado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-implicado-delete-popup',
  template: ''
})
export class ImplicadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ implicado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImplicadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.implicado = implicado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/implicado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/implicado', { outlets: { popup: null } }]);
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
