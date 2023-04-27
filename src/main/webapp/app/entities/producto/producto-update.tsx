import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntity, updateEntity, createEntity, reset } from './producto.reducer';

export const ProductoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productos = useAppSelector(state => state.producto.entities);
  const productoEntity = useAppSelector(state => state.producto.entity);
  const loading = useAppSelector(state => state.producto.loading);
  const updating = useAppSelector(state => state.producto.updating);
  const updateSuccess = useAppSelector(state => state.producto.updateSuccess);
  const handleClose = () => {
    props.history.push('/producto');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productoEntity,
      ...values,
      parent: productos.find(it => it.id.toString() === values.parent.toString()),
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
          ...productoEntity,
          parent: productoEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioApp.producto.home.createOrEditLabel" data-cy="ProductoCreateUpdateHeading">
            <Translate contentKey="diccionarioApp.producto.home.createOrEditLabel">Create or edit a Producto</Translate>
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
                  id="producto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioApp.producto.codigoArancelario')}
                id="producto-codigoArancelario"
                name="codigoArancelario"
                data-cy="codigoArancelario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 20, message: translate('entity.validation.maxlength', { max: 20 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.producto.nivel')}
                id="producto-nivel"
                name="nivel"
                data-cy="nivel"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioApp.producto.cNCode')}
                id="producto-cNCode"
                name="cNCode"
                data-cy="cNCode"
                type="text"
                validate={{
                  maxLength: { value: 15, message: translate('entity.validation.maxlength', { max: 15 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioApp.producto.longitudCNCode')}
                id="producto-longitudCNCode"
                name="longitudCNCode"
                data-cy="longitudCNCode"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioApp.producto.descripcion')}
                id="producto-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 2000, message: translate('entity.validation.maxlength', { max: 2000 }) },
                }}
              />
              <ValidatedField
                id="producto-parent"
                name="parent"
                data-cy="parent"
                label={translate('diccionarioApp.producto.parent')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto" replace color="info">
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

export default ProductoUpdate;
