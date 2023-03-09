import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeDetails } from 'app/shared/model/employee-details.model';
import { getEntities } from './employee-details.reducer';

export const EmployeeDetails = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeDetailsList = useAppSelector(state => state.employeeDetails.entities);
  const loading = useAppSelector(state => state.employeeDetails.loading);
  const totalItems = useAppSelector(state => state.employeeDetails.totalItems);

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
      <h2 id="employee-details-heading" data-cy="EmployeeDetailsHeading">
        Employee Details
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/employee-details/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Details
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeDetailsList && employeeDetailsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('religion')}>
                  Religion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maritalstatus')}>
                  Maritalstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cnic')}>
                  Cnic <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cnicexpiry')}>
                  Cnicexpiry <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('bloodgroup')}>
                  Bloodgroup <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxreturnfiler')}>
                  Taxreturnfiler <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passportno')}>
                  Passportno <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passportexpiry')}>
                  Passportexpiry <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totaltenure')}>
                  Totaltenure <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeDetailsList.map((employeeDetails, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-details/${employeeDetails.id}`} color="link" size="sm">
                      {employeeDetails.id}
                    </Button>
                  </td>
                  <td>{employeeDetails.religion}</td>
                  <td>{employeeDetails.maritalstatus}</td>
                  <td>
                    {employeeDetails.cnic ? (
                      <div>
                        {employeeDetails.cnicContentType ? (
                          <a onClick={openFile(employeeDetails.cnicContentType, employeeDetails.cnic)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeDetails.cnicContentType}, {byteSize(employeeDetails.cnic)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeDetails.cnicexpiry ? (
                      <div>
                        {employeeDetails.cnicexpiryContentType ? (
                          <a onClick={openFile(employeeDetails.cnicexpiryContentType, employeeDetails.cnicexpiry)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeDetails.cnicexpiryContentType}, {byteSize(employeeDetails.cnicexpiry)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employeeDetails.bloodgroup}</td>
                  <td>
                    {employeeDetails.taxreturnfiler ? (
                      <div>
                        {employeeDetails.taxreturnfilerContentType ? (
                          <a onClick={openFile(employeeDetails.taxreturnfilerContentType, employeeDetails.taxreturnfiler)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeDetails.taxreturnfilerContentType}, {byteSize(employeeDetails.taxreturnfiler)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeDetails.passportno ? (
                      <div>
                        {employeeDetails.passportnoContentType ? (
                          <a onClick={openFile(employeeDetails.passportnoContentType, employeeDetails.passportno)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeDetails.passportnoContentType}, {byteSize(employeeDetails.passportno)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeDetails.passportexpiry ? (
                      <div>
                        {employeeDetails.passportexpiryContentType ? (
                          <a onClick={openFile(employeeDetails.passportexpiryContentType, employeeDetails.passportexpiry)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeDetails.passportexpiryContentType}, {byteSize(employeeDetails.passportexpiry)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeDetails.createdat ? (
                      <TextFormat type="date" value={employeeDetails.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeDetails.updatedat ? (
                      <TextFormat type="date" value={employeeDetails.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeDetails.totaltenure}</td>
                  <td>
                    {employeeDetails.employee ? (
                      <Link to={`/employees/${employeeDetails.employee.id}`}>{employeeDetails.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-details/${employeeDetails.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-details/${employeeDetails.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-details/${employeeDetails.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Details found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeDetailsList && employeeDetailsList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeDetails;
