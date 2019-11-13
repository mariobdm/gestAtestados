import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { ImplicadoComponent } from 'app/entities/implicado/implicado.component';
import { ImplicadoService } from 'app/entities/implicado/implicado.service';
import { Implicado } from 'app/shared/model/implicado.model';

describe('Component Tests', () => {
  describe('Implicado Management Component', () => {
    let comp: ImplicadoComponent;
    let fixture: ComponentFixture<ImplicadoComponent>;
    let service: ImplicadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [ImplicadoComponent],
        providers: []
      })
        .overrideTemplate(ImplicadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImplicadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImplicadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Implicado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.implicados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
