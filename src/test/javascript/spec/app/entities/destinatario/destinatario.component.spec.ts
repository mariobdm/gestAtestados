import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { DestinatarioComponent } from 'app/entities/destinatario/destinatario.component';
import { DestinatarioService } from 'app/entities/destinatario/destinatario.service';
import { Destinatario } from 'app/shared/model/destinatario.model';

describe('Component Tests', () => {
  describe('Destinatario Management Component', () => {
    let comp: DestinatarioComponent;
    let fixture: ComponentFixture<DestinatarioComponent>;
    let service: DestinatarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [DestinatarioComponent],
        providers: []
      })
        .overrideTemplate(DestinatarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DestinatarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinatarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Destinatario(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.destinatarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
