import React, { useState, useEffect, FormEvent } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPais } from 'app/shared/model/pais.model';
import { getEntities as getPais } from 'app/entities/pais/pais.reducer';
import { IDivisa } from 'app/shared/model/divisa.model';
import { getEntities as getDivisas } from 'app/entities/divisa/divisa.reducer';
import { IIdioma } from 'app/shared/model/idioma.model';
import { getEntities as getIdiomas } from 'app/entities/idioma/idioma.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IProvincia } from 'app/shared/model/provincia.model';
import { getEntities as getProvincias } from 'app/entities/provincia/provincia.reducer';
import { IRemitente } from 'app/shared/model/remitente.model';
import { getEntities as getRemitentes } from 'app/entities/remitente/remitente.reducer';
import { IDestinatario } from 'app/shared/model/destinatario.model';
import { getEntities as getDestinatarios } from 'app/entities/destinatario/destinatario.reducer';
import { IClasificar } from 'app/shared/model/clasificar.model';
import { getEntity, updateEntity, createEntity, reset, getClasificacion } from './clasificar.reducer';
import { unwrapResult } from '@reduxjs/toolkit/dist/createAsyncThunk';

export const ClasificarUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  //const [isNew] = useState(!props.match.params || !props.match.params.id);
  const isNew = true;

  const pais = useAppSelector(state => state.pais.entities);
  const divisas = useAppSelector(state => state.divisa.entities);
  const idiomas = useAppSelector(state => state.idioma.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const provincias = useAppSelector(state => state.provincia.entities);
  const remitentes = useAppSelector(state => state.remitente.entities);
  const destinatarios = useAppSelector(state => state.destinatario.entities);
  const clasificarEntity = useAppSelector(state => state.clasificar.entity);
  const loading = useAppSelector(state => state.clasificar.loading);
  const updating = useAppSelector(state => state.clasificar.updating);
  const updateSuccess = useAppSelector(state => state.clasificar.updateSuccess);
  const [respuestaApi, setRespuestaApi] = useState('');
  const PanelTexto = ({ texto }) => {
    return <textarea readOnly value={texto} style={{ width: '100%', height: '300px' }}></textarea>;
  };

  const handleClose = () => {
    props.history.push('/');
  };

  useEffect(() => {
    dispatch(reset());
    dispatch(getPais({ page: 1, size: 50, sort: 'nombrePais,asc' }));
    dispatch(getDivisas({}));
    dispatch(getIdiomas({}));
    dispatch(getClientes({}));
    dispatch(getProvincias({}));
    dispatch(getRemitentes({}));
    dispatch(getDestinatarios({}));
  }, []);

  {
    /*  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);
  */
  }

  const saveEntity = async values => {
    const entity = {
      ...clasificarEntity,
      ...values,
      paisOrigen: pais.find(it => it.id.toString() === values.paisOrigen.toString()),
      paisDestino: pais.find(it => it.id.toString() === values.paisDestino.toString()),
      divisa: divisas.find(it => it.id.toString() === values.divisa.toString()),
      idioma: idiomas.find(it => it.id.toString() === values.idioma.toString()),
      refCliente: clientes.find(it => it.id.toString() === values.refCliente.toString()),
      provinciaDestino: provincias.find(it => it.id.toString() === values.provinciaDestino.toString()),
      idRemitente: remitentes.find(it => it.id.toString() === values.idRemitente.toString()),
      idDestinatario: destinatarios.find(it => it.id.toString() === values.idDestinatario.toString()),
    };

    const respuesta = await dispatch(getClasificacion(entity));
    const payload = JSON.stringify(respuesta.payload);
    const dataJson = JSON.parse(payload);
    const data = dataJson.data;

    setRespuestaApi(JSON.stringify(data));
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clasificarEntity,
          paisOrigen: clasificarEntity?.paisOrigen?.id,
          paisDestino: clasificarEntity?.paisDestino?.id,
          divisa: clasificarEntity?.divisa?.id,
          idioma: clasificarEntity?.idioma?.id,
          refCliente: clasificarEntity?.refCliente?.id,
          provinciaDestino: clasificarEntity?.provinciaDestino?.id,
          idRemitente: clasificarEntity?.idRemitente?.id,
          idDestinatario: clasificarEntity?.idDestinatario?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioApp.clasificar.home.createOrEditLabel" data-cy="ClasificarCreateUpdateHeading">
            <Translate contentKey="diccionarioApp.clasificar.home.createOrEditLabel">Create or edit a Clasificar</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="clasificar-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioApp.clasificar.descripcion')}
                id="clasificar-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 250, message: translate('entity.validation.maxlength', { max: 250 }) },
                }}
              />
              <ValidatedField
                id="clasificar-paisOrigen"
                name="paisOrigen"
                data-cy="paisOrigen"
                label={translate('diccionarioApp.clasificar.paisOrigen')}
                type="select"
                required
              >
                <option value="" key="0" />
                {pais
                  ? pais.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombrePais}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="clasificar-paisDestino"
                name="paisDestino"
                data-cy="paisDestino"
                label={translate('diccionarioApp.clasificar.paisDestino')}
                type="select"
                required
              >
                <option value="" key="0" />
                {pais
                  ? pais.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombrePais}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('diccionarioApp.clasificar.peso')}
                id="clasificar-peso"
                name="peso"
                data-cy="peso"
                type="text"
              />
              <ValidatedField
                id="clasificar-divisa"
                name="divisa"
                data-cy="divisa"
                label={translate('diccionarioApp.clasificar.divisa')}
                type="select"
              >
                <option value="" key="0" />
                {divisas
                  ? divisas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreDivisa}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="clasificar-idioma"
                name="idioma"
                data-cy="idioma"
                label={translate('diccionarioApp.clasificar.idioma')}
                type="select"
              >
                <option value="" key="0" />
                {idiomas
                  ? idiomas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('diccionarioApp.clasificar.cliente')}
                id="clasificar-cliente"
                name="cliente"
                data-cy="cliente"
                type="text"
                validate={{
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.clasificar.remitente')}
                id="clasificar-remitente"
                name="remitente"
                data-cy="remitente"
                type="text"
                validate={{
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.clasificar.destinatario')}
                id="clasificar-destinatario"
                name="destinatario"
                data-cy="destinatario"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.clasificar.proveedor')}
                id="clasificar-proveedor"
                name="proveedor"
                data-cy="proveedor"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.clasificar.codigoArancelarioOrigen')}
                id="clasificar-codigoArancelarioOrigen"
                name="codigoArancelarioOrigen"
                data-cy="codigoArancelarioOrigen"
                type="text"
                validate={{
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 20 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.clasificar.importe')}
                id="clasificar-importe"
                name="importe"
                data-cy="importe"
                type="text"
              />
              <ValidatedField label={translate('diccionarioApp.clasificar.uds')} id="clasificar-uds" name="uds" data-cy="uds" type="text" />
              <ValidatedField
                id="clasificar-refCliente"
                name="refCliente"
                data-cy="refCliente"
                label={translate('diccionarioApp.clasificar.refCliente')}
                type="select"
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="clasificar-provinciaDestino"
                name="provinciaDestino"
                data-cy="provinciaDestino"
                label={translate('diccionarioApp.clasificar.provinciaDestino')}
                type="select"
              >
                <option value="" key="0" />
                {provincias
                  ? provincias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreProvincia}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="clasificar-idRemitente"
                name="idRemitente"
                data-cy="idRemitente"
                label={translate('diccionarioApp.clasificar.idRemitente')}
                type="select"
              >
                <option value="" key="0" />
                {remitentes
                  ? remitentes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="clasificar-idDestinatario"
                name="idDestinatario"
                data-cy="idDestinatario"
                label={translate('diccionarioApp.clasificar.idDestinatario')}
                type="select"
              >
                <option value="" key="0" />
                {destinatarios
                  ? destinatarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Calcular</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
        <Col md="8">
          <div id="respuesta">
            <PanelTexto texto={respuestaApi} />
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default ClasificarUpdate;
