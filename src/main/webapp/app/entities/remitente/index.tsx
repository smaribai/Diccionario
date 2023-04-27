import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Remitente from './remitente';
import RemitenteDetail from './remitente-detail';
import RemitenteUpdate from './remitente-update';
import RemitenteDeleteDialog from './remitente-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RemitenteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RemitenteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RemitenteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Remitente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RemitenteDeleteDialog} />
  </>
);

export default Routes;
