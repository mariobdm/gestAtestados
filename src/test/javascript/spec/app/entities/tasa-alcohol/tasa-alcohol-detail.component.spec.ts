import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { TasaAlcoholDetailComponent } from 'app/entities/tasa-alcohol/tasa-alcohol-detail.component';
import { TasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

describe('Component Tests', () => {
  describe('TasaAlcohol Management Detail Component', () => {
    let comp: TasaAlcoholDetailComponent;
    let fixture: ComponentFixture<TasaAlcoholDetailComponent>;
    const route = ({ data: of({ tasaAlcohol: new TasaAlcohol(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [TasaAlcoholDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TasaAlcoholDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TasaAlcoholDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tasaAlcohol).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
