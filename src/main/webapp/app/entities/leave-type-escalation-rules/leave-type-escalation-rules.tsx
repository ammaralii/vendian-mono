import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveTypeEscalationRules } from 'app/shared/model/leave-type-escalation-rules.model';
import { getEntities } from './leave-type-escalation-rules.reducer';

export const LeaveTypeEscalationRules = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveTypeEscalationRulesList = useAppSelector(state => state.leaveTypeEscalationRules.entities);
  const loading = useAppSelector(state => state.leaveTypeEscalationRules.loading);
  const totalItems = useAppSelector(state => state.leaveTypeEscalationRules.totalItems);

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
      <h2 id="leave-type-escalation-rules-heading" data-cy="LeaveTypeEscalationRulesHeading">
        Leave Type Escalation Rules
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-type-escalation-rules/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Type Escalation Rules
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveTypeEscalationRulesList && leaveTypeEscalationRulesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noOfDays')}>
                  No Of Days <FontAwesomeIcon icon="sort" />
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
                  Leave Escalation Criteria <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave Request Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave Type <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveTypeEscalationRulesList.map((leaveTypeEscalationRules, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-type-escalation-rules/${leaveTypeEscalationRules.id}`} color="link" size="sm">
                      {leaveTypeEscalationRules.id}
                    </Button>
                  </td>
                  <td>{leaveTypeEscalationRules.noOfDays}</td>
                  <td>
                    {leaveTypeEscalationRules.effDate ? (
                      <TextFormat type="date" value={leaveTypeEscalationRules.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeEscalationRules.createdAt ? (
                      <TextFormat type="date" value={leaveTypeEscalationRules.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeEscalationRules.updatedAt ? (
                      <TextFormat type="date" value={leaveTypeEscalationRules.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeEscalationRules.endDate ? (
                      <TextFormat type="date" value={leaveTypeEscalationRules.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveTypeEscalationRules.version}</td>
                  <td>
                    {leaveTypeEscalationRules.leaveEscalationCriteria ? (
                      <Link to={`/leave-escalation-criterias/${leaveTypeEscalationRules.leaveEscalationCriteria.id}`}>
                        {leaveTypeEscalationRules.leaveEscalationCriteria.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveTypeEscalationRules.leaveRequestStatus ? (
                      <Link to={`/leave-statuses/${leaveTypeEscalationRules.leaveRequestStatus.id}`}>
                        {leaveTypeEscalationRules.leaveRequestStatus.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveTypeEscalationRules.leaveType ? (
                      <Link to={`/leave-types/${leaveTypeEscalationRules.leaveType.id}`}>{leaveTypeEscalationRules.leaveType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-type-escalation-rules/${leaveTypeEscalationRules.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-escalation-rules/${leaveTypeEscalationRules.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-escalation-rules/${leaveTypeEscalationRules.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Type Escalation Rules found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveTypeEscalationRulesList && leaveTypeEscalationRulesList.length > 0 ? '' : 'd-none'}>
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

export default LeaveTypeEscalationRules;
