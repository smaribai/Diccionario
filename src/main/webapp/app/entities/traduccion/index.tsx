import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Traduccion from './traduccion';
import TraduccionDetail from './traduccion-detail';
import TraduccionUpdate from './traduccion-update';
import TraduccionDeleteDialog from './traduccion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TraduccionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TraduccionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TraduccionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Traduccion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TraduccionDeleteDialog} />
  </>
);

export default Routes;
