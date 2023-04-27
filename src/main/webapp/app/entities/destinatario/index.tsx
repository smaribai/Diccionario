import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Destinatario from './destinatario';
import DestinatarioDetail from './destinatario-detail';
import DestinatarioUpdate from './destinatario-update';
import DestinatarioDeleteDialog from './destinatario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DestinatarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DestinatarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DestinatarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Destinatario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DestinatarioDeleteDialog} />
  </>
);

export default Routes;
