import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectCycles20190826 } from 'app/shared/model/project-cycles-20190826.model';
import { getEntities } from './project-cycles-20190826.reducer';

export const ProjectCycles20190826 = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const projectCycles20190826List = useAppSelector(state => state.projectCycles20190826.entities);
  const loading = useAppSelector(state => state.projectCycles20190826.loading);
  const totalItems = useAppSelector(state => state.projectCycles20190826.totalItems);

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
      <h2 id="project-cycles-20190826-heading" data-cy="ProjectCycles20190826Heading">
        Project Cycles 20190826 S
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/project-cycles-20190826/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Project Cycles 20190826
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectCycles20190826List && projectCycles20190826List.length > 0 ? (
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
                <th className="hand" onClick={sort('performancecycleid')}>
                  Performancecycleid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('projectid')}>
                  Projectid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allowedafterduedateby')}>
                  Allowedafterduedateby <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allowedafterduedateat')}>
                  Allowedafterduedateat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('duedate')}>
                  Duedate <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectCycles20190826List.map((projectCycles20190826, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/project-cycles-20190826/${projectCycles20190826.id}`} color="link" size="sm">
                      {projectCycles20190826.id}
                    </Button>
                  </td>
                  <td>{projectCycles20190826.isactive ? 'true' : 'false'}</td>
                  <td>
                    {projectCycles20190826.createdat ? (
                      <TextFormat type="date" value={projectCycles20190826.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {projectCycles20190826.updatedat ? (
                      <TextFormat type="date" value={projectCycles20190826.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{projectCycles20190826.performancecycleid}</td>
                  <td>{projectCycles20190826.projectid}</td>
                  <td>{projectCycles20190826.allowedafterduedateby}</td>
                  <td>
                    {projectCycles20190826.allowedafterduedateat ? (
                      <TextFormat type="date" value={projectCycles20190826.allowedafterduedateat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {projectCycles20190826.duedate ? (
                      <TextFormat type="date" value={projectCycles20190826.duedate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/project-cycles-20190826/${projectCycles20190826.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/project-cycles-20190826/${projectCycles20190826.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/project-cycles-20190826/${projectCycles20190826.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Project Cycles 20190826 S found</div>
        )}
      </div>
      {totalItems ? (
        <div className={projectCycles20190826List && projectCycles20190826List.length > 0 ? '' : 'd-none'}>
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

export default ProjectCycles20190826;
