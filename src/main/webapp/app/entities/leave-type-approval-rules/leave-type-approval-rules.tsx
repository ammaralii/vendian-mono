import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveTypeApprovalRules } from 'app/shared/model/leave-type-approval-rules.model';
import { getEntities } from './leave-type-approval-rules.reducer';

export const LeaveTypeApprovalRules = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveTypeApprovalRulesList = useAppSelector(state => state.leaveTypeApprovalRules.entities);
  const loading = useAppSelector(state => state.leaveTypeApprovalRules.loading);
  const totalItems = useAppSelector(state => state.leaveTypeApprovalRules.totalItems);

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
      <h2 id="leave-type-approval-rules-heading" data-cy="LeaveTypeApprovalRulesHeading">
        Leave Type Approval Rules
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-type-approval-rules/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Type Approval Rules
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveTypeApprovalRulesList && leaveTypeApprovalRulesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
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
                <th className="hand" onClick={sort('deletedAt')}>
                  Deleted At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  Version <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave Approval Criteria <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave Type <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveTypeApprovalRulesList.map((leaveTypeApprovalRules, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-type-approval-rules/${leaveTypeApprovalRules.id}`} color="link" size="sm">
                      {leaveTypeApprovalRules.id}
                    </Button>
                  </td>
                  <td>
                    {leaveTypeApprovalRules.effDate ? (
                      <TextFormat type="date" value={leaveTypeApprovalRules.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeApprovalRules.createdAt ? (
                      <TextFormat type="date" value={leaveTypeApprovalRules.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeApprovalRules.updatedAt ? (
                      <TextFormat type="date" value={leaveTypeApprovalRules.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeApprovalRules.deletedAt ? (
                      <TextFormat type="date" value={leaveTypeApprovalRules.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveTypeApprovalRules.version}</td>
                  <td>
                    {leaveTypeApprovalRules.leaveApprovalCriteria ? (
                      <Link to={`/leave-approval-criterias/${leaveTypeApprovalRules.leaveApprovalCriteria.id}`}>
                        {leaveTypeApprovalRules.leaveApprovalCriteria.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveTypeApprovalRules.leaveType ? (
                      <Link to={`/leave-types/${leaveTypeApprovalRules.leaveType.id}`}>{leaveTypeApprovalRules.leaveType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-type-approval-rules/${leaveTypeApprovalRules.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-approval-rules/${leaveTypeApprovalRules.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-approval-rules/${leaveTypeApprovalRules.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Type Approval Rules found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveTypeApprovalRulesList && leaveTypeApprovalRulesList.length > 0 ? '' : 'd-none'}>
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

export default LeaveTypeApprovalRules;
