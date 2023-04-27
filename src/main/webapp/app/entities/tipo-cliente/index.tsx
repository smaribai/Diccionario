import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoCliente from './tipo-cliente';
import TipoClienteDetail from './tipo-cliente-detail';
import TipoClienteUpdate from './tipo-cliente-update';
import TipoClienteDeleteDialog from './tipo-cliente-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoClienteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoClienteDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoCliente} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoClienteDeleteDialog} />
  </>
);

export default Routes;
