import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { RemitenteUpdateComponent } from 'app/entities/remitente/remitente-update.component';
import { RemitenteService } from 'app/entities/remitente/remitente.service';
import { Remitente } from 'app/shared/model/remitente.model';

describe('Component Tests', () => {
  describe('Remitente Management Update Component', () => {
    let comp: RemitenteUpdateComponent;
    let fixture: ComponentFixture<RemitenteUpdateComponent>;
    let service: RemitenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [RemitenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RemitenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemitenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemitenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remitente(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remitente();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
