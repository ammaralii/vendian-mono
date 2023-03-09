import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserAttributeEscalationRules } from 'app/shared/model/user-attribute-escalation-rules.model';
import { getEntities } from './user-attribute-escalation-rules.reducer';

export const UserAttributeEscalationRules = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const userAttributeEscalationRulesList = useAppSelector(state => state.userAttributeEscalationRules.entities);
  const loading = useAppSelector(state => state.userAttributeEscalationRules.loading);
  const totalItems = useAppSelector(state => state.userAttributeEscalationRules.totalItems);

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
      <h2 id="user-attribute-escalation-rules-heading" data-cy="UserAttributeEscalationRulesHeading">
        User Attribute Escalation Rules
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/user-attribute-escalation-rules/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new User Attribute Escalation Rules
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userAttributeEscalationRulesList && userAttributeEscalationRulesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leaveEscalationCriteriaId')}>
                  Leave Escalation Criteria Id <FontAwesomeIcon icon="sort" />
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
                  Attribute <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Approver Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leaveescalationcriteria <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userAttributeEscalationRulesList.map((userAttributeEscalationRules, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-attribute-escalation-rules/${userAttributeEscalationRules.id}`} color="link" size="sm">
                      {userAttributeEscalationRules.id}
                    </Button>
                  </td>
                  <td>{userAttributeEscalationRules.leaveEscalationCriteriaId}</td>
                  <td>{userAttributeEscalationRules.noOfDays}</td>
                  <td>
                    {userAttributeEscalationRules.effDate ? (
                      <TextFormat type="date" value={userAttributeEscalationRules.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userAttributeEscalationRules.createdAt ? (
                      <TextFormat type="date" value={userAttributeEscalationRules.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userAttributeEscalationRules.updatedAt ? (
                      <TextFormat type="date" value={userAttributeEscalationRules.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userAttributeEscalationRules.endDate ? (
                      <TextFormat type="date" value={userAttributeEscalationRules.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userAttributeEscalationRules.version}</td>
                  <td>
                    {userAttributeEscalationRules.attribute ? (
                      <Link to={`/attributes/${userAttributeEscalationRules.attribute.id}`}>
                        {userAttributeEscalationRules.attribute.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userAttributeEscalationRules.approverStatus ? (
                      <Link to={`/leave-statuses/${userAttributeEscalationRules.approverStatus.id}`}>
                        {userAttributeEscalationRules.approverStatus.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userAttributeEscalationRules.leaveescalationcriteria ? (
                      <Link to={`/leave-escalation-criterias/${userAttributeEscalationRules.leaveescalationcriteria.id}`}>
                        {userAttributeEscalationRules.leaveescalationcriteria.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/user-attribute-escalation-rules/${userAttributeEscalationRules.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-attribute-escalation-rules/${userAttributeEscalationRules.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-attribute-escalation-rules/${userAttributeEscalationRules.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No User Attribute Escalation Rules found</div>
        )}
      </div>
      {totalItems ? (
        <div className={userAttributeEscalationRulesList && userAttributeEscalationRulesList.length > 0 ? '' : 'd-none'}>
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

export default UserAttributeEscalationRules;
