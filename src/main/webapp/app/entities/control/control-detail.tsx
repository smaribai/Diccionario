import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './control.reducer';

export const ControlDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const controlEntity = useAppSelector(state => state.control.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="controlDetailsHeading">
          <Translate contentKey="diccionarioApp.control.detail.title">Control</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{controlEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioApp.control.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{controlEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.control.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{controlEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/control" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/control/${controlEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ControlDetail;
