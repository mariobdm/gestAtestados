import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestAtestadosTestModule } from '../../../test.module';
import { ImplicadoDeleteDialogComponent } from 'app/entities/implicado/implicado-delete-dialog.component';
import { ImplicadoService } from 'app/entities/implicado/implicado.service';

describe('Component Tests', () => {
  describe('Implicado Management Delete Component', () => {
    let comp: ImplicadoDeleteDialogComponent;
    let fixture: ComponentFixture<ImplicadoDeleteDialogComponent>;
    let service: ImplicadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [ImplicadoDeleteDialogComponent]
      })
        .overrideTemplate(ImplicadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImplicadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImplicadoService);
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
