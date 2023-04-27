import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRemitente } from 'app/shared/model/remitente.model';
import { getEntities } from './remitente.reducer';

export const Remitente = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const remitenteList = useAppSelector(state => state.remitente.entities);
  const loading = useAppSelector(state => state.remitente.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="remitente-heading" data-cy="RemitenteHeading">
        <Translate contentKey="diccionarioApp.remitente.home.title">Remitentes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diccionarioApp.remitente.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/remitente/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diccionarioApp.remitente.home.createLabel">Create new Remitente</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {remitenteList && remitenteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diccionarioApp.remitente.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.remitente.idRemitente">Id Remitente</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.remitente.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.remitente.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.remitente.direccion">Direccion</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {remitenteList.map((remitente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/remitente/${remitente.id}`} color="link" size="sm">
                      {remitente.id}
                    </Button>
                  </td>
                  <td>{remitente.idRemitente}</td>
                  <td>{remitente.nombre}</td>
                  <td>{remitente.descripcion}</td>
                  <td>{remitente.direccion}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/remitente/${remitente.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/remitente/${remitente.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/remitente/${remitente.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diccionarioApp.remitente.home.notFound">No Remitentes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Remitente;
