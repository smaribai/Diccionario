import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Idioma from './idioma';
import IdiomaDetail from './idioma-detail';
import IdiomaUpdate from './idioma-update';
import IdiomaDeleteDialog from './idioma-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdiomaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdiomaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdiomaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Idioma} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IdiomaDeleteDialog} />
  </>
);

export default Routes;
