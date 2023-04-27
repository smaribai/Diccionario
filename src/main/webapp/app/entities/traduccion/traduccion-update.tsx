import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIdioma } from 'app/shared/model/idioma.model';
import { getEntities as getIdiomas } from 'app/entities/idioma/idioma.reducer';
import { ITraduccion } from 'app/shared/model/traduccion.model';
import { getEntity, updateEntity, createEntity, reset } from './traduccion.reducer';

export const TraduccionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const idiomas = useAppSelector(state => state.idioma.entities);
  const traduccionEntity = useAppSelector(state => state.traduccion.entity);
  const loading = useAppSelector(state => state.traduccion.loading);
  const updating = useAppSelector(state => state.traduccion.updating);
  const updateSuccess = useAppSelector(state => state.traduccion.updateSuccess);
  const handleClose = () => {
    props.history.push('/traduccion');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getIdiomas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...traduccionEntity,
      ...values,
      idiomaOrigen: idiomas.find(it => it.id.toString() === values.idiomaOrigen.toString()),
      idiomaDestino: idiomas.find(it => it.id.toString() === values.idiomaDestino.toString()),
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
          ...traduccionEntity,
          idiomaOrigen: traduccionEntity?.idiomaOrigen?.id,
          idiomaDestino: traduccionEntity?.idiomaDestino?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioApp.traduccion.home.createOrEditLabel" data-cy="TraduccionCreateUpdateHeading">
            <Translate contentKey="diccionarioApp.traduccion.home.createOrEditLabel">Create or edit a Traduccion</Translate>
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
                  id="traduccion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioApp.traduccion.textoOrigen')}
                id="traduccion-textoOrigen"
                name="textoOrigen"
                data-cy="textoOrigen"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.traduccion.textoDestino')}
                id="traduccion-textoDestino"
                name="textoDestino"
                data-cy="textoDestino"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                id="traduccion-idiomaOrigen"
                name="idiomaOrigen"
                data-cy="idiomaOrigen"
                label={translate('diccionarioApp.traduccion.idiomaOrigen')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="traduccion-idiomaDestino"
                name="idiomaDestino"
                data-cy="idiomaDestino"
                label={translate('diccionarioApp.traduccion.idiomaDestino')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/traduccion" replace color="info">
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

export default TraduccionUpdate;
