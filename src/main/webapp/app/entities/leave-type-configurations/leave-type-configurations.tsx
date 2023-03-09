import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveTypeConfigurations } from 'app/shared/model/leave-type-configurations.model';
import { getEntities } from './leave-type-configurations.reducer';

export const LeaveTypeConfigurations = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveTypeConfigurationsList = useAppSelector(state => state.leaveTypeConfigurations.entities);
  const loading = useAppSelector(state => state.leaveTypeConfigurations.loading);
  const totalItems = useAppSelector(state => state.leaveTypeConfigurations.totalItems);

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
      <h2 id="leave-type-configurations-heading" data-cy="LeaveTypeConfigurationsHeading">
        Leave Type Configurations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-type-configurations/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Type Configurations
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveTypeConfigurationsList && leaveTypeConfigurationsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noOfLeaves')}>
                  No Of Leaves <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenureCycle')}>
                  Tenure Cycle <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('to')}>
                  To <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('from')}>
                  From <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('inclusivity')}>
                  Inclusivity <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxQuota')}>
                  Max Quota <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isAccured')}>
                  Is Accured <FontAwesomeIcon icon="sort" />
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
                  Leave Type <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveTypeConfigurationsList.map((leaveTypeConfigurations, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-type-configurations/${leaveTypeConfigurations.id}`} color="link" size="sm">
                      {leaveTypeConfigurations.id}
                    </Button>
                  </td>
                  <td>{leaveTypeConfigurations.noOfLeaves}</td>
                  <td>{leaveTypeConfigurations.tenureCycle}</td>
                  <td>{leaveTypeConfigurations.to}</td>
                  <td>{leaveTypeConfigurations.from}</td>
                  <td>{leaveTypeConfigurations.inclusivity}</td>
                  <td>{leaveTypeConfigurations.maxQuota}</td>
                  <td>{leaveTypeConfigurations.isAccured ? 'true' : 'false'}</td>
                  <td>
                    {leaveTypeConfigurations.effDate ? (
                      <TextFormat type="date" value={leaveTypeConfigurations.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeConfigurations.createdAt ? (
                      <TextFormat type="date" value={leaveTypeConfigurations.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeConfigurations.updatedAt ? (
                      <TextFormat type="date" value={leaveTypeConfigurations.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveTypeConfigurations.endDate ? (
                      <TextFormat type="date" value={leaveTypeConfigurations.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveTypeConfigurations.version}</td>
                  <td>
                    {leaveTypeConfigurations.leaveType ? (
                      <Link to={`/leave-types/${leaveTypeConfigurations.leaveType.id}`}>{leaveTypeConfigurations.leaveType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-type-configurations/${leaveTypeConfigurations.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-configurations/${leaveTypeConfigurations.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-type-configurations/${leaveTypeConfigurations.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Type Configurations found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveTypeConfigurationsList && leaveTypeConfigurationsList.length > 0 ? '' : 'd-none'}>
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

export default LeaveTypeConfigurations;
