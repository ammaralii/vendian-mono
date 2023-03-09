import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveStatusWorkFlows } from 'app/shared/model/leave-status-work-flows.model';
import { getEntities } from './leave-status-work-flows.reducer';

export const LeaveStatusWorkFlows = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveStatusWorkFlowsList = useAppSelector(state => state.leaveStatusWorkFlows.entities);
  const loading = useAppSelector(state => state.leaveStatusWorkFlows.loading);
  const totalItems = useAppSelector(state => state.leaveStatusWorkFlows.totalItems);

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
      <h2 id="leave-status-work-flows-heading" data-cy="LeaveStatusWorkFlowsHeading">
        Leave Status Work Flows
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-status-work-flows/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Status Work Flows
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveStatusWorkFlowsList && leaveStatusWorkFlowsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ifApprovals')}>
                  If Approvals <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvalRequired')}>
                  Approval Required <FontAwesomeIcon icon="sort" />
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
                  Current Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Next Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Skip To Status <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveStatusWorkFlowsList.map((leaveStatusWorkFlows, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-status-work-flows/${leaveStatusWorkFlows.id}`} color="link" size="sm">
                      {leaveStatusWorkFlows.id}
                    </Button>
                  </td>
                  <td>{leaveStatusWorkFlows.ifApprovals ? 'true' : 'false'}</td>
                  <td>{leaveStatusWorkFlows.approvalRequired ? 'true' : 'false'}</td>
                  <td>
                    {leaveStatusWorkFlows.createdAt ? (
                      <TextFormat type="date" value={leaveStatusWorkFlows.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveStatusWorkFlows.updatedAt ? (
                      <TextFormat type="date" value={leaveStatusWorkFlows.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveStatusWorkFlows.deletedAt ? (
                      <TextFormat type="date" value={leaveStatusWorkFlows.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveStatusWorkFlows.version}</td>
                  <td>
                    {leaveStatusWorkFlows.currentStatus ? (
                      <Link to={`/leave-statuses/${leaveStatusWorkFlows.currentStatus.id}`}>{leaveStatusWorkFlows.currentStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveStatusWorkFlows.nextStatus ? (
                      <Link to={`/leave-statuses/${leaveStatusWorkFlows.nextStatus.id}`}>{leaveStatusWorkFlows.nextStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveStatusWorkFlows.skipToStatus ? (
                      <Link to={`/leave-statuses/${leaveStatusWorkFlows.skipToStatus.id}`}>{leaveStatusWorkFlows.skipToStatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-status-work-flows/${leaveStatusWorkFlows.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-status-work-flows/${leaveStatusWorkFlows.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-status-work-flows/${leaveStatusWorkFlows.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Status Work Flows found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveStatusWorkFlowsList && leaveStatusWorkFlowsList.length > 0 ? '' : 'd-none'}>
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

export default LeaveStatusWorkFlows;
