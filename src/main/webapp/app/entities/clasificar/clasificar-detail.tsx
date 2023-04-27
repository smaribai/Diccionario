import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './clasificar.reducer';

export const ClasificarDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clasificarEntity = useAppSelector(state => state.clasificar.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clasificarDetailsHeading">
          <Translate contentKey="diccionarioApp.clasificar.detail.title">Clasificar</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.id}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.clasificar.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.descripcion}</dd>
          <dt>
            <span id="cliente">
              <Translate contentKey="diccionarioApp.clasificar.cliente">Cliente</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.cliente}</dd>
          <dt>
            <span id="remitente">
              <Translate contentKey="diccionarioApp.clasificar.remitente">Remitente</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.remitente}</dd>
          <dt>
            <span id="destinatario">
              <Translate contentKey="diccionarioApp.clasificar.destinatario">Destinatario</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.destinatario}</dd>
          <dt>
            <span id="proveedor">
              <Translate contentKey="diccionarioApp.clasificar.proveedor">Proveedor</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.proveedor}</dd>
          <dt>
            <span id="codigoArancelarioOrigen">
              <Translate contentKey="diccionarioApp.clasificar.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.codigoArancelarioOrigen}</dd>
          <dt>
            <span id="importe">
              <Translate contentKey="diccionarioApp.clasificar.importe">Importe</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.importe}</dd>
          <dt>
            <span id="uds">
              <Translate contentKey="diccionarioApp.clasificar.uds">Uds</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.uds}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="diccionarioApp.clasificar.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{clasificarEntity.peso}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.paisOrigen">Pais Origen</Translate>
          </dt>
          <dd>{clasificarEntity.paisOrigen ? clasificarEntity.paisOrigen.nombrePais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.paisDestino">Pais Destino</Translate>
          </dt>
          <dd>{clasificarEntity.paisDestino ? clasificarEntity.paisDestino.nombrePais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.divisa">Divisa</Translate>
          </dt>
          <dd>{clasificarEntity.divisa ? clasificarEntity.divisa.nombreDivisa : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.idioma">Idioma</Translate>
          </dt>
          <dd>{clasificarEntity.idioma ? clasificarEntity.idioma.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.refCliente">Ref Cliente</Translate>
          </dt>
          <dd>{clasificarEntity.refCliente ? clasificarEntity.refCliente.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.provinciaDestino">Provincia Destino</Translate>
          </dt>
          <dd>{clasificarEntity.provinciaDestino ? clasificarEntity.provinciaDestino.nombreProvincia : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.idRemitente">Id Remitente</Translate>
          </dt>
          <dd>{clasificarEntity.idRemitente ? clasificarEntity.idRemitente.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificar.idDestinatario">Id Destinatario</Translate>
          </dt>
          <dd>{clasificarEntity.idDestinatario ? clasificarEntity.idDestinatario.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/clasificar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/clasificar/${clasificarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClasificarDetail;
