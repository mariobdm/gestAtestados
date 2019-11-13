import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { TasaAlcoholComponent } from 'app/entities/tasa-alcohol/tasa-alcohol.component';
import { TasaAlcoholService } from 'app/entities/tasa-alcohol/tasa-alcohol.service';
import { TasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

describe('Component Tests', () => {
  describe('TasaAlcohol Management Component', () => {
    let comp: TasaAlcoholComponent;
    let fixture: ComponentFixture<TasaAlcoholComponent>;
    let service: TasaAlcoholService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [TasaAlcoholComponent],
        providers: []
      })
        .overrideTemplate(TasaAlcoholComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TasaAlcoholComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasaAlcoholService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TasaAlcohol(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tasaAlcohols[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
