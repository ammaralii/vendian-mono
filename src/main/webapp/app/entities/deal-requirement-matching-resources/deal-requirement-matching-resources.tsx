import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { getEntities } from './deal-requirement-matching-resources.reducer';

export const DealRequirementMatchingResources = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const dealRequirementMatchingResourcesList = useAppSelector(state => state.dealRequirementMatchingResources.entities);
  const loading = useAppSelector(state => state.dealRequirementMatchingResources.loading);
  const totalItems = useAppSelector(state => state.dealRequirementMatchingResources.totalItems);

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
      <h2 id="deal-requirement-matching-resources-heading" data-cy="DealRequirementMatchingResourcesHeading">
        Deal Requirement Matching Resources
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/deal-requirement-matching-resources/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Deal Requirement Matching Resources
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dealRequirementMatchingResourcesList && dealRequirementMatchingResourcesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  Comments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deletedat')}>
                  Deletedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Dealrequirement <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Resource <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Resourcestatus <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Systemstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dealRequirementMatchingResourcesList.map((dealRequirementMatchingResources, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/deal-requirement-matching-resources/${dealRequirementMatchingResources.id}`}
                      color="link"
                      size="sm"
                    >
                      {dealRequirementMatchingResources.id}
                    </Button>
                  </td>
                  <td>{dealRequirementMatchingResources.comments}</td>
                  <td>
                    {dealRequirementMatchingResources.createdat ? (
                      <TextFormat type="date" value={dealRequirementMatchingResources.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.updatedat ? (
                      <TextFormat type="date" value={dealRequirementMatchingResources.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.deletedat ? (
                      <TextFormat type="date" value={dealRequirementMatchingResources.deletedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.dealrequirement ? (
                      <Link to={`/deal-requirements/${dealRequirementMatchingResources.dealrequirement.id}`}>
                        {dealRequirementMatchingResources.dealrequirement.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.resource ? (
                      <Link to={`/deal-resources/${dealRequirementMatchingResources.resource.id}`}>
                        {dealRequirementMatchingResources.resource.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.resourcestatus ? (
                      <Link to={`/deal-resource-status/${dealRequirementMatchingResources.resourcestatus.id}`}>
                        {dealRequirementMatchingResources.resourcestatus.systemKey}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dealRequirementMatchingResources.systemstatus ? (
                      <Link to={`/deal-resource-status/${dealRequirementMatchingResources.systemstatus.id}`}>
                        {dealRequirementMatchingResources.systemstatus.systemKey}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/deal-requirement-matching-resources/${dealRequirementMatchingResources.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/deal-requirement-matching-resources/${dealRequirementMatchingResources.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/deal-requirement-matching-resources/${dealRequirementMatchingResources.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Deal Requirement Matching Resources found</div>
        )}
      </div>
      {totalItems ? (
        <div className={dealRequirementMatchingResourcesList && dealRequirementMatchingResourcesList.length > 0 ? '' : 'd-none'}>
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

export default DealRequirementMatchingResources;