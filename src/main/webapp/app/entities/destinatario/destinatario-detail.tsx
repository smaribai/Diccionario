import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './destinatario.reducer';

export const DestinatarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const destinatarioEntity = useAppSelector(state => state.destinatario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="destinatarioDetailsHeading">
          <Translate contentKey="diccionarioApp.destinatario.detail.title">Destinatario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{destinatarioEntity.id}</dd>
          <dt>
            <span id="idDestinatario">
              <Translate contentKey="diccionarioApp.destinatario.idDestinatario">Id Destinatario</Translate>
            </span>
          </dt>
          <dd>{destinatarioEntity.idDestinatario}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioApp.destinatario.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{destinatarioEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.destinatario.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{destinatarioEntity.descripcion}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="diccionarioApp.destinatario.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{destinatarioEntity.direccion}</dd>
        </dl>
        <Button tag={Link} to="/destinatario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/destinatario/${destinatarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DestinatarioDetail;
