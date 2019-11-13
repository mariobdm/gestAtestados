import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { AtestadoComponent } from 'app/entities/atestado/atestado.component';
import { AtestadoService } from 'app/entities/atestado/atestado.service';
import { Atestado } from 'app/shared/model/atestado.model';

describe('Component Tests', () => {
  describe('Atestado Management Component', () => {
    let comp: AtestadoComponent;
    let fixture: ComponentFixture<AtestadoComponent>;
    let service: AtestadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [AtestadoComponent],
        providers: []
      })
        .overrideTemplate(AtestadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AtestadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AtestadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Atestado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.atestados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
