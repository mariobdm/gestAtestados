import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestAtestadosSharedModule } from 'app/shared/shared.module';
import { DocumentoComponent } from './documento.component';
import { DocumentoDetailComponent } from './documento-detail.component';
import { DocumentoUpdateComponent } from './documento-update.component';
import { DocumentoDeletePopupComponent, DocumentoDeleteDialogComponent } from './documento-delete-dialog.component';
import { documentoRoute, documentoPopupRoute } from './documento.route';
import { DocumentoAtestadoComponent } from './documento-atestado.component';

const ENTITY_STATES = [...documentoRoute, ...documentoPopupRoute];

@NgModule({
  imports: [GestAtestadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentoComponent,
    DocumentoDetailComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent,
    DocumentoAtestadoComponent
  ],
  entryComponents: [
    DocumentoComponent,
    DocumentoUpdateComponent,
    DocumentoDeleteDialogComponent,
    DocumentoDeletePopupComponent,
    DocumentoAtestadoComponent
  ]
})
export class GestAtestadosDocumentoModule {}
