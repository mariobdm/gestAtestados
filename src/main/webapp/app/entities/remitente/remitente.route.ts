import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Remitente } from 'app/shared/model/remitente.model';
import { RemitenteService } from './remitente.service';
import { RemitenteComponent } from './remitente.component';
import { RemitenteDetailComponent } from './remitente-detail.component';
import { RemitenteUpdateComponent } from './remitente-update.component';
import { RemitenteDeletePopupComponent } from './remitente-delete-dialog.component';
import { IRemitente } from 'app/shared/model/remitente.model';

@Injectable({ providedIn: 'root' })
export class RemitenteResolve implements Resolve<IRemitente> {
  constructor(private service: RemitenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRemitente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Remitente>) => response.ok),
        map((remitente: HttpResponse<Remitente>) => remitente.body)
      );
    }
    return of(new Remitente());
  }
}

export const remitenteRoute: Routes = [
  {
    path: '',
    component: RemitenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remitentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RemitenteDetailComponent,
    resolve: {
      remitente: RemitenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remitentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RemitenteUpdateComponent,
    resolve: {
      remitente: RemitenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remitentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RemitenteUpdateComponent,
    resolve: {
      remitente: RemitenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remitentes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const remitentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RemitenteDeletePopupComponent,
    resolve: {
      remitente: RemitenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remitentes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
