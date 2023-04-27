import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClasificaciones } from 'app/shared/model/clasificaciones.model';
import { getEntities, reset } from './clasificaciones.reducer';

export const Clasificaciones = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const clasificacionesList = useAppSelector(state => state.clasificaciones.entities);
  const loading = useAppSelector(state => state.clasificaciones.loading);
  const totalItems = useAppSelector(state => state.clasificaciones.totalItems);
  const links = useAppSelector(state => state.clasificaciones.links);
  const entity = useAppSelector(state => state.clasificaciones.entity);
  const updateSuccess = useAppSelector(state => state.clasificaciones.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="clasificaciones-heading" data-cy="ClasificacionesHeading">
        <Translate contentKey="diccionarioApp.clasificaciones.home.title">Clasificaciones</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diccionarioApp.clasificaciones.home.refreshListLabel">Refresh List</Translate>
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={clasificacionesList ? clasificacionesList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {clasificacionesList && clasificacionesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.descripcion">Descripcion</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cliente')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.cliente">Cliente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remitente')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.remitente">Remitente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('destinatario')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.destinatario">Destinatario</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('proveedor')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.proveedor">Proveedor</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoArancelarioOrigen')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('importe')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.importe">Importe</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('uds')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.uds">Uds</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('peso')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.peso">Peso</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoArancelarioObtenido')}>
                    <Translate contentKey="diccionarioApp.clasificaciones.codigoArancelarioObtenido">Codigo Arancelario Obtenido</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.paisOrigen">Pais Origen</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.paisDestino">Pais Destino</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.divisa">Divisa</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.idioma">Idioma</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.refCliente">Ref Cliente</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.provinciaDestino">Provincia Destino</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.idRemitente">Id Remitente</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioApp.clasificaciones.idDestinatario">Id Destinatario</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {clasificacionesList.map((clasificaciones, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/clasificaciones/${clasificaciones.id}`} color="link" size="sm">
                        {clasificaciones.id}
                      </Button>
                    </td>
                    <td>{clasificaciones.descripcion}</td>
                    <td>{clasificaciones.cliente}</td>
                    <td>{clasificaciones.remitente}</td>
                    <td>{clasificaciones.destinatario}</td>
                    <td>{clasificaciones.proveedor}</td>
                    <td>{clasificaciones.codigoArancelarioOrigen}</td>
                    <td>{clasificaciones.importe}</td>
                    <td>{clasificaciones.uds}</td>
                    <td>{clasificaciones.peso}</td>
                    <td>{clasificaciones.codigoArancelarioObtenido}</td>
                    <td>
                      {clasificaciones.paisOrigen ? (
                        <Link to={`/pais/${clasificaciones.paisOrigen.id}`}>{clasificaciones.paisOrigen.nombrePais}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.paisDestino ? (
                        <Link to={`/pais/${clasificaciones.paisDestino.id}`}>{clasificaciones.paisDestino.nombrePais}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.divisa ? (
                        <Link to={`/divisa/${clasificaciones.divisa.id}`}>{clasificaciones.divisa.nombreDivisa}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.idioma ? (
                        <Link to={`/idioma/${clasificaciones.idioma.id}`}>{clasificaciones.idioma.nombre}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.refCliente ? (
                        <Link to={`/cliente/${clasificaciones.refCliente.id}`}>{clasificaciones.refCliente.nombre}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.provinciaDestino ? (
                        <Link to={`/provincia/${clasificaciones.provinciaDestino.id}`}>
                          {clasificaciones.provinciaDestino.nombreProvincia}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.idRemitente ? (
                        <Link to={`/remitente/${clasificaciones.idRemitente.id}`}>{clasificaciones.idRemitente.nombre}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {clasificaciones.idDestinatario ? (
                        <Link to={`/destinatario/${clasificaciones.idDestinatario.id}`}>{clasificaciones.idDestinatario.nombre}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/clasificaciones/${clasificaciones.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="diccionarioApp.clasificaciones.home.notFound">No Clasificaciones found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Clasificaciones;
