import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Destinatario } from 'app/shared/model/destinatario.model';
import { DestinatarioService } from './destinatario.service';
import { DestinatarioComponent } from './destinatario.component';
import { DestinatarioDetailComponent } from './destinatario-detail.component';
import { DestinatarioUpdateComponent } from './destinatario-update.component';
import { DestinatarioDeletePopupComponent } from './destinatario-delete-dialog.component';
import { IDestinatario } from 'app/shared/model/destinatario.model';

@Injectable({ providedIn: 'root' })
export class DestinatarioResolve implements Resolve<IDestinatario> {
  constructor(private service: DestinatarioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDestinatario> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Destinatario>) => response.ok),
        map((destinatario: HttpResponse<Destinatario>) => destinatario.body)
      );
    }
    return of(new Destinatario());
  }
}

export const destinatarioRoute: Routes = [
  {
    path: '',
    component: DestinatarioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinatarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DestinatarioDetailComponent,
    resolve: {
      destinatario: DestinatarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinatarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DestinatarioUpdateComponent,
    resolve: {
      destinatario: DestinatarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinatarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DestinatarioUpdateComponent,
    resolve: {
      destinatario: DestinatarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinatarios'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const destinatarioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DestinatarioDeletePopupComponent,
    resolve: {
      destinatario: DestinatarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinatarios'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
