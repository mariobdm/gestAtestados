import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { DestinatarioComponent } from './destinatario.component';
import { DestinatarioDetailComponent } from './destinatario-detail.component';
import { DestinatarioUpdateComponent } from './destinatario-update.component';
import { DestinatarioDeletePopupComponent, DestinatarioDeleteDialogComponent } from './destinatario-delete-dialog.component';
import { destinatarioRoute, destinatarioPopupRoute } from './destinatario.route';

const ENTITY_STATES = [...destinatarioRoute, ...destinatarioPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DestinatarioComponent,
    DestinatarioDetailComponent,
    DestinatarioUpdateComponent,
    DestinatarioDeleteDialogComponent,
    DestinatarioDeletePopupComponent
  ],
  entryComponents: [DestinatarioComponent, DestinatarioUpdateComponent, DestinatarioDeleteDialogComponent, DestinatarioDeletePopupComponent]
})
export class GestAtestadosDestinatarioModule {}
