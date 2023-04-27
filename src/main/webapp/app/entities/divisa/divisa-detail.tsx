import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './divisa.reducer';

export const DivisaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const divisaEntity = useAppSelector(state => state.divisa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="divisaDetailsHeading">
          <Translate contentKey="diccionarioApp.divisa.detail.title">Divisa</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{divisaEntity.id}</dd>
          <dt>
            <span id="codigoDivisa">
              <Translate contentKey="diccionarioApp.divisa.codigoDivisa">Codigo Divisa</Translate>
            </span>
          </dt>
          <dd>{divisaEntity.codigoDivisa}</dd>
          <dt>
            <span id="nombreDivisa">
              <Translate contentKey="diccionarioApp.divisa.nombreDivisa">Nombre Divisa</Translate>
            </span>
          </dt>
          <dd>{divisaEntity.nombreDivisa}</dd>
        </dl>
        <Button tag={Link} to="/divisa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/divisa/${divisaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DivisaDetail;
