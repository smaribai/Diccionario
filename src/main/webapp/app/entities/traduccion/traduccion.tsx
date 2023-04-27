import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITraduccion } from 'app/shared/model/traduccion.model';
import { getEntities } from './traduccion.reducer';

export const Traduccion = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const traduccionList = useAppSelector(state => state.traduccion.entities);
  const loading = useAppSelector(state => state.traduccion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="traduccion-heading" data-cy="TraduccionHeading">
        <Translate contentKey="diccionarioApp.traduccion.home.title">Traduccions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diccionarioApp.traduccion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/traduccion/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diccionarioApp.traduccion.home.createLabel">Create new Traduccion</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {traduccionList && traduccionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diccionarioApp.traduccion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.traduccion.textoOrigen">Texto Origen</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.traduccion.textoDestino">Texto Destino</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.traduccion.idiomaOrigen">Idioma Origen</Translate>
                </th>
                <th>
                  <Translate contentKey="diccionarioApp.traduccion.idiomaDestino">Idioma Destino</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {traduccionList.map((traduccion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/traduccion/${traduccion.id}`} color="link" size="sm">
                      {traduccion.id}
                    </Button>
                  </td>
                  <td>{traduccion.textoOrigen}</td>
                  <td>{traduccion.textoDestino}</td>
                  <td>
                    {traduccion.idiomaOrigen ? (
                      <Link to={`/idioma/${traduccion.idiomaOrigen.id}`}>{traduccion.idiomaOrigen.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {traduccion.idiomaDestino ? (
                      <Link to={`/idioma/${traduccion.idiomaDestino.id}`}>{traduccion.idiomaDestino.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/traduccion/${traduccion.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/traduccion/${traduccion.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/traduccion/${traduccion.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diccionarioApp.traduccion.home.notFound">No Traduccions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Traduccion;
