import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveRequestApproverFlows } from 'app/shared/model/leave-request-approver-flows.model';
import { getEntities } from './leave-request-approver-flows.reducer';

export const LeaveRequestApproverFlows = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveRequestApproverFlowsList = useAppSelector(state => state.leaveRequestApproverFlows.entities);
  const loading = useAppSelector(state => state.leaveRequestApproverFlows.loading);
  const totalItems = useAppSelector(state => state.leaveRequestApproverFlows.totalItems);

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
      <h2 id="leave-request-approver-flows-heading" data-cy="LeaveRequestApproverFlowsHeading">
        Leave Request Approver Flows
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-request-approver-flows/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Request Approver Flows
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveRequestApproverFlowsList && leaveRequestApproverFlowsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvals')}>
                  Approvals <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('effDate')}>
                  Eff Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  Updated At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  End Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  Version <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Approver Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Current Leave Request Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Next Leave Request Status <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveRequestApproverFlowsList.map((leaveRequestApproverFlows, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-request-approver-flows/${leaveRequestApproverFlows.id}`} color="link" size="sm">
                      {leaveRequestApproverFlows.id}
                    </Button>
                  </td>
                  <td>{leaveRequestApproverFlows.approvals}</td>
                  <td>
                    {leaveRequestApproverFlows.effDate ? (
                      <TextFormat type="date" value={leaveRequestApproverFlows.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequestApproverFlows.createdAt ? (
                      <TextFormat type="date" value={leaveRequestApproverFlows.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequestApproverFlows.updatedAt ? (
                      <TextFormat type="date" value={leaveRequestApproverFlows.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequestApproverFlows.endDate ? (
                      <TextFormat type="date" value={leaveRequestApproverFlows.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequestApproverFlows.version}</td>
                  <td>
                    {leaveRequestApproverFlows.approverStatus ? (
                      <Link to={`/leave-statuses/${leaveRequestApproverFlows.approverStatus.id}`}>
                        {leaveRequestApproverFlows.approverStatus.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequestApproverFlows.currentLeaveRequestStatus ? (
                      <Link to={`/leave-statuses/${leaveRequestApproverFlows.currentLeaveRequestStatus.id}`}>
                        {leaveRequestApproverFlows.currentLeaveRequestStatus.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequestApproverFlows.nextLeaveRequestStatus ? (
                      <Link to={`/leave-statuses/${leaveRequestApproverFlows.nextLeaveRequestStatus.id}`}>
                        {leaveRequestApproverFlows.nextLeaveRequestStatus.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-request-approver-flows/${leaveRequestApproverFlows.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-request-approver-flows/${leaveRequestApproverFlows.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-request-approver-flows/${leaveRequestApproverFlows.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Request Approver Flows found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveRequestApproverFlowsList && leaveRequestApproverFlowsList.length > 0 ? '' : 'd-none'}>
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

export default LeaveRequestApproverFlows;
