import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { getEntities } from './performance-cycle-employee-rating.reducer';

export const PerformanceCycleEmployeeRating = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const performanceCycleEmployeeRatingList = useAppSelector(state => state.performanceCycleEmployeeRating.entities);
  const loading = useAppSelector(state => state.performanceCycleEmployeeRating.loading);
  const totalItems = useAppSelector(state => state.performanceCycleEmployeeRating.totalItems);

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
      <h2 id="performance-cycle-employee-rating-heading" data-cy="PerformanceCycleEmployeeRatingHeading">
        Performance Cycle Employee Ratings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/performance-cycle-employee-rating/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Performance Cycle Employee Rating
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {performanceCycleEmployeeRatingList && performanceCycleEmployeeRatingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  Comment <FontAwesomeIcon icon="sort" />
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
                <th>
                  Performancecycle <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {performanceCycleEmployeeRatingList.map((performanceCycleEmployeeRating, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/performance-cycle-employee-rating/${performanceCycleEmployeeRating.id}`}
                      color="link"
                      size="sm"
                    >
                      {performanceCycleEmployeeRating.id}
                    </Button>
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.rating ? (
                      <div>
                        {performanceCycleEmployeeRating.ratingContentType ? (
                          <a onClick={openFile(performanceCycleEmployeeRating.ratingContentType, performanceCycleEmployeeRating.rating)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {performanceCycleEmployeeRating.ratingContentType}, {byteSize(performanceCycleEmployeeRating.rating)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.comment ? (
                      <div>
                        {performanceCycleEmployeeRating.commentContentType ? (
                          <a onClick={openFile(performanceCycleEmployeeRating.commentContentType, performanceCycleEmployeeRating.comment)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {performanceCycleEmployeeRating.commentContentType}, {byteSize(performanceCycleEmployeeRating.comment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.createdAt ? (
                      <TextFormat type="date" value={performanceCycleEmployeeRating.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.updatedAt ? (
                      <TextFormat type="date" value={performanceCycleEmployeeRating.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.deletedAt ? (
                      <TextFormat type="date" value={performanceCycleEmployeeRating.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{performanceCycleEmployeeRating.version}</td>
                  <td>
                    {performanceCycleEmployeeRating.performancecycle ? (
                      <Link to={`/hr-performance-cycles/${performanceCycleEmployeeRating.performancecycle.id}`}>
                        {performanceCycleEmployeeRating.performancecycle.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.employee ? (
                      <Link to={`/employees/${performanceCycleEmployeeRating.employee.id}`}>
                        {performanceCycleEmployeeRating.employee.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {performanceCycleEmployeeRating.manager ? (
                      <Link to={`/employees/${performanceCycleEmployeeRating.manager.id}`}>
                        {performanceCycleEmployeeRating.manager.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/performance-cycle-employee-rating/${performanceCycleEmployeeRating.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/performance-cycle-employee-rating/${performanceCycleEmployeeRating.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/performance-cycle-employee-rating/${performanceCycleEmployeeRating.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Performance Cycle Employee Ratings found</div>
        )}
      </div>
      {totalItems ? (
        <div className={performanceCycleEmployeeRatingList && performanceCycleEmployeeRatingList.length > 0 ? '' : 'd-none'}>
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

export default PerformanceCycleEmployeeRating;
