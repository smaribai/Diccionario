import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Clasificaciones from './clasificaciones';
import ClasificacionesDetail from './clasificaciones-detail';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClasificacionesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Clasificaciones} />
    </Switch>
  </>
);

export default Routes;
