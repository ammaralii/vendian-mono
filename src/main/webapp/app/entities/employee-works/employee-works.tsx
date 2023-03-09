import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeWorks } from 'app/shared/model/employee-works.model';
import { getEntities } from './employee-works.reducer';

export const EmployeeWorks = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeWorksList = useAppSelector(state => state.employeeWorks.entities);
  const loading = useAppSelector(state => state.employeeWorks.loading);
  const totalItems = useAppSelector(state => state.employeeWorks.totalItems);

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
      <h2 id="employee-works-heading" data-cy="EmployeeWorksHeading">
        Employee Works
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/employee-works/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Works
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeWorksList && employeeWorksList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startdate')}>
                  Startdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('designation')}>
                  Designation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leavingreason')}>
                  Leavingreason <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Company <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeWorksList.map((employeeWorks, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-works/${employeeWorks.id}`} color="link" size="sm">
                      {employeeWorks.id}
                    </Button>
                  </td>
                  <td>
                    {employeeWorks.startdate ? <TextFormat type="date" value={employeeWorks.startdate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeWorks.enddate ? <TextFormat type="date" value={employeeWorks.enddate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{employeeWorks.designation}</td>
                  <td>{employeeWorks.leavingreason}</td>
                  <td>
                    {employeeWorks.createdat ? <TextFormat type="date" value={employeeWorks.createdat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeWorks.updatedat ? <TextFormat type="date" value={employeeWorks.updatedat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeWorks.employee ? <Link to={`/employees/${employeeWorks.employee.id}`}>{employeeWorks.employee.id}</Link> : ''}
                  </td>
                  <td>
                    {employeeWorks.company ? <Link to={`/companies/${employeeWorks.company.id}`}>{employeeWorks.company.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/employee-works/${employeeWorks.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-works/${employeeWorks.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-works/${employeeWorks.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Works found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeWorksList && employeeWorksList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeWorks;
