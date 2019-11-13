import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { AtestadoComponent } from './atestado.component';
import { AtestadoDetailComponent } from './atestado-detail.component';
import { AtestadoUpdateComponent } from './atestado-update.component';
import { AtestadoDeletePopupComponent, AtestadoDeleteDialogComponent } from './atestado-delete-dialog.component';
import { atestadoRoute, atestadoPopupRoute } from './atestado.route';

const ENTITY_STATES = [...atestadoRoute, ...atestadoPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AtestadoComponent,
    AtestadoDetailComponent,
    AtestadoUpdateComponent,
    AtestadoDeleteDialogComponent,
    AtestadoDeletePopupComponent
  ],
  entryComponents: [AtestadoComponent, AtestadoUpdateComponent, AtestadoDeleteDialogComponent, AtestadoDeletePopupComponent]
})
export class GestAtestadosAtestadoModule {}
