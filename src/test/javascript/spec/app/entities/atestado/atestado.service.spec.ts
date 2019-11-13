import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AtestadoService } from 'app/entities/atestado/atestado.service';
import { IAtestado, Atestado } from 'app/shared/model/atestado.model';
import { EnumTipoJuicio } from 'app/shared/model/enumerations/enum-tipo-juicio.model';

describe('Service Tests', () => {
  describe('Atestado Service', () => {
    let injector: TestBed;
    let service: AtestadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAtestado;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AtestadoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Atestado(
        0,
        'AAAAAAA',
        EnumTipoJuicio.RAPIDO,
        currentDate,
        currentDate,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaAtestado: currentDate.format(DATE_FORMAT),
            fechaHoraSuceso: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Atestado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAtestado: currentDate.format(DATE_FORMAT),
            fechaHoraSuceso: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAtestado: currentDate,
            fechaHoraSuceso: currentDate
          },
          returnedFromService
        );
        service
          .create(new Atestado(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Atestado', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            tipojuicio: 'BBBBBB',
            fechaAtestado: currentDate.format(DATE_FORMAT),
            fechaHoraSuceso: currentDate.format(DATE_TIME_FORMAT),
            lugar: 'BBBBBB',
            accidente: true,
            permiso: true,
            alcoholemia: true,
            bienes: true,
            velocidad: true,
            lesiones: true,
            fallecido: true,
            instructor: 'BBBBBB',
            secretario: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAtestado: currentDate,
            fechaHoraSuceso: currentDate
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

      it('should return a list of Atestado', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            tipojuicio: 'BBBBBB',
            fechaAtestado: currentDate.format(DATE_FORMAT),
            fechaHoraSuceso: currentDate.format(DATE_TIME_FORMAT),
            lugar: 'BBBBBB',
            accidente: true,
            permiso: true,
            alcoholemia: true,
            bienes: true,
            velocidad: true,
            lesiones: true,
            fallecido: true,
            instructor: 'BBBBBB',
            secretario: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAtestado: currentDate,
            fechaHoraSuceso: currentDate
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

      it('should delete a Atestado', () => {
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
