import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { ImplicadoUpdateComponent } from 'app/entities/implicado/implicado-update.component';
import { ImplicadoService } from 'app/entities/implicado/implicado.service';
import { Implicado } from 'app/shared/model/implicado.model';

describe('Component Tests', () => {
  describe('Implicado Management Update Component', () => {
    let comp: ImplicadoUpdateComponent;
    let fixture: ComponentFixture<ImplicadoUpdateComponent>;
    let service: ImplicadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [ImplicadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImplicadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImplicadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImplicadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Implicado(123);
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
        const entity = new Implicado();
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
