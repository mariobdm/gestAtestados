import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ImplicadoService } from 'app/entities/implicado/implicado.service';
import { IImplicado, Implicado } from 'app/shared/model/implicado.model';
import { EnumTipoDocumentacion } from 'app/shared/model/enumerations/enum-tipo-documentacion.model';
import { EnumTipoImplicado } from 'app/shared/model/enumerations/enum-tipo-implicado.model';

describe('Service Tests', () => {
  describe('Implicado Service', () => {
    let injector: TestBed;
    let service: ImplicadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IImplicado;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ImplicadoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Implicado(
        0,
        EnumTipoDocumentacion.DNI,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        EnumTipoImplicado.IMPUTADO,
        'AAAAAAA',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Implicado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Implicado(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Implicado', () => {
        const returnedFromService = Object.assign(
          {
            tipoDocumento: 'BBBBBB',
            documento: 'BBBBBB',
            nombre: 'BBBBBB',
            apellido1: 'BBBBBB',
            apellido2: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            telefono: 'BBBBBB',
            calidad: 'BBBBBB',
            direccion: 'BBBBBB',
            municipio: 'BBBBBB',
            codigopostal: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Implicado', () => {
        const returnedFromService = Object.assign(
          {
            tipoDocumento: 'BBBBBB',
            documento: 'BBBBBB',
            nombre: 'BBBBBB',
            apellido1: 'BBBBBB',
            apellido2: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            telefono: 'BBBBBB',
            calidad: 'BBBBBB',
            direccion: 'BBBBBB',
            municipio: 'BBBBBB',
            codigopostal: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Implicado', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
