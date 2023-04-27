import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './clasificaciones.reducer';

export const ClasificacionesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clasificacionesEntity = useAppSelector(state => state.clasificaciones.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clasificacionesDetailsHeading">
          <Translate contentKey="diccionarioApp.clasificaciones.detail.title">Clasificaciones</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.id}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioApp.clasificaciones.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.descripcion}</dd>
          <dt>
            <span id="cliente">
              <Translate contentKey="diccionarioApp.clasificaciones.cliente">Cliente</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.cliente}</dd>
          <dt>
            <span id="remitente">
              <Translate contentKey="diccionarioApp.clasificaciones.remitente">Remitente</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.remitente}</dd>
          <dt>
            <span id="destinatario">
              <Translate contentKey="diccionarioApp.clasificaciones.destinatario">Destinatario</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.destinatario}</dd>
          <dt>
            <span id="proveedor">
              <Translate contentKey="diccionarioApp.clasificaciones.proveedor">Proveedor</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.proveedor}</dd>
          <dt>
            <span id="codigoArancelarioOrigen">
              <Translate contentKey="diccionarioApp.clasificaciones.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.codigoArancelarioOrigen}</dd>
          <dt>
            <span id="importe">
              <Translate contentKey="diccionarioApp.clasificaciones.importe">Importe</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.importe}</dd>
          <dt>
            <span id="uds">
              <Translate contentKey="diccionarioApp.clasificaciones.uds">Uds</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.uds}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="diccionarioApp.clasificaciones.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.peso}</dd>
          <dt>
            <span id="codigoArancelarioObtenido">
              <Translate contentKey="diccionarioApp.clasificaciones.codigoArancelarioObtenido">Codigo Arancelario Obtenido</Translate>
            </span>
          </dt>
          <dd>{clasificacionesEntity.codigoArancelarioObtenido}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.paisOrigen">Pais Origen</Translate>
          </dt>
          <dd>{clasificacionesEntity.paisOrigen ? clasificacionesEntity.paisOrigen.nombrePais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.paisDestino">Pais Destino</Translate>
          </dt>
          <dd>{clasificacionesEntity.paisDestino ? clasificacionesEntity.paisDestino.nombrePais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.divisa">Divisa</Translate>
          </dt>
          <dd>{clasificacionesEntity.divisa ? clasificacionesEntity.divisa.nombreDivisa : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.idioma">Idioma</Translate>
          </dt>
          <dd>{clasificacionesEntity.idioma ? clasificacionesEntity.idioma.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.refCliente">Ref Cliente</Translate>
          </dt>
          <dd>{clasificacionesEntity.refCliente ? clasificacionesEntity.refCliente.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.provinciaDestino">Provincia Destino</Translate>
          </dt>
          <dd>{clasificacionesEntity.provinciaDestino ? clasificacionesEntity.provinciaDestino.nombreProvincia : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.idRemitente">Id Remitente</Translate>
          </dt>
          <dd>{clasificacionesEntity.idRemitente ? clasificacionesEntity.idRemitente.nombre : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioApp.clasificaciones.idDestinatario">Id Destinatario</Translate>
          </dt>
          <dd>{clasificacionesEntity.idDestinatario ? clasificacionesEntity.idDestinatario.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/clasificaciones" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/clasificaciones/${clasificacionesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClasificacionesDetail;
