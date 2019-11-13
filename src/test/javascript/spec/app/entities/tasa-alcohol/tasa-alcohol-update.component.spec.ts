import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { TasaAlcoholUpdateComponent } from 'app/entities/tasa-alcohol/tasa-alcohol-update.component';
import { TasaAlcoholService } from 'app/entities/tasa-alcohol/tasa-alcohol.service';
import { TasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

describe('Component Tests', () => {
  describe('TasaAlcohol Management Update Component', () => {
    let comp: TasaAlcoholUpdateComponent;
    let fixture: ComponentFixture<TasaAlcoholUpdateComponent>;
    let service: TasaAlcoholService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [TasaAlcoholUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TasaAlcoholUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TasaAlcoholUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasaAlcoholService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TasaAlcohol(123);
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
        const entity = new TasaAlcohol();
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
