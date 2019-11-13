import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { DocumentoComponent } from './documento.component';
import { DocumentoDetailComponent } from './documento-detail.component';
import { DocumentoUpdateComponent } from './documento-update.component';
import { DocumentoDeletePopupComponent, DocumentoDeleteDialogComponent } from './documento-delete-dialog.component';
import { documentoRoute, documentoPopupRoute } from './documento.route';

const ENTITY_STATES = [...documentoRoute, ...documentoPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentoComponent,
    DocumentoDetailComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent
  ],
  entryComponents: [DocumentoComponent, DocumentoUpdateComponent, DocumentoDeleteDialogComponent, DocumentoDeletePopupComponent]
})
export class GestAtestadosDocumentoModule {}
