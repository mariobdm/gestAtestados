import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SessionsComponent } from './sessions.component';

export const sessionsRoute: Route = {
  path: 'sessions',
  component: SessionsComponent,
  data: {
    authorities: ['ROLE_USER', 'ROLE_MODIFY', 'ROLE_DELETE'],
    pageTitle: 'Sessions'
  },
  canActivate: [UserRouteAccessService]
};
