import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { RemitenteComponent } from 'app/entities/remitente/remitente.component';
import { RemitenteService } from 'app/entities/remitente/remitente.service';
import { Remitente } from 'app/shared/model/remitente.model';

describe('Component Tests', () => {
  describe('Remitente Management Component', () => {
    let comp: RemitenteComponent;
    let fixture: ComponentFixture<RemitenteComponent>;
    let service: RemitenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [RemitenteComponent],
        providers: []
      })
        .overrideTemplate(RemitenteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemitenteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemitenteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Remitente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.remitentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
