import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserGoalRaterAttributes } from 'app/shared/model/user-goal-rater-attributes.model';
import { getEntities } from './user-goal-rater-attributes.reducer';

export const UserGoalRaterAttributes = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const userGoalRaterAttributesList = useAppSelector(state => state.userGoalRaterAttributes.entities);
  const loading = useAppSelector(state => state.userGoalRaterAttributes.loading);
  const totalItems = useAppSelector(state => state.userGoalRaterAttributes.totalItems);

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
      <h2 id="user-goal-rater-attributes-heading" data-cy="UserGoalRaterAttributesHeading">
        User Goal Rater Attributes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/user-goal-rater-attributes/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new User Goal Rater Attributes
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userGoalRaterAttributesList && userGoalRaterAttributesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ugraRating')}>
                  Ugra Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  Comment <FontAwesomeIcon icon="sort" />
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
                <th>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Usergoal <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userGoalRaterAttributesList.map((userGoalRaterAttributes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-goal-rater-attributes/${userGoalRaterAttributes.id}`} color="link" size="sm">
                      {userGoalRaterAttributes.id}
                    </Button>
                  </td>
                  <td>
                    {userGoalRaterAttributes.ugraRating ? (
                      <div>
                        {userGoalRaterAttributes.ugraRatingContentType ? (
                          <a onClick={openFile(userGoalRaterAttributes.ugraRatingContentType, userGoalRaterAttributes.ugraRating)}>
                            Open &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {userGoalRaterAttributes.ugraRatingContentType}, {byteSize(userGoalRaterAttributes.ugraRating)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {userGoalRaterAttributes.comment ? (
                      <div>
                        {userGoalRaterAttributes.commentContentType ? (
                          <a onClick={openFile(userGoalRaterAttributes.commentContentType, userGoalRaterAttributes.comment)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {userGoalRaterAttributes.commentContentType}, {byteSize(userGoalRaterAttributes.comment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {userGoalRaterAttributes.createdAt ? (
                      <TextFormat type="date" value={userGoalRaterAttributes.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userGoalRaterAttributes.updatedAt ? (
                      <TextFormat type="date" value={userGoalRaterAttributes.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {userGoalRaterAttributes.deletedAt ? (
                      <TextFormat type="date" value={userGoalRaterAttributes.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userGoalRaterAttributes.version}</td>
                  <td>
                    {userGoalRaterAttributes.rating ? (
                      <Link to={`/pc-ratings/${userGoalRaterAttributes.rating.id}`}>{userGoalRaterAttributes.rating.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {userGoalRaterAttributes.usergoal ? (
                      <Link to={`/user-goals/${userGoalRaterAttributes.usergoal.id}`}>{userGoalRaterAttributes.usergoal.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/user-goal-rater-attributes/${userGoalRaterAttributes.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-goal-rater-attributes/${userGoalRaterAttributes.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-goal-rater-attributes/${userGoalRaterAttributes.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No User Goal Rater Attributes found</div>
        )}
      </div>
      {totalItems ? (
        <div className={userGoalRaterAttributesList && userGoalRaterAttributesList.length > 0 ? '' : 'd-none'}>
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

export default UserGoalRaterAttributes;
