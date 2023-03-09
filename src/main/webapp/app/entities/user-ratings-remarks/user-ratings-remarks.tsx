import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserRatingsRemarks } from 'app/shared/model/user-ratings-remarks.model';
import { getEntities } from './user-ratings-remarks.reducer';

export const UserRatingsRemarks = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const userRatingsRemarksList = useAppSelector(state => state.userRatingsRemarks.entities);
  const loading = useAppSelector(state => state.userRatingsRemarks.loading);
  const totalItems = useAppSelector(state => state.userRatingsRemarks.totalItems);

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
      <h2 id="user-ratings-remarks-heading" data-cy="UserRatingsRemarksHeading">
        User Ratings Remarks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/user-ratings-remarks/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new User Ratings Remarks
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userRatingsRemarksList && userRatingsRemarksList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPromotion')}>
                  Is Promotion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('strength')}>
                  Strength <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('areaOfImprovement')}>
                  Area Of Improvement <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('promotionJustification')}>
                  Promotion Justification <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('newGrade')}>
                  New Grade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isRedesignation')}>
                  Is Redesignation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recommendedSalary')}>
                  Recommended Salary <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  Updated At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deletedAt')}>
                  Deleted At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  Version <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherComments')}>
                  Other Comments <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Designation After Promotion <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Designation After Redesignation <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rater <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Pc Employee Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userRatingsRemarksList.map((userRatingsRemarks, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-ratings-remarks/${userRatingsRemarks.id}`} color="link" size="sm">
                      {userRatingsRemarks.id}
                    </Button>
                  </td>
                  <td>{userRatingsRemarks.isPromotion ? 'true' : 'false'}</td>
                  <td>{userRatingsRemarks.strength}</td>
                  <td>{userRatingsRemarks.areaOfImprovement}</td>
                  <td>{userRatingsRemarks.promotionJustification}</td>
                  <td>{userRatingsRemarks.newGrade}</td>
                  <td>{userRatingsRemarks.isRedesignation ? 'true' : 'false'}</td>
                  <td>{userRatingsRemarks.recommendedSalary}</td>
                  <td>{userRatingsRemarks.status}</td>
                  <td>
                    {userRatingsRemarks.createdAt ? (
                      <TextFormat type="date" value={userRatingsRemarks.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userRatingsRemarks.updatedAt ? (
                      <TextFormat type="date" value={userRatingsRemarks.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userRatingsRemarks.deletedAt ? (
                      <TextFormat type="date" value={userRatingsRemarks.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userRatingsRemarks.version}</td>
                  <td>{userRatingsRemarks.otherComments}</td>
                  <td>
                    {userRatingsRemarks.designationAfterPromotion ? (
                      <Link to={`/designations/${userRatingsRemarks.designationAfterPromotion.id}`}>
                        {userRatingsRemarks.designationAfterPromotion.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userRatingsRemarks.designationAfterRedesignation ? (
                      <Link to={`/designations/${userRatingsRemarks.designationAfterRedesignation.id}`}>
                        {userRatingsRemarks.designationAfterRedesignation.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userRatingsRemarks.rater ? (
                      <Link to={`/employees/${userRatingsRemarks.rater.id}`}>{userRatingsRemarks.rater.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userRatingsRemarks.pcEmployeeRating ? (
                      <Link to={`/performance-cycle-employee-rating/${userRatingsRemarks.pcEmployeeRating.id}`}>
                        {userRatingsRemarks.pcEmployeeRating.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/user-ratings-remarks/${userRatingsRemarks.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-ratings-remarks/${userRatingsRemarks.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-ratings-remarks/${userRatingsRemarks.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No User Ratings Remarks found</div>
        )}
      </div>
      {totalItems ? (
        <div className={userRatingsRemarksList && userRatingsRemarksList.length > 0 ? '' : 'd-none'}>
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

export default UserRatingsRemarks;
