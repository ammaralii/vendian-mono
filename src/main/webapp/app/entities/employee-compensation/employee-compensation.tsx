import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeCompensation } from 'app/shared/model/employee-compensation.model';
import { getEntities } from './employee-compensation.reducer';

export const EmployeeCompensation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeCompensationList = useAppSelector(state => state.employeeCompensation.entities);
  const loading = useAppSelector(state => state.employeeCompensation.loading);
  const totalItems = useAppSelector(state => state.employeeCompensation.totalItems);

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
      <h2 id="employee-compensation-heading" data-cy="EmployeeCompensationHeading">
        Employee Compensations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/employee-compensation/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Compensation
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeCompensationList && employeeCompensationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  Amount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('date')}>
                  Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ecReason')}>
                  Ec Reason <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('commitmentuntil')}>
                  Commitmentuntil <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  Comments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reasoncomments')}>
                  Reasoncomments <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Reason <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeCompensationList.map((employeeCompensation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-compensation/${employeeCompensation.id}`} color="link" size="sm">
                      {employeeCompensation.id}
                    </Button>
                  </td>
                  <td>
                    {employeeCompensation.amount ? (
                      <div>
                        {employeeCompensation.amountContentType ? (
                          <a onClick={openFile(employeeCompensation.amountContentType, employeeCompensation.amount)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeCompensation.amountContentType}, {byteSize(employeeCompensation.amount)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeCompensation.date ? (
                      <TextFormat type="date" value={employeeCompensation.date} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeCompensation.ecReason ? (
                      <div>
                        {employeeCompensation.ecReasonContentType ? (
                          <a onClick={openFile(employeeCompensation.ecReasonContentType, employeeCompensation.ecReason)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeCompensation.ecReasonContentType}, {byteSize(employeeCompensation.ecReason)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employeeCompensation.type}</td>
                  <td>
                    {employeeCompensation.commitmentuntil ? (
                      <TextFormat type="date" value={employeeCompensation.commitmentuntil} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeCompensation.comments}</td>
                  <td>
                    {employeeCompensation.createdat ? (
                      <TextFormat type="date" value={employeeCompensation.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeCompensation.updatedat ? (
                      <TextFormat type="date" value={employeeCompensation.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeCompensation.reasoncomments}</td>
                  <td>
                    {employeeCompensation.employee ? (
                      <Link to={`/employees/${employeeCompensation.employee.id}`}>{employeeCompensation.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeCompensation.reason ? (
                      <Link to={`/reasons/${employeeCompensation.reason.id}`}>{employeeCompensation.reason.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-compensation/${employeeCompensation.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-compensation/${employeeCompensation.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-compensation/${employeeCompensation.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Compensations found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeCompensationList && employeeCompensationList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeCompensation;
