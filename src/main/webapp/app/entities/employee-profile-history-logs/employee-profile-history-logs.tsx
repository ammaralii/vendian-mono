import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeProfileHistoryLogs } from 'app/shared/model/employee-profile-history-logs.model';
import { getEntities } from './employee-profile-history-logs.reducer';

export const EmployeeProfileHistoryLogs = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeProfileHistoryLogsList = useAppSelector(state => state.employeeProfileHistoryLogs.entities);
  const loading = useAppSelector(state => state.employeeProfileHistoryLogs.loading);
  const totalItems = useAppSelector(state => state.employeeProfileHistoryLogs.totalItems);

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
      <h2 id="employee-profile-history-logs-heading" data-cy="EmployeeProfileHistoryLogsHeading">
        Employee Profile History Logs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/employee-profile-history-logs/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Profile History Logs
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeProfileHistoryLogsList && employeeProfileHistoryLogsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tablename')}>
                  Tablename <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rowid')}>
                  Rowid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventtype')}>
                  Eventtype <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fields')}>
                  Fields <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedbyid')}>
                  Updatedbyid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activityid')}>
                  Activityid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('category')}>
                  Category <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeProfileHistoryLogsList.map((employeeProfileHistoryLogs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-profile-history-logs/${employeeProfileHistoryLogs.id}`} color="link" size="sm">
                      {employeeProfileHistoryLogs.id}
                    </Button>
                  </td>
                  <td>{employeeProfileHistoryLogs.tablename}</td>
                  <td>{employeeProfileHistoryLogs.rowid}</td>
                  <td>{employeeProfileHistoryLogs.eventtype}</td>
                  <td>
                    {employeeProfileHistoryLogs.fields ? (
                      <div>
                        {employeeProfileHistoryLogs.fieldsContentType ? (
                          <a onClick={openFile(employeeProfileHistoryLogs.fieldsContentType, employeeProfileHistoryLogs.fields)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProfileHistoryLogs.fieldsContentType}, {byteSize(employeeProfileHistoryLogs.fields)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employeeProfileHistoryLogs.updatedbyid}</td>
                  <td>{employeeProfileHistoryLogs.activityid}</td>
                  <td>
                    {employeeProfileHistoryLogs.createdat ? (
                      <TextFormat type="date" value={employeeProfileHistoryLogs.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeProfileHistoryLogs.updatedat ? (
                      <TextFormat type="date" value={employeeProfileHistoryLogs.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeProfileHistoryLogs.category}</td>
                  <td>
                    {employeeProfileHistoryLogs.employee ? (
                      <Link to={`/employees/${employeeProfileHistoryLogs.employee.id}`}>{employeeProfileHistoryLogs.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-profile-history-logs/${employeeProfileHistoryLogs.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-profile-history-logs/${employeeProfileHistoryLogs.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-profile-history-logs/${employeeProfileHistoryLogs.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Profile History Logs found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeProfileHistoryLogsList && employeeProfileHistoryLogsList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeProfileHistoryLogs;
