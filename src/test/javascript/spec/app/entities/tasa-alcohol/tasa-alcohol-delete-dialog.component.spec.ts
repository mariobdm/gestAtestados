import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestAtestadosTestModule } from '../../../test.module';
import { TasaAlcoholDeleteDialogComponent } from 'app/entities/tasa-alcohol/tasa-alcohol-delete-dialog.component';
import { TasaAlcoholService } from 'app/entities/tasa-alcohol/tasa-alcohol.service';

describe('Component Tests', () => {
  describe('TasaAlcohol Management Delete Component', () => {
    let comp: TasaAlcoholDeleteDialogComponent;
    let fixture: ComponentFixture<TasaAlcoholDeleteDialogComponent>;
    let service: TasaAlcoholService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [TasaAlcoholDeleteDialogComponent]
      })
        .overrideTemplate(TasaAlcoholDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TasaAlcoholDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasaAlcoholService);
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
