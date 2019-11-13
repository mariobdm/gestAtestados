import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

type EntityResponseType = HttpResponse<ITasaAlcohol>;
type EntityArrayResponseType = HttpResponse<ITasaAlcohol[]>;

@Injectable({ providedIn: 'root' })
export class TasaAlcoholService {
  public resourceUrl = SERVER_API_URL + 'api/tasa-alcohols';

  constructor(protected http: HttpClient) {}

  create(tasaAlcohol: ITasaAlcohol): Observable<EntityResponseType> {
    return this.http.post<ITasaAlcohol>(this.resourceUrl, tasaAlcohol, { observe: 'response' });
  }

  update(tasaAlcohol: ITasaAlcohol): Observable<EntityResponseType> {
    return this.http.put<ITasaAlcohol>(this.resourceUrl, tasaAlcohol, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITasaAlcohol>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITasaAlcohol[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
