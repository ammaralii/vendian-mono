import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { getEntities } from './employee-job-info.reducer';

export const EmployeeJobInfo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeJobInfoList = useAppSelector(state => state.employeeJobInfo.entities);
  const loading = useAppSelector(state => state.employeeJobInfo.loading);
  const totalItems = useAppSelector(state => state.employeeJobInfo.totalItems);

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
      <h2 id="employee-job-info-heading" data-cy="EmployeeJobInfoHeading">
        Employee Job Infos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/employee-job-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Job Info
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeJobInfoList && employeeJobInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  Title <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grade')}>
                  Grade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subgrade')}>
                  Subgrade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startdate')}>
                  Startdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('location')}>
                  Location <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grosssalary')}>
                  Grosssalary <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fuelallowance')}>
                  Fuelallowance <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Designation <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Reviewmanager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Department <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employmenttype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Jobdescription <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Division <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Businessunit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Competency <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeJobInfoList.map((employeeJobInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-job-info/${employeeJobInfo.id}`} color="link" size="sm">
                      {employeeJobInfo.id}
                    </Button>
                  </td>
                  <td>{employeeJobInfo.title}</td>
                  <td>{employeeJobInfo.grade}</td>
                  <td>{employeeJobInfo.subgrade}</td>
                  <td>
                    {employeeJobInfo.startdate ? (
                      <TextFormat type="date" value={employeeJobInfo.startdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeJobInfo.enddate ? <TextFormat type="date" value={employeeJobInfo.enddate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeJobInfo.createdat ? (
                      <TextFormat type="date" value={employeeJobInfo.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeJobInfo.updatedat ? (
                      <TextFormat type="date" value={employeeJobInfo.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employeeJobInfo.location}</td>
                  <td>
                    {employeeJobInfo.grosssalary ? (
                      <div>
                        {employeeJobInfo.grosssalaryContentType ? (
                          <a onClick={openFile(employeeJobInfo.grosssalaryContentType, employeeJobInfo.grosssalary)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeJobInfo.grosssalaryContentType}, {byteSize(employeeJobInfo.grosssalary)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeJobInfo.fuelallowance ? (
                      <div>
                        {employeeJobInfo.fuelallowanceContentType ? (
                          <a onClick={openFile(employeeJobInfo.fuelallowanceContentType, employeeJobInfo.fuelallowance)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeJobInfo.fuelallowanceContentType}, {byteSize(employeeJobInfo.fuelallowance)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeJobInfo.employee ? (
                      <Link to={`/employees/${employeeJobInfo.employee.id}`}>{employeeJobInfo.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.designation ? (
                      <Link to={`/designations/${employeeJobInfo.designation.id}`}>{employeeJobInfo.designation.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.reviewmanager ? (
                      <Link to={`/employees/${employeeJobInfo.reviewmanager.id}`}>{employeeJobInfo.reviewmanager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.manager ? (
                      <Link to={`/employees/${employeeJobInfo.manager.id}`}>{employeeJobInfo.manager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.department ? (
                      <Link to={`/departments/${employeeJobInfo.department.id}`}>{employeeJobInfo.department.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.employmenttype ? (
                      <Link to={`/employment-types/${employeeJobInfo.employmenttype.id}`}>{employeeJobInfo.employmenttype.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.jobdescription ? (
                      <Link to={`/designation-job-descriptions/${employeeJobInfo.jobdescription.id}`}>
                        {employeeJobInfo.jobdescription.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.division ? (
                      <Link to={`/divisions/${employeeJobInfo.division.id}`}>{employeeJobInfo.division.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.businessunit ? (
                      <Link to={`/business-units/${employeeJobInfo.businessunit.id}`}>{employeeJobInfo.businessunit.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeJobInfo.competency ? (
                      <Link to={`/competencies/${employeeJobInfo.competency.id}`}>{employeeJobInfo.competency.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-job-info/${employeeJobInfo.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-job-info/${employeeJobInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-job-info/${employeeJobInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Job Infos found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeJobInfoList && employeeJobInfoList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeJobInfo;
