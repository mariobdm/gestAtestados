import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Implicado } from 'app/shared/model/implicado.model';
import { ImplicadoService } from './implicado.service';
import { ImplicadoComponent } from './implicado.component';
import { ImplicadoDetailComponent } from './implicado-detail.component';
import { ImplicadoUpdateComponent } from './implicado-update.component';
import { ImplicadoDeletePopupComponent } from './implicado-delete-dialog.component';
import { IImplicado } from 'app/shared/model/implicado.model';
import { ImplicadoAtestadoComponent } from './implicado-atestado.component';

@Injectable({ providedIn: 'root' })
export class ImplicadoResolve implements Resolve<IImplicado> {
  constructor(private service: ImplicadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImplicado> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Implicado>) => response.ok),
        map((implicado: HttpResponse<Implicado>) => implicado.body)
      );
    }
    return of(new Implicado());
  }
}

export const implicadoRoute: Routes = [
  {
    path: '',
    component: ImplicadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImplicadoDetailComponent,
    resolve: {
      implicado: ImplicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImplicadoUpdateComponent,
    resolve: {
      implicado: ImplicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImplicadoUpdateComponent,
    resolve: {
      implicado: ImplicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicados'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'atestado/:id',
    component: ImplicadoAtestadoComponent,
    resolve: {
      documento: ImplicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicado por Atestado'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const implicadoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImplicadoDeletePopupComponent,
    resolve: {
      implicado: ImplicadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Implicados'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
