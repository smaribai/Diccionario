import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Traduccion from './traduccion';
import Cliente from './cliente';
import Remitente from './remitente';
import Destinatario from './destinatario';
import Control from './control';
import Provincia from './provincia';
import Divisa from './divisa';
import Clasificaciones from './clasificaciones';
import Clasificar from './clasificar';
import Idioma from './idioma';
import Pais from './pais';
import Producto from './producto';
import ProductoControles from './producto-controles';
import TipoCliente from './tipo-cliente';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}traduccion`} component={Traduccion} />
        <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
        <ErrorBoundaryRoute path={`${match.url}remitente`} component={Remitente} />
        <ErrorBoundaryRoute path={`${match.url}destinatario`} component={Destinatario} />
        <ErrorBoundaryRoute path={`${match.url}control`} component={Control} />
        <ErrorBoundaryRoute path={`${match.url}provincia`} component={Provincia} />
        <ErrorBoundaryRoute path={`${match.url}divisa`} component={Divisa} />
        <ErrorBoundaryRoute path={`${match.url}clasificaciones`} component={Clasificaciones} />
        <ErrorBoundaryRoute path={`${match.url}clasificar`} component={Clasificar} />
        <ErrorBoundaryRoute path={`${match.url}idioma`} component={Idioma} />
        <ErrorBoundaryRoute path={`${match.url}pais`} component={Pais} />
        <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
        <ErrorBoundaryRoute path={`${match.url}producto-controles`} component={ProductoControles} />
        <ErrorBoundaryRoute path={`${match.url}tipo-cliente`} component={TipoCliente} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
