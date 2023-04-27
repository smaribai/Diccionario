import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-controles.reducer';

export const ProductoControlesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoControlesEntity = useAppSelector(state => state.productoControles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoControlesDetailsHeading">
          <Translate contentKey="diccionarioApp.productoControles.detail.title">ProductoControles</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoControlesEntity.id}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.productoControles.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoControlesEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.productoControles.codigoArancelario">Codigo Arancelario</Translate>
          </dt>
          <dd>{productoControlesEntity.codigoArancelario ? productoControlesEntity.codigoArancelario.codigoArancelario : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.productoControles.idControl">Id Control</Translate>
          </dt>
          <dd>{productoControlesEntity.idControl ? productoControlesEntity.idControl.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto-controles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto-controles/${productoControlesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoControlesDetail;
