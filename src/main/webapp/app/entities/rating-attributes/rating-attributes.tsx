import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRatingAttributes } from 'app/shared/model/rating-attributes.model';
import { getEntities } from './rating-attributes.reducer';

export const RatingAttributes = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const ratingAttributesList = useAppSelector(state => state.ratingAttributes.entities);
  const loading = useAppSelector(state => state.ratingAttributes.loading);
  const totalItems = useAppSelector(state => state.ratingAttributes.totalItems);

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
      <h2 id="rating-attributes-heading" data-cy="RatingAttributesHeading">
        Rating Attributes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/rating-attributes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Rating Attributes
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ratingAttributesList && ratingAttributesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('raRating')}>
                  Ra Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  Comment <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Raterattributemapping <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ratingAttributesList.map((ratingAttributes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rating-attributes/${ratingAttributes.id}`} color="link" size="sm">
                      {ratingAttributes.id}
                    </Button>
                  </td>
                  <td>
                    {ratingAttributes.raRating ? (
                      <div>
                        {ratingAttributes.raRatingContentType ? (
                          <a onClick={openFile(ratingAttributes.raRatingContentType, ratingAttributes.raRating)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {ratingAttributes.raRatingContentType}, {byteSize(ratingAttributes.raRating)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {ratingAttributes.comment ? (
                      <div>
                        {ratingAttributes.commentContentType ? (
                          <a onClick={openFile(ratingAttributes.commentContentType, ratingAttributes.comment)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {ratingAttributes.commentContentType}, {byteSize(ratingAttributes.comment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {ratingAttributes.createdat ? (
                      <TextFormat type="date" value={ratingAttributes.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {ratingAttributes.updatedat ? (
                      <TextFormat type="date" value={ratingAttributes.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {ratingAttributes.rating ? <Link to={`/ratings/${ratingAttributes.rating.id}`}>{ratingAttributes.rating.id}</Link> : ''}
                  </td>
                  <td>
                    {ratingAttributes.raterattributemapping ? (
                      <Link to={`/rater-attribute-mappings/${ratingAttributes.raterattributemapping.id}`}>
                        {ratingAttributes.raterattributemapping.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/rating-attributes/${ratingAttributes.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rating-attributes/${ratingAttributes.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rating-attributes/${ratingAttributes.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Rating Attributes found</div>
        )}
      </div>
      {totalItems ? (
        <div className={ratingAttributesList && ratingAttributesList.length > 0 ? '' : 'd-none'}>
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

export default RatingAttributes;
