import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcRaterAttributes } from 'app/shared/model/pc-rater-attributes.model';
import { getEntities } from './pc-rater-attributes.reducer';

export const PcRaterAttributes = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pcRaterAttributesList = useAppSelector(state => state.pcRaterAttributes.entities);
  const loading = useAppSelector(state => state.pcRaterAttributes.loading);
  const totalItems = useAppSelector(state => state.pcRaterAttributes.totalItems);

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
      <h2 id="pc-rater-attributes-heading" data-cy="PcRaterAttributesHeading">
        Pc Rater Attributes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/pc-rater-attributes/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pc Rater Attributes
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pcRaterAttributesList && pcRaterAttributesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pcRating')}>
                  Pc Rating <FontAwesomeIcon icon="sort" />
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
                  Rating Attribute Mapping <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rating Option <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pcRaterAttributesList.map((pcRaterAttributes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pc-rater-attributes/${pcRaterAttributes.id}`} color="link" size="sm">
                      {pcRaterAttributes.id}
                    </Button>
                  </td>
                  <td>
                    {pcRaterAttributes.pcRating ? (
                      <div>
                        {pcRaterAttributes.pcRatingContentType ? (
                          <a onClick={openFile(pcRaterAttributes.pcRatingContentType, pcRaterAttributes.pcRating)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {pcRaterAttributes.pcRatingContentType}, {byteSize(pcRaterAttributes.pcRating)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {pcRaterAttributes.comment ? (
                      <div>
                        {pcRaterAttributes.commentContentType ? (
                          <a onClick={openFile(pcRaterAttributes.commentContentType, pcRaterAttributes.comment)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {pcRaterAttributes.commentContentType}, {byteSize(pcRaterAttributes.comment)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {pcRaterAttributes.createdAt ? (
                      <TextFormat type="date" value={pcRaterAttributes.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRaterAttributes.updatedAt ? (
                      <TextFormat type="date" value={pcRaterAttributes.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRaterAttributes.deletedAt ? (
                      <TextFormat type="date" value={pcRaterAttributes.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pcRaterAttributes.version}</td>
                  <td>
                    {pcRaterAttributes.ratingAttributeMapping ? (
                      <Link to={`/rating-attribute-mappings/${pcRaterAttributes.ratingAttributeMapping.id}`}>
                        {pcRaterAttributes.ratingAttributeMapping.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pcRaterAttributes.ratingOption ? (
                      <Link to={`/rating-options/${pcRaterAttributes.ratingOption.id}`}>{pcRaterAttributes.ratingOption.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pcRaterAttributes.rating ? (
                      <Link to={`/pc-ratings/${pcRaterAttributes.rating.id}`}>{pcRaterAttributes.rating.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/pc-rater-attributes/${pcRaterAttributes.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-rater-attributes/${pcRaterAttributes.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-rater-attributes/${pcRaterAttributes.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Pc Rater Attributes found</div>
        )}
      </div>
      {totalItems ? (
        <div className={pcRaterAttributesList && pcRaterAttributesList.length > 0 ? '' : 'd-none'}>
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

export default PcRaterAttributes;
