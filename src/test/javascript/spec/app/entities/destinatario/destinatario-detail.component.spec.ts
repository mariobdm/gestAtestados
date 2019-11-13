import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestAtestadosTestModule } from '../../../test.module';
import { DestinatarioDetailComponent } from 'app/entities/destinatario/destinatario-detail.component';
import { Destinatario } from 'app/shared/model/destinatario.model';

describe('Component Tests', () => {
  describe('Destinatario Management Detail Component', () => {
    let comp: DestinatarioDetailComponent;
    let fixture: ComponentFixture<DestinatarioDetailComponent>;
    const route = ({ data: of({ destinatario: new Destinatario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [DestinatarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DestinatarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DestinatarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.destinatario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
