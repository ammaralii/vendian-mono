import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeProjectRatings20190826 } from 'app/shared/model/employee-project-ratings-20190826.model';
import { getEntities } from './employee-project-ratings-20190826.reducer';

export const EmployeeProjectRatings20190826 = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeProjectRatings20190826List = useAppSelector(state => state.employeeProjectRatings20190826.entities);
  const loading = useAppSelector(state => state.employeeProjectRatings20190826.loading);
  const totalItems = useAppSelector(state => state.employeeProjectRatings20190826.totalItems);

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
      <h2 id="employee-project-ratings-20190826-heading" data-cy="EmployeeProjectRatings20190826Heading">
        Employee Project Ratings 20190826 S
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/employee-project-ratings-20190826/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Project Ratings 20190826
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeProjectRatings20190826List && employeeProjectRatings20190826List.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmquality')}>
                  Pmquality <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmownership')}>
                  Pmownership <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmskill')}>
                  Pmskill <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmethics')}>
                  Pmethics <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmefficiency')}>
                  Pmefficiency <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmfreeze')}>
                  Pmfreeze <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archfreeze')}>
                  Archfreeze <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmcomment')}>
                  Pmcomment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archquality')}>
                  Archquality <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archownership')}>
                  Archownership <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archskill')}>
                  Archskill <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archethics')}>
                  Archethics <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archefficiency')}>
                  Archefficiency <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archcomment')}>
                  Archcomment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('projectarchitectid')}>
                  Projectarchitectid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('projectmanagerid')}>
                  Projectmanagerid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employeeid')}>
                  Employeeid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('projectcycleid')}>
                  Projectcycleid <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeProjectRatings20190826List.map((employeeProjectRatings20190826, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/employee-project-ratings-20190826/${employeeProjectRatings20190826.id}`}
                      color="link"
                      size="sm"
                    >
                      {employeeProjectRatings20190826.id}
                    </Button>
                  </td>
                  <td>
                    {employeeProjectRatings20190826.createdat ? (
                      <TextFormat type="date" value={employeeProjectRatings20190826.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.updatedat ? (
                      <TextFormat type="date" value={employeeProjectRatings20190826.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmquality ? (
                      <div>
                        {employeeProjectRatings20190826.pmqualityContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.pmqualityContentType,
                              employeeProjectRatings20190826.pmquality
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmqualityContentType}, {byteSize(employeeProjectRatings20190826.pmquality)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmownership ? (
                      <div>
                        {employeeProjectRatings20190826.pmownershipContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.pmownershipContentType,
                              employeeProjectRatings20190826.pmownership
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmownershipContentType}, {byteSize(employeeProjectRatings20190826.pmownership)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmskill ? (
                      <div>
                        {employeeProjectRatings20190826.pmskillContentType ? (
                          <a onClick={openFile(employeeProjectRatings20190826.pmskillContentType, employeeProjectRatings20190826.pmskill)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmskillContentType}, {byteSize(employeeProjectRatings20190826.pmskill)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmethics ? (
                      <div>
                        {employeeProjectRatings20190826.pmethicsContentType ? (
                          <a
                            onClick={openFile(employeeProjectRatings20190826.pmethicsContentType, employeeProjectRatings20190826.pmethics)}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmethicsContentType}, {byteSize(employeeProjectRatings20190826.pmethics)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmefficiency ? (
                      <div>
                        {employeeProjectRatings20190826.pmefficiencyContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.pmefficiencyContentType,
                              employeeProjectRatings20190826.pmefficiency
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmefficiencyContentType}, {byteSize(employeeProjectRatings20190826.pmefficiency)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmfreeze ? (
                      <div>
                        {employeeProjectRatings20190826.pmfreezeContentType ? (
                          <a
                            onClick={openFile(employeeProjectRatings20190826.pmfreezeContentType, employeeProjectRatings20190826.pmfreeze)}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmfreezeContentType}, {byteSize(employeeProjectRatings20190826.pmfreeze)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archfreeze ? (
                      <div>
                        {employeeProjectRatings20190826.archfreezeContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archfreezeContentType,
                              employeeProjectRatings20190826.archfreeze
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archfreezeContentType}, {byteSize(employeeProjectRatings20190826.archfreeze)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.pmcomment ? (
                      <div>
                        {employeeProjectRatings20190826.pmcommentContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.pmcommentContentType,
                              employeeProjectRatings20190826.pmcomment
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.pmcommentContentType}, {byteSize(employeeProjectRatings20190826.pmcomment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archquality ? (
                      <div>
                        {employeeProjectRatings20190826.archqualityContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archqualityContentType,
                              employeeProjectRatings20190826.archquality
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archqualityContentType}, {byteSize(employeeProjectRatings20190826.archquality)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archownership ? (
                      <div>
                        {employeeProjectRatings20190826.archownershipContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archownershipContentType,
                              employeeProjectRatings20190826.archownership
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archownershipContentType},{' '}
                          {byteSize(employeeProjectRatings20190826.archownership)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archskill ? (
                      <div>
                        {employeeProjectRatings20190826.archskillContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archskillContentType,
                              employeeProjectRatings20190826.archskill
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archskillContentType}, {byteSize(employeeProjectRatings20190826.archskill)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archethics ? (
                      <div>
                        {employeeProjectRatings20190826.archethicsContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archethicsContentType,
                              employeeProjectRatings20190826.archethics
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archethicsContentType}, {byteSize(employeeProjectRatings20190826.archethics)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archefficiency ? (
                      <div>
                        {employeeProjectRatings20190826.archefficiencyContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archefficiencyContentType,
                              employeeProjectRatings20190826.archefficiency
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archefficiencyContentType},{' '}
                          {byteSize(employeeProjectRatings20190826.archefficiency)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings20190826.archcomment ? (
                      <div>
                        {employeeProjectRatings20190826.archcommentContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings20190826.archcommentContentType,
                              employeeProjectRatings20190826.archcomment
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings20190826.archcommentContentType}, {byteSize(employeeProjectRatings20190826.archcomment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employeeProjectRatings20190826.projectarchitectid}</td>
                  <td>{employeeProjectRatings20190826.projectmanagerid}</td>
                  <td>{employeeProjectRatings20190826.employeeid}</td>
                  <td>{employeeProjectRatings20190826.projectcycleid}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings-20190826/${employeeProjectRatings20190826.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings-20190826/${employeeProjectRatings20190826.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings-20190826/${employeeProjectRatings20190826.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Project Ratings 20190826 S found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeProjectRatings20190826List && employeeProjectRatings20190826List.length > 0 ? '' : 'd-none'}>
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

export default EmployeeProjectRatings20190826;
