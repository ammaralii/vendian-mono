import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcRatingAttributesCategories } from 'app/shared/model/pc-rating-attributes-categories.model';
import { getEntities } from './pc-rating-attributes-categories.reducer';

export const PcRatingAttributesCategories = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pcRatingAttributesCategoriesList = useAppSelector(state => state.pcRatingAttributesCategories.entities);
  const loading = useAppSelector(state => state.pcRatingAttributesCategories.loading);
  const totalItems = useAppSelector(state => state.pcRatingAttributesCategories.totalItems);

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
      <h2 id="pc-rating-attributes-categories-heading" data-cy="PcRatingAttributesCategoriesHeading">
        Pc Rating Attributes Categories
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/pc-rating-attributes-categories/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pc Rating Attributes Categories
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pcRatingAttributesCategoriesList && pcRatingAttributesCategoriesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('effDate')}>
                  Eff Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  Created At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  Updated At <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  End Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('version')}>
                  Version <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Category <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rating Attribute <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pcRatingAttributesCategoriesList.map((pcRatingAttributesCategories, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pc-rating-attributes-categories/${pcRatingAttributesCategories.id}`} color="link" size="sm">
                      {pcRatingAttributesCategories.id}
                    </Button>
                  </td>
                  <td>
                    {pcRatingAttributesCategories.effDate ? (
                      <TextFormat type="date" value={pcRatingAttributesCategories.effDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRatingAttributesCategories.createdAt ? (
                      <TextFormat type="date" value={pcRatingAttributesCategories.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRatingAttributesCategories.updatedAt ? (
                      <TextFormat type="date" value={pcRatingAttributesCategories.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRatingAttributesCategories.endDate ? (
                      <TextFormat type="date" value={pcRatingAttributesCategories.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pcRatingAttributesCategories.version}</td>
                  <td>
                    {pcRatingAttributesCategories.category ? (
                      <Link to={`/rating-categories/${pcRatingAttributesCategories.category.id}`}>
                        {pcRatingAttributesCategories.category.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pcRatingAttributesCategories.ratingAttribute ? (
                      <Link to={`/pc-rating-attributes/${pcRatingAttributesCategories.ratingAttribute.id}`}>
                        {pcRatingAttributesCategories.ratingAttribute.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/pc-rating-attributes-categories/${pcRatingAttributesCategories.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-rating-attributes-categories/${pcRatingAttributesCategories.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-rating-attributes-categories/${pcRatingAttributesCategories.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Pc Rating Attributes Categories found</div>
        )}
      </div>
      {totalItems ? (
        <div className={pcRatingAttributesCategoriesList && pcRatingAttributesCategoriesList.length > 0 ? '' : 'd-none'}>
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

export default PcRatingAttributesCategories;
