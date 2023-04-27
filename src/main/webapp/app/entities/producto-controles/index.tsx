import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductoControles from './producto-controles';
import ProductoControlesDetail from './producto-controles-detail';
import ProductoControlesUpdate from './producto-controles-update';
import ProductoControlesDeleteDialog from './producto-controles-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductoControlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductoControlesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductoControlesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductoControles} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductoControlesDeleteDialog} />
  </>
);

export default Routes;
