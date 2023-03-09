import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';
import { getEntities } from './performance-cycles.reducer';

export const PerformanceCycles = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const performanceCyclesList = useAppSelector(state => state.performanceCycles.entities);
  const loading = useAppSelector(state => state.performanceCycles.loading);
  const totalItems = useAppSelector(state => state.performanceCycles.totalItems);

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
      <h2 id="performance-cycles-heading" data-cy="PerformanceCyclesHeading">
        Performance Cycles
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/performance-cycles/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Performance Cycles
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {performanceCyclesList && performanceCyclesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('month')}>
                  Month <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('year')}>
                  Year <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalresources')}>
                  Totalresources <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmreviewed')}>
                  Pmreviewed <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archreviewed')}>
                  Archreviewed <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startdate')}>
                  Startdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isactive')}>
                  Isactive <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('projectcount')}>
                  Projectcount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('criteria')}>
                  Criteria <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notificationsent')}>
                  Notificationsent <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('duedate')}>
                  Duedate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('initiationdate')}>
                  Initiationdate <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {performanceCyclesList.map((performanceCycles, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/performance-cycles/${performanceCycles.id}`} color="link" size="sm">
                      {performanceCycles.id}
                    </Button>
                  </td>
                  <td>{performanceCycles.month ? 'true' : 'false'}</td>
                  <td>{performanceCycles.year ? 'true' : 'false'}</td>
                  <td>{performanceCycles.totalresources ? 'true' : 'false'}</td>
                  <td>{performanceCycles.pmreviewed ? 'true' : 'false'}</td>
                  <td>{performanceCycles.archreviewed ? 'true' : 'false'}</td>
                  <td>
                    {performanceCycles.startdate ? (
                      <TextFormat type="date" value={performanceCycles.startdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {performanceCycles.enddate ? (
                      <TextFormat type="date" value={performanceCycles.enddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{performanceCycles.isactive ? 'true' : 'false'}</td>
                  <td>
                    {performanceCycles.createdat ? (
                      <TextFormat type="date" value={performanceCycles.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {performanceCycles.updatedat ? (
                      <TextFormat type="date" value={performanceCycles.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{performanceCycles.projectcount ? 'true' : 'false'}</td>
                  <td>{performanceCycles.criteria}</td>
                  <td>{performanceCycles.notificationsent ? 'true' : 'false'}</td>
                  <td>
                    {performanceCycles.duedate ? (
                      <TextFormat type="date" value={performanceCycles.duedate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {performanceCycles.initiationdate ? (
                      <TextFormat type="date" value={performanceCycles.initiationdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/performance-cycles/${performanceCycles.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/performance-cycles/${performanceCycles.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/performance-cycles/${performanceCycles.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Performance Cycles found</div>
        )}
      </div>
      {totalItems ? (
        <div className={performanceCyclesList && performanceCyclesList.length > 0 ? '' : 'd-none'}>
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

export default PerformanceCycles;
