import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestAtestadosTestModule } from '../../../test.module';
import { RemitenteDeleteDialogComponent } from 'app/entities/remitente/remitente-delete-dialog.component';
import { RemitenteService } from 'app/entities/remitente/remitente.service';

describe('Component Tests', () => {
  describe('Remitente Management Delete Component', () => {
    let comp: RemitenteDeleteDialogComponent;
    let fixture: ComponentFixture<RemitenteDeleteDialogComponent>;
    let service: RemitenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [RemitenteDeleteDialogComponent]
      })
        .overrideTemplate(RemitenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemitenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemitenteService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
