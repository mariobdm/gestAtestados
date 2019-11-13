import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { RemitenteComponent } from './remitente.component';
import { RemitenteDetailComponent } from './remitente-detail.component';
import { RemitenteUpdateComponent } from './remitente-update.component';
import { RemitenteDeletePopupComponent, RemitenteDeleteDialogComponent } from './remitente-delete-dialog.component';
import { remitenteRoute, remitentePopupRoute } from './remitente.route';

const ENTITY_STATES = [...remitenteRoute, ...remitentePopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RemitenteComponent,
    RemitenteDetailComponent,
    RemitenteUpdateComponent,
    RemitenteDeleteDialogComponent,
    RemitenteDeletePopupComponent
  ],
  entryComponents: [RemitenteComponent, RemitenteUpdateComponent, RemitenteDeleteDialogComponent, RemitenteDeletePopupComponent]
})
export class GestAtestadosRemitenteModule {}
