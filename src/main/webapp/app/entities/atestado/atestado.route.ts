import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Atestado } from 'app/shared/model/atestado.model';
import { AtestadoService } from './atestado.service';
import { AtestadoComponent } from './atestado.component';
import { AtestadoDetailComponent } from './atestado-detail.component';
import { AtestadoUpdateComponent } from './atestado-update.component';
import { AtestadoDeletePopupComponent } from './atestado-delete-dialog.component';
import { IAtestado } from 'app/shared/model/atestado.model';

@Injectable({ providedIn: 'root' })
export class AtestadoResolve implements Resolve<IAtestado> {
  constructor(private service: AtestadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAtestado> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Atestado>) => response.ok),
        map((atestado: HttpResponse<Atestado>) => atestado.body)
      );
    }
    return of(new Atestado());
  }
}

export const atestadoRoute: Routes = [
  {
    path: '',
    component: AtestadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Atestados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AtestadoDetailComponent,
    resolve: {
      atestado: AtestadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Atestados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AtestadoUpdateComponent,
    resolve: {
      atestado: AtestadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Atestados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AtestadoUpdateComponent,
    resolve: {
      atestado: AtestadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Atestados'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const atestadoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AtestadoDeletePopupComponent,
    resolve: {
      atestado: AtestadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Atestados'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
