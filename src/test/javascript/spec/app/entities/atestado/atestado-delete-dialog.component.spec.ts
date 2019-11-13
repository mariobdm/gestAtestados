import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestAtestadosTestModule } from '../../../test.module';
import { AtestadoDeleteDialogComponent } from 'app/entities/atestado/atestado-delete-dialog.component';
import { AtestadoService } from 'app/entities/atestado/atestado.service';

describe('Component Tests', () => {
  describe('Atestado Management Delete Component', () => {
    let comp: AtestadoDeleteDialogComponent;
    let fixture: ComponentFixture<AtestadoDeleteDialogComponent>;
    let service: AtestadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [AtestadoDeleteDialogComponent]
      })
        .overrideTemplate(AtestadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AtestadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtestadoService);
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
