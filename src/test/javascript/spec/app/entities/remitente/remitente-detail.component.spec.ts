import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { RemitenteDetailComponent } from 'app/entities/remitente/remitente-detail.component';
import { Remitente } from 'app/shared/model/remitente.model';

describe('Component Tests', () => {
  describe('Remitente Management Detail Component', () => {
    let comp: RemitenteDetailComponent;
    let fixture: ComponentFixture<RemitenteDetailComponent>;
    const route = ({ data: of({ remitente: new Remitente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [RemitenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RemitenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemitenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remitente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
