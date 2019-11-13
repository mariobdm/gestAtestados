import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { AtestadoUpdateComponent } from 'app/entities/atestado/atestado-update.component';
import { AtestadoService } from 'app/entities/atestado/atestado.service';
import { Atestado } from 'app/shared/model/atestado.model';

describe('Component Tests', () => {
  describe('Atestado Management Update Component', () => {
    let comp: AtestadoUpdateComponent;
    let fixture: ComponentFixture<AtestadoUpdateComponent>;
    let service: AtestadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [AtestadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AtestadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AtestadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtestadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Atestado(123);
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
        const entity = new Atestado();
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
