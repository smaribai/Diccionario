import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './traduccion.reducer';

export const TraduccionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const traduccionEntity = useAppSelector(state => state.traduccion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="traduccionDetailsHeading">
          <Translate contentKey="diccionarioApp.traduccion.detail.title">Traduccion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{traduccionEntity.id}</dd>
          <dt>
            <span id="textoOrigen">
              <Translate contentKey="diccionarioApp.traduccion.textoOrigen">Texto Origen</Translate>
            </span>
          </dt>
          <dd>{traduccionEntity.textoOrigen}</dd>
          <dt>
            <span id="textoDestino">
              <Translate contentKey="diccionarioApp.traduccion.textoDestino">Texto Destino</Translate>
            </span>
          </dt>
          <dd>{traduccionEntity.textoDestino}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.traduccion.idiomaOrigen">Idioma Origen</Translate>
          </dt>
          <dd>{traduccionEntity.idiomaOrigen ? traduccionEntity.idiomaOrigen.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.traduccion.idiomaDestino">Idioma Destino</Translate>
          </dt>
          <dd>{traduccionEntity.idiomaDestino ? traduccionEntity.idiomaDestino.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/traduccion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/traduccion/${traduccionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TraduccionDetail;
