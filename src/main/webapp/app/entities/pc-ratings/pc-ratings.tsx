import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { getEntities } from './pc-ratings.reducer';

export const PcRatings = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pcRatingsList = useAppSelector(state => state.pcRatings.entities);
  const loading = useAppSelector(state => state.pcRatings.loading);
  const totalItems = useAppSelector(state => state.pcRatings.totalItems);

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
      <h2 id="pc-ratings-heading" data-cy="PcRatingsHeading">
        Pc Ratings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/pc-ratings/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pc Ratings
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pcRatingsList && pcRatingsList.length > 0 ? (
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
                <th className="hand" onClick={sort('stausDate')}>
                  Staus Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dueDate')}>
                  Due Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('freeze')}>
                  Freeze <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('includeInFinalRating')}>
                  Include In Final Rating <FontAwesomeIcon icon="sort" />
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
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Pcemployeerating <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pcRatingsList.map((pcRatings, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pc-ratings/${pcRatings.id}`} color="link" size="sm">
                      {pcRatings.id}
                    </Button>
                  </td>
                  <td>
                    {pcRatings.rating ? (
                      <div>
                        {pcRatings.ratingContentType ? (
                          <a onClick={openFile(pcRatings.ratingContentType, pcRatings.rating)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {pcRatings.ratingContentType}, {byteSize(pcRatings.rating)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {pcRatings.comment ? (
                      <div>
                        {pcRatings.commentContentType ? (
                          <a onClick={openFile(pcRatings.commentContentType, pcRatings.comment)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {pcRatings.commentContentType}, {byteSize(pcRatings.comment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{pcRatings.stausDate ? <TextFormat type="date" value={pcRatings.stausDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pcRatings.dueDate ? <TextFormat type="date" value={pcRatings.dueDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{pcRatings.freeze ? 'true' : 'false'}</td>
                  <td>{pcRatings.includeInFinalRating ? 'true' : 'false'}</td>
                  <td>{pcRatings.createdAt ? <TextFormat type="date" value={pcRatings.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pcRatings.updatedAt ? <TextFormat type="date" value={pcRatings.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pcRatings.deletedAt ? <TextFormat type="date" value={pcRatings.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pcRatings.version}</td>
                  <td>{pcRatings.status ? <Link to={`/pc-statuses/${pcRatings.status.id}`}>{pcRatings.status.id}</Link> : ''}</td>
                  <td>
                    {pcRatings.pcemployeerating ? (
                      <Link to={`/performance-cycle-employee-rating/${pcRatings.pcemployeerating.id}`}>
                        {pcRatings.pcemployeerating.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{pcRatings.employee ? <Link to={`/employees/${pcRatings.employee.id}`}>{pcRatings.employee.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pc-ratings/${pcRatings.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-ratings/${pcRatings.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-ratings/${pcRatings.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Pc Ratings found</div>
        )}
      </div>
      {totalItems ? (
        <div className={pcRatingsList && pcRatingsList.length > 0 ? '' : 'd-none'}>
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

export default PcRatings;
