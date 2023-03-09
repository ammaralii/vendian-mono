import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConfigurations } from 'app/shared/model/configurations.model';
import { getEntities } from './configurations.reducer';

export const Configurations = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const configurationsList = useAppSelector(state => state.configurations.entities);
  const loading = useAppSelector(state => state.configurations.loading);
  const totalItems = useAppSelector(state => state.configurations.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="configurations-heading" data-cy="ConfigurationsHeading">
        Configurations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/configurations/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Configurations
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {configurationsList && configurationsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('group')}>
                  Group <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('intValue')}>
                  Int Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stringValue')}>
                  String Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('doubleValue')}>
                  Double Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateValue')}>
                  Date Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jsonValue')}>
                  Json Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  Updated At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  Deleted At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  Version <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {configurationsList.map((configurations, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/configurations/${configurations.id}`} color="link" size="sm">
                      {configurations.id}
                    </Button>
                  </td>
                  <td>{configurations.name}</td>
                  <td>{configurations.group}</td>
                  <td>{configurations.intValue}</td>
                  <td>{configurations.stringValue}</td>
                  <td>{configurations.doubleValue}</td>
                  <td>
                    {configurations.dateValue ? <TextFormat type="date" value={configurations.dateValue} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{configurations.jsonValue}</td>
                  <td>
                    {configurations.createdAt ? <TextFormat type="date" value={configurations.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {configurations.updatedAt ? <TextFormat type="date" value={configurations.updatedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {configurations.deletedAt ? <TextFormat type="date" value={configurations.deletedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{configurations.version}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/configurations/${configurations.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/configurations/${configurations.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/configurations/${configurations.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Configurations found</div>
        )}
      </div>
      {totalItems ? (
        <div className={configurationsList && configurationsList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Configurations;
