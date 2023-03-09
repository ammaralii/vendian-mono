import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZEmployeeProjects } from 'app/shared/model/z-employee-projects.model';
import { getEntities } from './z-employee-projects.reducer';

export const ZEmployeeProjects = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const zEmployeeProjectsList = useAppSelector(state => state.zEmployeeProjects.entities);
  const loading = useAppSelector(state => state.zEmployeeProjects.loading);
  const totalItems = useAppSelector(state => state.zEmployeeProjects.totalItems);

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
      <h2 id="z-employee-projects-heading" data-cy="ZEmployeeProjectsHeading">
        Z Employee Projects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/z-employee-projects/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Z Employee Projects
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zEmployeeProjectsList && zEmployeeProjectsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startdate')}>
                  Startdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allocation')}>
                  Allocation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('billed')}>
                  Billed <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deliverymanagerid')}>
                  Deliverymanagerid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('architectid')}>
                  Architectid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  Notes <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('previousenddate')}>
                  Previousenddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('data')}>
                  Data <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extendedenddate')}>
                  Extendedenddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('probability')}>
                  Probability <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Event <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employeeproject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Assignor <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectmanager <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zEmployeeProjectsList.map((zEmployeeProjects, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/z-employee-projects/${zEmployeeProjects.id}`} color="link" size="sm">
                      {zEmployeeProjects.id}
                    </Button>
                  </td>
                  <td>{zEmployeeProjects.status ? 'true' : 'false'}</td>
                  <td>{zEmployeeProjects.type}</td>
                  <td>
                    {zEmployeeProjects.startdate ? (
                      <TextFormat type="date" value={zEmployeeProjects.startdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {zEmployeeProjects.enddate ? (
                      <TextFormat type="date" value={zEmployeeProjects.enddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{zEmployeeProjects.name}</td>
                  <td>{zEmployeeProjects.allocation ? 'true' : 'false'}</td>
                  <td>{zEmployeeProjects.billed}</td>
                  <td>
                    {zEmployeeProjects.createdat ? (
                      <TextFormat type="date" value={zEmployeeProjects.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {zEmployeeProjects.updatedat ? (
                      <TextFormat type="date" value={zEmployeeProjects.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{zEmployeeProjects.deliverymanagerid}</td>
                  <td>{zEmployeeProjects.architectid}</td>
                  <td>{zEmployeeProjects.notes}</td>
                  <td>
                    {zEmployeeProjects.previousenddate ? (
                      <TextFormat type="date" value={zEmployeeProjects.previousenddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{zEmployeeProjects.data}</td>
                  <td>
                    {zEmployeeProjects.extendedenddate ? (
                      <TextFormat type="date" value={zEmployeeProjects.extendedenddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{zEmployeeProjects.probability}</td>
                  <td>
                    {zEmployeeProjects.event ? <Link to={`/events/${zEmployeeProjects.event.id}`}>{zEmployeeProjects.event.id}</Link> : ''}
                  </td>
                  <td>
                    {zEmployeeProjects.employee ? (
                      <Link to={`/employees/${zEmployeeProjects.employee.id}`}>{zEmployeeProjects.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zEmployeeProjects.project ? (
                      <Link to={`/projects/${zEmployeeProjects.project.id}`}>{zEmployeeProjects.project.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zEmployeeProjects.employeeproject ? (
                      <Link to={`/employee-projects/${zEmployeeProjects.employeeproject.id}`}>{zEmployeeProjects.employeeproject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zEmployeeProjects.assignor ? (
                      <Link to={`/employees/${zEmployeeProjects.assignor.id}`}>{zEmployeeProjects.assignor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zEmployeeProjects.projectmanager ? (
                      <Link to={`/employees/${zEmployeeProjects.projectmanager.id}`}>{zEmployeeProjects.projectmanager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/z-employee-projects/${zEmployeeProjects.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/z-employee-projects/${zEmployeeProjects.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/z-employee-projects/${zEmployeeProjects.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Z Employee Projects found</div>
        )}
      </div>
      {totalItems ? (
        <div className={zEmployeeProjectsList && zEmployeeProjectsList.length > 0 ? '' : 'd-none'}>
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

export default ZEmployeeProjects;
