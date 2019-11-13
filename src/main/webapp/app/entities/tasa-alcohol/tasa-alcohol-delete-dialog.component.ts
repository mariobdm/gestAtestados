import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { TasaAlcoholService } from './tasa-alcohol.service';

@Component({
  selector: 'jhi-tasa-alcohol-delete-dialog',
  templateUrl: './tasa-alcohol-delete-dialog.component.html'
})
export class TasaAlcoholDeleteDialogComponent {
  tasaAlcohol: ITasaAlcohol;

  constructor(
    protected tasaAlcoholService: TasaAlcoholService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tasaAlcoholService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tasaAlcoholListModification',
        content: 'Deleted an tasaAlcohol'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tasa-alcohol-delete-popup',
  template: ''
})
export class TasaAlcoholDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tasaAlcohol }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TasaAlcoholDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tasaAlcohol = tasaAlcohol;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tasa-alcohol', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tasa-alcohol', { outlets: { popup: null } }]);
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
