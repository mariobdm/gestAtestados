import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { ImplicadoComponent } from './implicado.component';
import { ImplicadoDetailComponent } from './implicado-detail.component';
import { ImplicadoUpdateComponent } from './implicado-update.component';
import { ImplicadoDeletePopupComponent, ImplicadoDeleteDialogComponent } from './implicado-delete-dialog.component';
import { implicadoRoute, implicadoPopupRoute } from './implicado.route';
import { ImplicadoAtestadoComponent } from './implicado-atestado.component';

const ENTITY_STATES = [...implicadoRoute, ...implicadoPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImplicadoComponent,
    ImplicadoDetailComponent,
    ImplicadoUpdateComponent,
    ImplicadoDeleteDialogComponent,
    ImplicadoDeletePopupComponent,
    ImplicadoAtestadoComponent
  ],
  entryComponents: [
    ImplicadoComponent,
    ImplicadoUpdateComponent,
    ImplicadoDeleteDialogComponent,
    ImplicadoDeletePopupComponent,
    ImplicadoAtestadoComponent
  ]
})
export class GestAtestadosImplicadoModule {}
