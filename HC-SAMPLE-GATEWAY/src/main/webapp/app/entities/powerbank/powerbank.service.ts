import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPowerbank } from 'app/shared/model/powerbank.model';

type EntityResponseType = HttpResponse<IPowerbank>;
type EntityArrayResponseType = HttpResponse<IPowerbank[]>;

@Injectable({ providedIn: 'root' })
export class PowerbankService {
  public resourceUrl = SERVER_API_URL + 'services/hc-sample-ms-b/api/powerbanks';

  constructor(protected http: HttpClient) {}

  create(powerbank: IPowerbank): Observable<EntityResponseType> {
    return this.http.post<IPowerbank>(this.resourceUrl, powerbank, { observe: 'response' });
  }

  update(powerbank: IPowerbank): Observable<EntityResponseType> {
    return this.http.put<IPowerbank>(this.resourceUrl, powerbank, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPowerbank>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPowerbank[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
