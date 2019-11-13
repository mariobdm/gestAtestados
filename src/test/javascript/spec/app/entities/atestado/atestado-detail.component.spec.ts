import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { AtestadoDetailComponent } from 'app/entities/atestado/atestado-detail.component';
import { Atestado } from 'app/shared/model/atestado.model';

describe('Component Tests', () => {
  describe('Atestado Management Detail Component', () => {
    let comp: AtestadoDetailComponent;
    let fixture: ComponentFixture<AtestadoDetailComponent>;
    const route = ({ data: of({ atestado: new Atestado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [AtestadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AtestadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AtestadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.atestado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
