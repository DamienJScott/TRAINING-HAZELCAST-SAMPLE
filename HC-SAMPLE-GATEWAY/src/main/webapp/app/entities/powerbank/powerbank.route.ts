import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPowerbank, Powerbank } from 'app/shared/model/powerbank.model';
import { PowerbankService } from './powerbank.service';
import { PowerbankComponent } from './powerbank.component';
import { PowerbankDetailComponent } from './powerbank-detail.component';
import { PowerbankUpdateComponent } from './powerbank-update.component';

@Injectable({ providedIn: 'root' })
export class PowerbankResolve implements Resolve<IPowerbank> {
  constructor(private service: PowerbankService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPowerbank> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((powerbank: HttpResponse<Powerbank>) => {
          if (powerbank.body) {
            return of(powerbank.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Powerbank());
  }
}

export const powerbankRoute: Routes = [
  {
    path: '',
    component: PowerbankComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.powerbank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PowerbankDetailComponent,
    resolve: {
      powerbank: PowerbankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.powerbank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PowerbankUpdateComponent,
    resolve: {
      powerbank: PowerbankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.powerbank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PowerbankUpdateComponent,
    resolve: {
      powerbank: PowerbankResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.powerbank.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
