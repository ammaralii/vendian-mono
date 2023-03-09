import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFeedbackRequests } from 'app/shared/model/feedback-requests.model';
import { getEntities } from './feedback-requests.reducer';

export const FeedbackRequests = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const feedbackRequestsList = useAppSelector(state => state.feedbackRequests.entities);
  const loading = useAppSelector(state => state.feedbackRequests.loading);
  const totalItems = useAppSelector(state => state.feedbackRequests.totalItems);

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
      <h2 id="feedback-requests-heading" data-cy="FeedbackRequestsHeading">
        Feedback Requests
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/feedback-requests/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Feedback Requests
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {feedbackRequestsList && feedbackRequestsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isreportavailable')}>
                  Isreportavailable <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reportpath')}>
                  Reportpath <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedat')}>
                  Approvedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expiredat')}>
                  Expiredat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Linemanager <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {feedbackRequestsList.map((feedbackRequests, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/feedback-requests/${feedbackRequests.id}`} color="link" size="sm">
                      {feedbackRequests.id}
                    </Button>
                  </td>
                  <td>{feedbackRequests.status}</td>
                  <td>{feedbackRequests.isreportavailable ? 'true' : 'false'}</td>
                  <td>{feedbackRequests.reportpath}</td>
                  <td>
                    {feedbackRequests.approvedat ? (
                      <TextFormat type="date" value={feedbackRequests.approvedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {feedbackRequests.expiredat ? (
                      <TextFormat type="date" value={feedbackRequests.expiredat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {feedbackRequests.createdat ? (
                      <TextFormat type="date" value={feedbackRequests.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {feedbackRequests.updatedat ? (
                      <TextFormat type="date" value={feedbackRequests.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {feedbackRequests.employee ? (
                      <Link to={`/employees/${feedbackRequests.employee.id}`}>{feedbackRequests.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {feedbackRequests.linemanager ? (
                      <Link to={`/employees/${feedbackRequests.linemanager.id}`}>{feedbackRequests.linemanager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/feedback-requests/${feedbackRequests.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/feedback-requests/${feedbackRequests.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/feedback-requests/${feedbackRequests.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Feedback Requests found</div>
        )}
      </div>
      {totalItems ? (
        <div className={feedbackRequestsList && feedbackRequestsList.length > 0 ? '' : 'd-none'}>
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

export default FeedbackRequests;
