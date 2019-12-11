import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImplicado } from 'app/shared/model/implicado.model';

type EntityResponseType = HttpResponse<IImplicado>;
type EntityArrayResponseType = HttpResponse<IImplicado[]>;

@Injectable({ providedIn: 'root' })
export class ImplicadoService {
  public resourceUrl = SERVER_API_URL + 'api/implicados';

  constructor(protected http: HttpClient) {}

  create(implicado: IImplicado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(implicado);
    return this.http
      .post<IImplicado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(implicado: IImplicado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(implicado);
    return this.http
      .put<IImplicado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImplicado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImplicado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByAtestado(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IImplicado[]>(`${this.resourceUrl}/atestado/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(implicado: IImplicado): IImplicado {
    const copy: IImplicado = Object.assign({}, implicado, {
      fechaNacimiento:
        implicado.fechaNacimiento != null && implicado.fechaNacimiento.isValid() ? implicado.fechaNacimiento.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaNacimiento = res.body.fechaNacimiento != null ? moment(res.body.fechaNacimiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((implicado: IImplicado) => {
        implicado.fechaNacimiento = implicado.fechaNacimiento != null ? moment(implicado.fechaNacimiento) : null;
      });
    }
    return res;
  }
}
