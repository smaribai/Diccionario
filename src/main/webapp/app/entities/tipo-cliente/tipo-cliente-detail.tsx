import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-cliente.reducer';

export const TipoClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tipoClienteEntity = useAppSelector(state => state.tipoCliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoClienteDetailsHeading">
          <Translate contentKey="diccionarioApp.tipoCliente.detail.title">TipoCliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoClienteEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="diccionarioApp.tipoCliente.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{tipoClienteEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioApp.tipoCliente.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{tipoClienteEntity.nombre}</dd>
        </dl>
        <Button tag={Link} to="/tipo-cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-cliente/${tipoClienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoClienteDetail;
