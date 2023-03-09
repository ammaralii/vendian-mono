import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveRequests } from 'app/shared/model/leave-requests.model';
import { getEntities } from './leave-requests.reducer';

export const LeaveRequests = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveRequestsList = useAppSelector(state => state.leaveRequests.entities);
  const loading = useAppSelector(state => state.leaveRequests.loading);
  const totalItems = useAppSelector(state => state.leaveRequests.totalItems);

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
      <h2 id="leave-requests-heading" data-cy="LeaveRequestsHeading">
        Leave Requests
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/leave-requests/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Requests
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveRequestsList && leaveRequestsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestStartDate')}>
                  Request Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestEndDate')}>
                  Request End Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isHalfDay')}>
                  Is Half Day <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statusDate')}>
                  Status Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  Comments <FontAwesomeIcon icon="sort" />
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
                  Leave Detail <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Parent Leave Request <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveRequestsList.map((leaveRequests, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-requests/${leaveRequests.id}`} color="link" size="sm">
                      {leaveRequests.id}
                    </Button>
                  </td>
                  <td>
                    {leaveRequests.createdAt ? <TextFormat type="date" value={leaveRequests.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {leaveRequests.requestStartDate ? (
                      <TextFormat type="date" value={leaveRequests.requestStartDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequests.requestEndDate ? (
                      <TextFormat type="date" value={leaveRequests.requestEndDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequests.isHalfDay ? 'true' : 'false'}</td>
                  <td>
                    {leaveRequests.statusDate ? <TextFormat type="date" value={leaveRequests.statusDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{leaveRequests.comments}</td>
                  <td>
                    {leaveRequests.updatedAt ? <TextFormat type="date" value={leaveRequests.updatedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {leaveRequests.deletedAt ? <TextFormat type="date" value={leaveRequests.deletedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{leaveRequests.version}</td>
                  <td>
                    {leaveRequests.leaveDetail ? (
                      <Link to={`/leave-details/${leaveRequests.leaveDetail.id}`}>{leaveRequests.leaveDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequests.leaveStatus ? (
                      <Link to={`/leave-statuses/${leaveRequests.leaveStatus.id}`}>{leaveRequests.leaveStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequests.parentLeaveRequest ? (
                      <Link to={`/leave-requests/${leaveRequests.parentLeaveRequest.id}`}>{leaveRequests.parentLeaveRequest.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/leave-requests/${leaveRequests.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-requests/${leaveRequests.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-requests/${leaveRequests.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Requests found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveRequestsList && leaveRequestsList.length > 0 ? '' : 'd-none'}>
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

export default LeaveRequests;
