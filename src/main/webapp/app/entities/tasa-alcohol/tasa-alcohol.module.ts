import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { TasaAlcoholComponent } from './tasa-alcohol.component';
import { TasaAlcoholDetailComponent } from './tasa-alcohol-detail.component';
import { TasaAlcoholUpdateComponent } from './tasa-alcohol-update.component';
import { TasaAlcoholDeletePopupComponent, TasaAlcoholDeleteDialogComponent } from './tasa-alcohol-delete-dialog.component';
import { tasaAlcoholRoute, tasaAlcoholPopupRoute } from './tasa-alcohol.route';

const ENTITY_STATES = [...tasaAlcoholRoute, ...tasaAlcoholPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TasaAlcoholComponent,
    TasaAlcoholDetailComponent,
    TasaAlcoholUpdateComponent,
    TasaAlcoholDeleteDialogComponent,
    TasaAlcoholDeletePopupComponent
  ],
  entryComponents: [TasaAlcoholComponent, TasaAlcoholUpdateComponent, TasaAlcoholDeleteDialogComponent, TasaAlcoholDeletePopupComponent]
})
export class GestAtestadosTasaAlcoholModule {}
