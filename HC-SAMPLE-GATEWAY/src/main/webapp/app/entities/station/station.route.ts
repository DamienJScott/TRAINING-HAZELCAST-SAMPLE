import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStation, Station } from 'app/shared/model/station.model';
import { StationService } from './station.service';
import { StationComponent } from './station.component';
import { StationDetailComponent } from './station-detail.component';
import { StationUpdateComponent } from './station-update.component';

@Injectable({ providedIn: 'root' })
export class StationResolve implements Resolve<IStation> {
  constructor(private service: StationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((station: HttpResponse<Station>) => {
          if (station.body) {
            return of(station.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Station());
  }
}

export const stationRoute: Routes = [
  {
    path: '',
    component: StationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.station.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StationDetailComponent,
    resolve: {
      station: StationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.station.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StationUpdateComponent,
    resolve: {
      station: StationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.station.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StationUpdateComponent,
    resolve: {
      station: StationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.station.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
