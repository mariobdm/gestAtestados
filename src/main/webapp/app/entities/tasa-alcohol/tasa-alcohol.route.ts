import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TasaAlcohol } from 'app/shared/model/tasa-alcohol.model';
import { TasaAlcoholService } from './tasa-alcohol.service';
import { TasaAlcoholComponent } from './tasa-alcohol.component';
import { TasaAlcoholDetailComponent } from './tasa-alcohol-detail.component';
import { TasaAlcoholUpdateComponent } from './tasa-alcohol-update.component';
import { TasaAlcoholDeletePopupComponent } from './tasa-alcohol-delete-dialog.component';
import { ITasaAlcohol } from 'app/shared/model/tasa-alcohol.model';

@Injectable({ providedIn: 'root' })
export class TasaAlcoholResolve implements Resolve<ITasaAlcohol> {
  constructor(private service: TasaAlcoholService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITasaAlcohol> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TasaAlcohol>) => response.ok),
        map((tasaAlcohol: HttpResponse<TasaAlcohol>) => tasaAlcohol.body)
      );
    }
    return of(new TasaAlcohol());
  }
}

export const tasaAlcoholRoute: Routes = [
  {
    path: '',
    component: TasaAlcoholComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TasaAlcohols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TasaAlcoholDetailComponent,
    resolve: {
      tasaAlcohol: TasaAlcoholResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TasaAlcohols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TasaAlcoholUpdateComponent,
    resolve: {
      tasaAlcohol: TasaAlcoholResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TasaAlcohols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TasaAlcoholUpdateComponent,
    resolve: {
      tasaAlcohol: TasaAlcoholResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TasaAlcohols'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tasaAlcoholPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TasaAlcoholDeletePopupComponent,
    resolve: {
      tasaAlcohol: TasaAlcoholResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TasaAlcohols'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
