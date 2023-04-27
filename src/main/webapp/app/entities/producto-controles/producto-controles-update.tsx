import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IControl } from 'app/shared/model/control.model';
import { getEntities as getControls } from 'app/entities/control/control.reducer';
import { IProductoControles } from 'app/shared/model/producto-controles.model';
import { getEntity, updateEntity, createEntity, reset } from './producto-controles.reducer';

export const ProductoControlesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productos = useAppSelector(state => state.producto.entities);
  const controls = useAppSelector(state => state.control.entities);
  const productoControlesEntity = useAppSelector(state => state.productoControles.entity);
  const loading = useAppSelector(state => state.productoControles.loading);
  const updating = useAppSelector(state => state.productoControles.updating);
  const updateSuccess = useAppSelector(state => state.productoControles.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto-controles');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductos({}));
    dispatch(getControls({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productoControlesEntity,
      ...values,
      codigoArancelario: productos.find(it => it.id.toString() === values.codigoArancelario.toString()),
      idControl: controls.find(it => it.id.toString() === values.idControl.toString()),
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
          ...productoControlesEntity,
          codigoArancelario: productoControlesEntity?.codigoArancelario?.id,
          idControl: productoControlesEntity?.idControl?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioApp.productoControles.home.createOrEditLabel" data-cy="ProductoControlesCreateUpdateHeading">
            <Translate contentKey="diccionarioApp.productoControles.home.createOrEditLabel">Create or edit a ProductoControles</Translate>
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
                  id="producto-controles-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioApp.productoControles.descripcion')}
                id="producto-controles-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="producto-controles-codigoArancelario"
                name="codigoArancelario"
                data-cy="codigoArancelario"
                label={translate('diccionarioApp.productoControles.codigoArancelario')}
                type="select"
                required
              >
                <option value="" key="0" />
                {productos
                  ? productos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codigoArancelario}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="producto-controles-idControl"
                name="idControl"
                data-cy="idControl"
                label={translate('diccionarioApp.productoControles.idControl')}
                type="select"
                required
              >
                <option value="" key="0" />
                {controls
                  ? controls.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-controles" replace color="info">
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

export default ProductoControlesUpdate;
