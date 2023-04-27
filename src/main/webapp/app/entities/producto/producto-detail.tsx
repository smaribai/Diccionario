import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto.reducer';

export const ProductoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productoEntity = useAppSelector(state => state.producto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDetailsHeading">
          <Translate contentKey="diccionarioApp.producto.detail.title">Producto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productoEntity.id}</dd>
          <dt>
            <span id="codigoArancelario">
              <Translate contentKey="diccionarioApp.producto.codigoArancelario">Codigo Arancelario</Translate>
            </span>
          </dt>
          <dd>{productoEntity.codigoArancelario}</dd>
          <dt>
            <span id="nivel">
              <Translate contentKey="diccionarioApp.producto.nivel">Nivel</Translate>
            </span>
          </dt>
          <dd>{productoEntity.nivel}</dd>
          <dt>
            <span id="cNCode">
              <Translate contentKey="diccionarioApp.producto.cNCode">C N Code</Translate>
            </span>
          </dt>
          <dd>{productoEntity.cNCode}</dd>
          <dt>
            <span id="longitudCNCode">
              <Translate contentKey="diccionarioApp.producto.longitudCNCode">Longitud CN Code</Translate>
            </span>
          </dt>
          <dd>{productoEntity.longitudCNCode}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.producto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{productoEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.producto.parent">Parent</Translate>
          </dt>
          <dd>{productoEntity.parent ? productoEntity.parent.codigoArancelario : ''}</dd>
        </dl>
        <Button tag={Link} to="/producto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/producto/${productoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDetail;
