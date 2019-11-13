import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAtestado } from 'app/shared/model/atestado.model';

type EntityResponseType = HttpResponse<IAtestado>;
type EntityArrayResponseType = HttpResponse<IAtestado[]>;

@Injectable({ providedIn: 'root' })
export class AtestadoService {
  public resourceUrl = SERVER_API_URL + 'api/atestados';

  constructor(protected http: HttpClient) {}

  create(atestado: IAtestado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atestado);
    return this.http
      .post<IAtestado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(atestado: IAtestado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(atestado);
    return this.http
      .put<IAtestado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAtestado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAtestado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(atestado: IAtestado): IAtestado {
    const copy: IAtestado = Object.assign({}, atestado, {
      fechaAtestado: atestado.fechaAtestado != null && atestado.fechaAtestado.isValid() ? atestado.fechaAtestado.format(DATE_FORMAT) : null,
      fechaHoraSuceso: atestado.fechaHoraSuceso != null && atestado.fechaHoraSuceso.isValid() ? atestado.fechaHoraSuceso.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAtestado = res.body.fechaAtestado != null ? moment(res.body.fechaAtestado) : null;
      res.body.fechaHoraSuceso = res.body.fechaHoraSuceso != null ? moment(res.body.fechaHoraSuceso) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((atestado: IAtestado) => {
        atestado.fechaAtestado = atestado.fechaAtestado != null ? moment(atestado.fechaAtestado) : null;
        atestado.fechaHoraSuceso = atestado.fechaHoraSuceso != null ? moment(atestado.fechaHoraSuceso) : null;
      });
    }
    return res;
  }
}
