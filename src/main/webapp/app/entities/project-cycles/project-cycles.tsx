import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { getEntities } from './project-cycles.reducer';

export const ProjectCycles = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const projectCyclesList = useAppSelector(state => state.projectCycles.entities);
  const loading = useAppSelector(state => state.projectCycles.loading);
  const totalItems = useAppSelector(state => state.projectCycles.totalItems);

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
      <h2 id="project-cycles-heading" data-cy="ProjectCyclesHeading">
        Project Cycles
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/project-cycles/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Project Cycles
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectCyclesList && projectCyclesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isactive')}>
                  Isactive <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allowedafterduedateat')}>
                  Allowedafterduedateat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('duedate')}>
                  Duedate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('auditlogs')}>
                  Auditlogs <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deletedat')}>
                  Deletedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Allowedafterduedateby <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Architect <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectmanager <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectCyclesList.map((projectCycles, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/project-cycles/${projectCycles.id}`} color="link" size="sm">
                      {projectCycles.id}
                    </Button>
                  </td>
                  <td>{projectCycles.isactive ? 'true' : 'false'}</td>
                  <td>
                    {projectCycles.createdat ? <TextFormat type="date" value={projectCycles.createdat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {projectCycles.updatedat ? <TextFormat type="date" value={projectCycles.updatedat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {projectCycles.allowedafterduedateat ? (
                      <TextFormat type="date" value={projectCycles.allowedafterduedateat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {projectCycles.duedate ? <TextFormat type="date" value={projectCycles.duedate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{projectCycles.auditlogs}</td>
                  <td>
                    {projectCycles.deletedat ? <TextFormat type="date" value={projectCycles.deletedat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {projectCycles.project ? <Link to={`/projects/${projectCycles.project.id}`}>{projectCycles.project.id}</Link> : ''}
                  </td>
                  <td>
                    {projectCycles.allowedafterduedateby ? (
                      <Link to={`/employees/${projectCycles.allowedafterduedateby.id}`}>{projectCycles.allowedafterduedateby.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectCycles.architect ? (
                      <Link to={`/employees/${projectCycles.architect.id}`}>{projectCycles.architect.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectCycles.projectmanager ? (
                      <Link to={`/employees/${projectCycles.projectmanager.id}`}>{projectCycles.projectmanager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/project-cycles/${projectCycles.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/project-cycles/${projectCycles.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/project-cycles/${projectCycles.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Project Cycles found</div>
        )}
      </div>
      {totalItems ? (
        <div className={projectCyclesList && projectCyclesList.length > 0 ? '' : 'd-none'}>
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

export default ProjectCycles;
