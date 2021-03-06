import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStation } from 'app/shared/model/station.model';

type EntityResponseType = HttpResponse<IStation>;
type EntityArrayResponseType = HttpResponse<IStation[]>;

@Injectable({ providedIn: 'root' })
export class StationService {
  public resourceUrl = SERVER_API_URL + 'services/hc-sample-ms-b/api/stations';

  constructor(protected http: HttpClient) {}

  create(station: IStation): Observable<EntityResponseType> {
    return this.http.post<IStation>(this.resourceUrl, station, { observe: 'response' });
  }

  update(station: IStation): Observable<EntityResponseType> {
    return this.http.put<IStation>(this.resourceUrl, station, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
