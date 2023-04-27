import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClasificar } from 'app/shared/model/clasificar.model';
import { getEntities } from './clasificar.reducer';

export const Clasificar = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const clasificarList = useAppSelector(state => state.clasificar.entities);
  const loading = useAppSelector(state => state.clasificar.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="clasificar-heading" data-cy="ClasificarHeading">
        <Translate contentKey="diccionarioApp.clasificar.home.title">Clasificars</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diccionarioApp.clasificar.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/clasificar/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diccionarioApp.clasificar.home.createLabel">Create new Clasificar</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clasificarList && clasificarList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.cliente">Cliente</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.remitente">Remitente</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.destinatario">Destinatario</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.proveedor">Proveedor</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.importe">Importe</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.uds">Uds</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.peso">Peso</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.paisOrigen">Pais Origen</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.paisDestino">Pais Destino</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.divisa">Divisa</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.idioma">Idioma</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.refCliente">Ref Cliente</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.provinciaDestino">Provincia Destino</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.idRemitente">Id Remitente</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.clasificar.idDestinatario">Id Destinatario</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clasificarList.map((clasificar, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/clasificar/${clasificar.id}`} color="link" size="sm">
                      {clasificar.id}
                    </Button>
                  </td>
                  <td>{clasificar.descripcion}</td>
                  <td>{clasificar.cliente}</td>
                  <td>{clasificar.remitente}</td>
                  <td>{clasificar.destinatario}</td>
                  <td>{clasificar.proveedor}</td>
                  <td>{clasificar.codigoArancelarioOrigen}</td>
                  <td>{clasificar.importe}</td>
                  <td>{clasificar.uds}</td>
                  <td>{clasificar.peso}</td>
                  <td>
                    {clasificar.paisOrigen ? <Link to={`/pais/${clasificar.paisOrigen.id}`}>{clasificar.paisOrigen.nombrePais}</Link> : ''}
                  </td>
                  <td>
                    {clasificar.paisDestino ? (
                      <Link to={`/pais/${clasificar.paisDestino.id}`}>{clasificar.paisDestino.nombrePais}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{clasificar.divisa ? <Link to={`/divisa/${clasificar.divisa.id}`}>{clasificar.divisa.nombreDivisa}</Link> : ''}</td>
                  <td>{clasificar.idioma ? <Link to={`/idioma/${clasificar.idioma.id}`}>{clasificar.idioma.nombre}</Link> : ''}</td>
                  <td>
                    {clasificar.refCliente ? <Link to={`/cliente/${clasificar.refCliente.id}`}>{clasificar.refCliente.nombre}</Link> : ''}
                  </td>
                  <td>
                    {clasificar.provinciaDestino ? (
                      <Link to={`/provincia/${clasificar.provinciaDestino.id}`}>{clasificar.provinciaDestino.nombreProvincia}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {clasificar.idRemitente ? (
                      <Link to={`/remitente/${clasificar.idRemitente.id}`}>{clasificar.idRemitente.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {clasificar.idDestinatario ? (
                      <Link to={`/destinatario/${clasificar.idDestinatario.id}`}>{clasificar.idDestinatario.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/clasificar/${clasificar.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/clasificar/${clasificar.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/clasificar/${clasificar.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="diccionarioApp.clasificar.home.notFound">No Clasificars found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Clasificar;
