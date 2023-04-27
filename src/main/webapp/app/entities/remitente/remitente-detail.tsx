import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './remitente.reducer';

export const RemitenteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const remitenteEntity = useAppSelector(state => state.remitente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="remitenteDetailsHeading">
          <Translate contentKey="diccionarioApp.remitente.detail.title">Remitente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{remitenteEntity.id}</dd>
          <dt>
            <span id="idRemitente">
              <Translate contentKey="diccionarioApp.remitente.idRemitente">Id Remitente</Translate>
            </span>
          </dt>
          <dd>{remitenteEntity.idRemitente}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioApp.remitente.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{remitenteEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.remitente.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{remitenteEntity.descripcion}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="diccionarioApp.remitente.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{remitenteEntity.direccion}</dd>
        </dl>
        <Button tag={Link} to="/remitente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/remitente/${remitenteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RemitenteDetail;
