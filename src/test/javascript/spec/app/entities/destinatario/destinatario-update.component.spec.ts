import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { DestinatarioUpdateComponent } from 'app/entities/destinatario/destinatario-update.component';
import { DestinatarioService } from 'app/entities/destinatario/destinatario.service';
import { Destinatario } from 'app/shared/model/destinatario.model';

describe('Component Tests', () => {
  describe('Destinatario Management Update Component', () => {
    let comp: DestinatarioUpdateComponent;
    let fixture: ComponentFixture<DestinatarioUpdateComponent>;
    let service: DestinatarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [DestinatarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DestinatarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DestinatarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinatarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Destinatario(123);
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
        const entity = new Destinatario();
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
