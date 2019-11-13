import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestAtestadosTestModule } from '../../../test.module';
import { DocumentoComponent } from 'app/entities/documento/documento.component';
import { DocumentoService } from 'app/entities/documento/documento.service';
import { Documento } from 'app/shared/model/documento.model';

describe('Component Tests', () => {
  describe('Documento Management Component', () => {
    let comp: DocumentoComponent;
    let fixture: ComponentFixture<DocumentoComponent>;
    let service: DocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestAtestadosTestModule],
        declarations: [DocumentoComponent],
        providers: []
      })
        .overrideTemplate(DocumentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Documento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
