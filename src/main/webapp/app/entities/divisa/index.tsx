import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Divisa from './divisa';
import DivisaDetail from './divisa-detail';
import DivisaUpdate from './divisa-update';
import DivisaDeleteDialog from './divisa-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DivisaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DivisaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DivisaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Divisa} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DivisaDeleteDialog} />
  </>
);

export default Routes;
