import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInterviews } from 'app/shared/model/interviews.model';
import { getEntities } from './interviews.reducer';

export const Interviews = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const interviewsList = useAppSelector(state => state.interviews.entities);
  const loading = useAppSelector(state => state.interviews.loading);
  const totalItems = useAppSelector(state => state.interviews.totalItems);

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
      <h2 id="interviews-heading" data-cy="InterviewsHeading">
        Interviews
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/interviews/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Interviews
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {interviewsList && interviewsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('result')}>
                  Result <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientcomments')}>
                  Clientcomments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lmcomments')}>
                  Lmcomments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('scheduledat')}>
                  Scheduledat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deletedat')}>
                  Deletedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Track <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {interviewsList.map((interviews, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/interviews/${interviews.id}`} color="link" size="sm">
                      {interviews.id}
                    </Button>
                  </td>
                  <td>{interviews.result}</td>
                  <td>{interviews.clientcomments}</td>
                  <td>{interviews.lmcomments}</td>
                  <td>
                    {interviews.scheduledat ? <TextFormat type="date" value={interviews.scheduledat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{interviews.createdat ? <TextFormat type="date" value={interviews.createdat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{interviews.updatedat ? <TextFormat type="date" value={interviews.updatedat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{interviews.deletedat ? <TextFormat type="date" value={interviews.deletedat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{interviews.employee ? <Link to={`/employees/${interviews.employee.id}`}>{interviews.employee.id}</Link> : ''}</td>
                  <td>{interviews.project ? <Link to={`/projects/${interviews.project.id}`}>{interviews.project.id}</Link> : ''}</td>
                  <td>{interviews.track ? <Link to={`/tracks/${interviews.track.id}`}>{interviews.track.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/interviews/${interviews.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/interviews/${interviews.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/interviews/${interviews.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Interviews found</div>
        )}
      </div>
      {totalItems ? (
        <div className={interviewsList && interviewsList.length > 0 ? '' : 'd-none'}>
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

export default Interviews;
