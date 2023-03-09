import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities } from './employees.reducer';

export const Employees = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeesList = useAppSelector(state => state.employees.entities);
  const loading = useAppSelector(state => state.employees.loading);
  const totalItems = useAppSelector(state => state.employees.totalItems);

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
      <h2 id="employees-heading" data-cy="EmployeesHeading">
        Employees
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/employees/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employees
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeesList && employeesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstname')}>
                  Firstname <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastname')}>
                  Lastname <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phonenumber')}>
                  Phonenumber <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateofbirth')}>
                  Dateofbirth <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  Email <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('skype')}>
                  Skype <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employeeDesignation')}>
                  Employee Designation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('joiningdate')}>
                  Joiningdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  Area <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leavingdate')}>
                  Leavingdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  Notes <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isactive')}>
                  Isactive <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('googleid')}>
                  Googleid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('oracleid')}>
                  Oracleid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deptt')}>
                  Deptt <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('genderid')}>
                  Genderid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('onprobation')}>
                  Onprobation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employeeCompetency')}>
                  Employee Competency <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resourcetype')}>
                  Resourcetype <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grade')}>
                  Grade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subgrade')}>
                  Subgrade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageurl')}>
                  Imageurl <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resignationdate')}>
                  Resignationdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('graduationdate')}>
                  Graduationdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('careerstartdate')}>
                  Careerstartdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('externalexpyears')}>
                  Externalexpyears <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('externalexpmonths')}>
                  Externalexpmonths <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('placeofbirth')}>
                  Placeofbirth <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hireddate')}>
                  Hireddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lasttrackingupdate')}>
                  Lasttrackingupdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('middlename')}>
                  Middlename <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grosssalary')}>
                  Grosssalary <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fuelallowance')}>
                  Fuelallowance <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobilenumber')}>
                  Mobilenumber <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resignationtype')}>
                  Resignationtype <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('primaryreasonforleaving')}>
                  Primaryreasonforleaving <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('secondaryreasonforleaving')}>
                  Secondaryreasonforleaving <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noticeperiodduration')}>
                  Noticeperiodduration <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noticeperiodserved')}>
                  Noticeperiodserved <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('probationperiodduration')}>
                  Probationperiodduration <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Location <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Role <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leave <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Department <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Businessunit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Division <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Competency <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employmentstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employmenttype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Designation <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeesList.map((employees, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employees/${employees.id}`} color="link" size="sm">
                      {employees.id}
                    </Button>
                  </td>
                  <td>{employees.firstname}</td>
                  <td>{employees.lastname}</td>
                  <td>{employees.phonenumber}</td>
                  <td>
                    {employees.dateofbirth ? <TextFormat type="date" value={employees.dateofbirth} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{employees.email}</td>
                  <td>{employees.skype}</td>
                  <td>{employees.employeeDesignation}</td>
                  <td>
                    {employees.joiningdate ? <TextFormat type="date" value={employees.joiningdate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{employees.area}</td>
                  <td>
                    {employees.leavingdate ? <TextFormat type="date" value={employees.leavingdate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{employees.notes}</td>
                  <td>{employees.isactive ? 'true' : 'false'}</td>
                  <td>{employees.googleid}</td>
                  <td>{employees.oracleid}</td>
                  <td>{employees.deptt}</td>
                  <td>{employees.createdat ? <TextFormat type="date" value={employees.createdat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employees.updatedat ? <TextFormat type="date" value={employees.updatedat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employees.genderid}</td>
                  <td>{employees.onprobation ? 'true' : 'false'}</td>
                  <td>{employees.employeeCompetency}</td>
                  <td>{employees.resourcetype}</td>
                  <td>{employees.grade}</td>
                  <td>{employees.subgrade}</td>
                  <td>{employees.imageurl}</td>
                  <td>
                    {employees.resignationdate ? (
                      <TextFormat type="date" value={employees.resignationdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employees.graduationdate ? (
                      <TextFormat type="date" value={employees.graduationdate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employees.careerstartdate ? (
                      <TextFormat type="date" value={employees.careerstartdate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employees.externalexpyears}</td>
                  <td>{employees.externalexpmonths}</td>
                  <td>{employees.placeofbirth}</td>
                  <td>{employees.hireddate ? <TextFormat type="date" value={employees.hireddate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {employees.lasttrackingupdate ? (
                      <TextFormat type="date" value={employees.lasttrackingupdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{employees.middlename}</td>
                  <td>
                    {employees.grosssalary ? (
                      <div>
                        {employees.grosssalaryContentType ? (
                          <a onClick={openFile(employees.grosssalaryContentType, employees.grosssalary)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employees.grosssalaryContentType}, {byteSize(employees.grosssalary)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employees.fuelallowance ? (
                      <div>
                        {employees.fuelallowanceContentType ? (
                          <a onClick={openFile(employees.fuelallowanceContentType, employees.fuelallowance)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employees.fuelallowanceContentType}, {byteSize(employees.fuelallowance)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employees.mobilenumber}</td>
                  <td>{employees.resignationtype}</td>
                  <td>{employees.primaryreasonforleaving}</td>
                  <td>{employees.secondaryreasonforleaving}</td>
                  <td>{employees.noticeperiodduration}</td>
                  <td>{employees.noticeperiodserved}</td>
                  <td>{employees.probationperiodduration}</td>
                  <td>{employees.location ? <Link to={`/locations/${employees.location.id}`}>{employees.location.id}</Link> : ''}</td>
                  <td>{employees.role ? <Link to={`/roles/${employees.role.id}`}>{employees.role.id}</Link> : ''}</td>
                  <td>{employees.manager ? <Link to={`/employees/${employees.manager.id}`}>{employees.manager.id}</Link> : ''}</td>
                  <td>{employees.leave ? <Link to={`/leaves-olds/${employees.leave.id}`}>{employees.leave.id}</Link> : ''}</td>
                  <td>
                    {employees.department ? <Link to={`/departments/${employees.department.id}`}>{employees.department.id}</Link> : ''}
                  </td>
                  <td>
                    {employees.businessunit ? (
                      <Link to={`/business-units/${employees.businessunit.id}`}>{employees.businessunit.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{employees.division ? <Link to={`/divisions/${employees.division.id}`}>{employees.division.id}</Link> : ''}</td>
                  <td>
                    {employees.competency ? <Link to={`/competencies/${employees.competency.id}`}>{employees.competency.id}</Link> : ''}
                  </td>
                  <td>
                    {employees.employmentstatus ? (
                      <Link to={`/employment-statuses/${employees.employmentstatus.id}`}>{employees.employmentstatus.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employees.employmenttype ? (
                      <Link to={`/employment-types/${employees.employmenttype.id}`}>{employees.employmenttype.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employees.designation ? <Link to={`/designations/${employees.designation.id}`}>{employees.designation.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/employees/${employees.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employees/${employees.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employees/${employees.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employees found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeesList && employeesList.length > 0 ? '' : 'd-none'}>
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

export default Employees;
