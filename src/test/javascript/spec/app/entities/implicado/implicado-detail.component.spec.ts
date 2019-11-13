import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { ImplicadoDetailComponent } from 'app/entities/implicado/implicado-detail.component';
import { Implicado } from 'app/shared/model/implicado.model';

describe('Component Tests', () => {
  describe('Implicado Management Detail Component', () => {
    let comp: ImplicadoDetailComponent;
    let fixture: ComponentFixture<ImplicadoDetailComponent>;
    const route = ({ data: of({ implicado: new Implicado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [ImplicadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImplicadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImplicadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.implicado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
