import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRemitente } from 'app/shared/model/remitente.model';

type EntityResponseType = HttpResponse<IRemitente>;
type EntityArrayResponseType = HttpResponse<IRemitente[]>;

@Injectable({ providedIn: 'root' })
export class RemitenteService {
  public resourceUrl = SERVER_API_URL + 'api/remitentes';

  constructor(protected http: HttpClient) {}

  create(remitente: IRemitente): Observable<EntityResponseType> {
    return this.http.post<IRemitente>(this.resourceUrl, remitente, { observe: 'response' });
  }

  update(remitente: IRemitente): Observable<EntityResponseType> {
    return this.http.put<IRemitente>(this.resourceUrl, remitente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemitente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemitente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
