import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestAtestadosTestModule } from '../../../test.module';
import { DestinatarioDeleteDialogComponent } from 'app/entities/destinatario/destinatario-delete-dialog.component';
import { DestinatarioService } from 'app/entities/destinatario/destinatario.service';

describe('Component Tests', () => {
  describe('Destinatario Management Delete Component', () => {
    let comp: DestinatarioDeleteDialogComponent;
    let fixture: ComponentFixture<DestinatarioDeleteDialogComponent>;
    let service: DestinatarioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [DestinatarioDeleteDialogComponent]
      })
        .overrideTemplate(DestinatarioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DestinatarioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinatarioService);
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
