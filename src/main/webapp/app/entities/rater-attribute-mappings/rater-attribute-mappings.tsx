import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';
import { getEntities } from './rater-attribute-mappings.reducer';

export const RaterAttributeMappings = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const raterAttributeMappingsList = useAppSelector(state => state.raterAttributeMappings.entities);
  const loading = useAppSelector(state => state.raterAttributeMappings.loading);
  const totalItems = useAppSelector(state => state.raterAttributeMappings.totalItems);

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
      <h2 id="rater-attribute-mappings-heading" data-cy="RaterAttributeMappingsHeading">
        Rater Attribute Mappings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/rater-attribute-mappings/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Rater Attribute Mappings
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {raterAttributeMappingsList && raterAttributeMappingsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('effdate')}>
                  Effdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Raterattribute <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Attributes For <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Attributes By <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {raterAttributeMappingsList.map((raterAttributeMappings, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rater-attribute-mappings/${raterAttributeMappings.id}`} color="link" size="sm">
                      {raterAttributeMappings.id}
                    </Button>
                  </td>
                  <td>
                    {raterAttributeMappings.effdate ? (
                      <TextFormat type="date" value={raterAttributeMappings.effdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {raterAttributeMappings.enddate ? (
                      <TextFormat type="date" value={raterAttributeMappings.enddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {raterAttributeMappings.createdat ? (
                      <TextFormat type="date" value={raterAttributeMappings.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {raterAttributeMappings.updatedat ? (
                      <TextFormat type="date" value={raterAttributeMappings.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {raterAttributeMappings.raterattribute ? (
                      <Link to={`/rater-attributes/${raterAttributeMappings.raterattribute.id}`}>
                        {raterAttributeMappings.raterattribute.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {raterAttributeMappings.attributesFor ? (
                      <Link to={`/attributes/${raterAttributeMappings.attributesFor.id}`}>{raterAttributeMappings.attributesFor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {raterAttributeMappings.attributesBy ? (
                      <Link to={`/attributes/${raterAttributeMappings.attributesBy.id}`}>{raterAttributeMappings.attributesBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/rater-attribute-mappings/${raterAttributeMappings.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rater-attribute-mappings/${raterAttributeMappings.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rater-attribute-mappings/${raterAttributeMappings.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Rater Attribute Mappings found</div>
        )}
      </div>
      {totalItems ? (
        <div className={raterAttributeMappingsList && raterAttributeMappingsList.length > 0 ? '' : 'd-none'}>
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

export default RaterAttributeMappings;
