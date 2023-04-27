import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoCliente } from 'app/shared/model/tipo-cliente.model';
import { getEntities as getTipoClientes } from 'app/entities/tipo-cliente/tipo-cliente.reducer';
import { IPais } from 'app/shared/model/pais.model';
import { getEntities as getPais } from 'app/entities/pais/pais.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntity, updateEntity, createEntity, reset } from './cliente.reducer';

export const ClienteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tipoClientes = useAppSelector(state => state.tipoCliente.entities);
  const pais = useAppSelector(state => state.pais.entities);
  const clienteEntity = useAppSelector(state => state.cliente.entity);
  const loading = useAppSelector(state => state.cliente.loading);
  const updating = useAppSelector(state => state.cliente.updating);
  const updateSuccess = useAppSelector(state => state.cliente.updateSuccess);
  const handleClose = () => {
    props.history.push('/cliente' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTipoClientes({}));
    dispatch(getPais({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clienteEntity,
      ...values,
      tipoCliente: tipoClientes.find(it => it.id.toString() === values.tipoCliente.toString()),
      pais: pais.find(it => it.id.toString() === values.pais.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clienteEntity,
          tipoCliente: clienteEntity?.tipoCliente?.id,
          pais: clienteEntity?.pais?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioApp.cliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">
            <Translate contentKey="diccionarioApp.cliente.home.createOrEditLabel">Create or edit a Cliente</Translate>
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
                  id="cliente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioApp.cliente.idCliente')}
                id="cliente-idCliente"
                name="idCliente"
                data-cy="idCliente"
                type="text"
                validate={{
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 20 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.cliente.nombre')}
                id="cliente-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 70, message: translate('entity.validation.maxlength', { max: 70 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.cliente.descripcion')}
                id="cliente-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.cliente.direccion')}
                id="cliente-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="cliente-tipoCliente"
                name="tipoCliente"
                data-cy="tipoCliente"
                label={translate('diccionarioApp.cliente.tipoCliente')}
                type="select"
                required
              >
                <option value="" key="0" />
                {tipoClientes
                  ? tipoClientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="cliente-pais"
                name="pais"
                data-cy="pais"
                label={translate('diccionarioApp.cliente.pais')}
                type="select"
                required
              >
                <option value="" key="0" />
                {pais
                  ? pais.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codigoPais}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cliente" replace color="info">
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
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClienteUpdate;
