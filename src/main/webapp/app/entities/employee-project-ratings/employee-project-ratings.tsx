import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeProjectRatings } from 'app/shared/model/employee-project-ratings.model';
import { getEntities } from './employee-project-ratings.reducer';

export const EmployeeProjectRatings = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const employeeProjectRatingsList = useAppSelector(state => state.employeeProjectRatings.entities);
  const loading = useAppSelector(state => state.employeeProjectRatings.loading);
  const totalItems = useAppSelector(state => state.employeeProjectRatings.totalItems);

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
      <h2 id="employee-project-ratings-heading" data-cy="EmployeeProjectRatingsHeading">
        Employee Project Ratings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/employee-project-ratings/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Project Ratings
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeProjectRatingsList && employeeProjectRatingsList.length > 0 ? (
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
                <th className="hand" onClick={sort('archcodequality')}>
                  Archcodequality <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archdocumentation')}>
                  Archdocumentation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('archcollaboration')}>
                  Archcollaboration <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmdocumentation')}>
                  Pmdocumentation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmcollaboration')}>
                  Pmcollaboration <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectarchitect <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectmanager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectcycle <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeProjectRatingsList.map((employeeProjectRatings, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee-project-ratings/${employeeProjectRatings.id}`} color="link" size="sm">
                      {employeeProjectRatings.id}
                    </Button>
                  </td>
                  <td>
                    {employeeProjectRatings.createdat ? (
                      <TextFormat type="date" value={employeeProjectRatings.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.updatedat ? (
                      <TextFormat type="date" value={employeeProjectRatings.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmquality ? (
                      <div>
                        {employeeProjectRatings.pmqualityContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmqualityContentType, employeeProjectRatings.pmquality)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmqualityContentType}, {byteSize(employeeProjectRatings.pmquality)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmownership ? (
                      <div>
                        {employeeProjectRatings.pmownershipContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmownershipContentType, employeeProjectRatings.pmownership)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmownershipContentType}, {byteSize(employeeProjectRatings.pmownership)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmskill ? (
                      <div>
                        {employeeProjectRatings.pmskillContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmskillContentType, employeeProjectRatings.pmskill)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmskillContentType}, {byteSize(employeeProjectRatings.pmskill)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmethics ? (
                      <div>
                        {employeeProjectRatings.pmethicsContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmethicsContentType, employeeProjectRatings.pmethics)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmethicsContentType}, {byteSize(employeeProjectRatings.pmethics)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmefficiency ? (
                      <div>
                        {employeeProjectRatings.pmefficiencyContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmefficiencyContentType, employeeProjectRatings.pmefficiency)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmefficiencyContentType}, {byteSize(employeeProjectRatings.pmefficiency)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmfreeze ? (
                      <div>
                        {employeeProjectRatings.pmfreezeContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmfreezeContentType, employeeProjectRatings.pmfreeze)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmfreezeContentType}, {byteSize(employeeProjectRatings.pmfreeze)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archfreeze ? (
                      <div>
                        {employeeProjectRatings.archfreezeContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archfreezeContentType, employeeProjectRatings.archfreeze)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archfreezeContentType}, {byteSize(employeeProjectRatings.archfreeze)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmcomment ? (
                      <div>
                        {employeeProjectRatings.pmcommentContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmcommentContentType, employeeProjectRatings.pmcomment)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmcommentContentType}, {byteSize(employeeProjectRatings.pmcomment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archquality ? (
                      <div>
                        {employeeProjectRatings.archqualityContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archqualityContentType, employeeProjectRatings.archquality)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archqualityContentType}, {byteSize(employeeProjectRatings.archquality)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archownership ? (
                      <div>
                        {employeeProjectRatings.archownershipContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archownershipContentType, employeeProjectRatings.archownership)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archownershipContentType}, {byteSize(employeeProjectRatings.archownership)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archskill ? (
                      <div>
                        {employeeProjectRatings.archskillContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archskillContentType, employeeProjectRatings.archskill)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archskillContentType}, {byteSize(employeeProjectRatings.archskill)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archethics ? (
                      <div>
                        {employeeProjectRatings.archethicsContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archethicsContentType, employeeProjectRatings.archethics)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archethicsContentType}, {byteSize(employeeProjectRatings.archethics)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archefficiency ? (
                      <div>
                        {employeeProjectRatings.archefficiencyContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archefficiencyContentType, employeeProjectRatings.archefficiency)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archefficiencyContentType}, {byteSize(employeeProjectRatings.archefficiency)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archcomment ? (
                      <div>
                        {employeeProjectRatings.archcommentContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archcommentContentType, employeeProjectRatings.archcomment)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archcommentContentType}, {byteSize(employeeProjectRatings.archcomment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archcodequality ? (
                      <div>
                        {employeeProjectRatings.archcodequalityContentType ? (
                          <a onClick={openFile(employeeProjectRatings.archcodequalityContentType, employeeProjectRatings.archcodequality)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archcodequalityContentType}, {byteSize(employeeProjectRatings.archcodequality)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archdocumentation ? (
                      <div>
                        {employeeProjectRatings.archdocumentationContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings.archdocumentationContentType,
                              employeeProjectRatings.archdocumentation
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archdocumentationContentType}, {byteSize(employeeProjectRatings.archdocumentation)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.archcollaboration ? (
                      <div>
                        {employeeProjectRatings.archcollaborationContentType ? (
                          <a
                            onClick={openFile(
                              employeeProjectRatings.archcollaborationContentType,
                              employeeProjectRatings.archcollaboration
                            )}
                          >
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.archcollaborationContentType}, {byteSize(employeeProjectRatings.archcollaboration)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmdocumentation ? (
                      <div>
                        {employeeProjectRatings.pmdocumentationContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmdocumentationContentType, employeeProjectRatings.pmdocumentation)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmdocumentationContentType}, {byteSize(employeeProjectRatings.pmdocumentation)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.pmcollaboration ? (
                      <div>
                        {employeeProjectRatings.pmcollaborationContentType ? (
                          <a onClick={openFile(employeeProjectRatings.pmcollaborationContentType, employeeProjectRatings.pmcollaboration)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employeeProjectRatings.pmcollaborationContentType}, {byteSize(employeeProjectRatings.pmcollaboration)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {employeeProjectRatings.projectarchitect ? (
                      <Link to={`/employees/${employeeProjectRatings.projectarchitect.id}`}>
                        {employeeProjectRatings.projectarchitect.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeProjectRatings.projectmanager ? (
                      <Link to={`/employees/${employeeProjectRatings.projectmanager.id}`}>{employeeProjectRatings.projectmanager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeProjectRatings.employee ? (
                      <Link to={`/employees/${employeeProjectRatings.employee.id}`}>{employeeProjectRatings.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeProjectRatings.projectcycle ? (
                      <Link to={`/project-cycles/${employeeProjectRatings.projectcycle.id}`}>{employeeProjectRatings.projectcycle.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings/${employeeProjectRatings.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings/${employeeProjectRatings.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee-project-ratings/${employeeProjectRatings.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Employee Project Ratings found</div>
        )}
      </div>
      {totalItems ? (
        <div className={employeeProjectRatingsList && employeeProjectRatingsList.length > 0 ? '' : 'd-none'}>
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

export default EmployeeProjectRatings;
