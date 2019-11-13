import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'atestado',
        loadChildren: () => import('./atestado/atestado.module').then(m => m.GestAtestadosAtestadoModule)
      },
      {
        path: 'documento',
        loadChildren: () => import('./documento/documento.module').then(m => m.GestAtestadosDocumentoModule)
      },
      {
        path: 'destinatario',
        loadChildren: () => import('./destinatario/destinatario.module').then(m => m.GestAtestadosDestinatarioModule)
      },
      {
        path: 'remitente',
        loadChildren: () => import('./remitente/remitente.module').then(m => m.GestAtestadosRemitenteModule)
      },
      {
        path: 'implicado',
        loadChildren: () => import('./implicado/implicado.module').then(m => m.GestAtestadosImplicadoModule)
      },
      {
        path: 'tasa-alcohol',
        loadChildren: () => import('./tasa-alcohol/tasa-alcohol.module').then(m => m.GestAtestadosTasaAlcoholModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: []
})
export class GestAtestadosEntityModule {}
