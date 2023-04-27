import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Clasificar from './clasificar';
import ClasificarDetail from './clasificar-detail';
import ClasificarUpdate from './clasificar-update';
import ClasificarDeleteDialog from './clasificar-delete-dialog';

const Routes = ({ match }) => (
  <>
    {/*<Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClasificarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClasificarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClasificarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Clasificar} />
    </Switch>
<ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClasificarDeleteDialog} />*/}
    <ErrorBoundaryRoute path={match.url} component={ClasificarUpdate} />
  </>
);

export default Routes;
