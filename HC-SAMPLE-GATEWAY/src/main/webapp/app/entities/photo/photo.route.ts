import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhoto, Photo } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { PhotoComponent } from './photo.component';
import { PhotoDetailComponent } from './photo-detail.component';
import { PhotoUpdateComponent } from './photo-update.component';

@Injectable({ providedIn: 'root' })
export class PhotoResolve implements Resolve<IPhoto> {
  constructor(private service: PhotoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhoto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((photo: HttpResponse<Photo>) => {
          if (photo.body) {
            return of(photo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Photo());
  }
}

export const photoRoute: Routes = [
  {
    path: '',
    component: PhotoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.photo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhotoDetailComponent,
    resolve: {
      photo: PhotoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.photo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhotoUpdateComponent,
    resolve: {
      photo: PhotoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.photo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhotoUpdateComponent,
    resolve: {
      photo: PhotoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hcSampleApp.photo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
