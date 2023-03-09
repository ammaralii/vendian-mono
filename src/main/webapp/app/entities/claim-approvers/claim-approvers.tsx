import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClaimApprovers } from 'app/shared/model/claim-approvers.model';
import { getEntities } from './claim-approvers.reducer';

export const ClaimApprovers = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const claimApproversList = useAppSelector(state => state.claimApprovers.entities);
  const loading = useAppSelector(state => state.claimApprovers.loading);
  const totalItems = useAppSelector(state => state.claimApprovers.totalItems);

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
      <h2 id="claim-approvers-heading" data-cy="ClaimApproversHeading">
        Claim Approvers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/claim-approvers/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Claim Approvers
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {claimApproversList && claimApproversList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('referenceid')}>
                  Referenceid <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('designation')}>
                  Designation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approveorder')}>
                  Approveorder <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reference')}>
                  Reference <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  Comments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedby')}>
                  Approvedby <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Claimrequest <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {claimApproversList.map((claimApprovers, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/claim-approvers/${claimApprovers.id}`} color="link" size="sm">
                      {claimApprovers.id}
                    </Button>
                  </td>
                  <td>{claimApprovers.referenceid}</td>
                  <td>{claimApprovers.designation}</td>
                  <td>{claimApprovers.approveorder}</td>
                  <td>{claimApprovers.reference}</td>
                  <td>{claimApprovers.comments}</td>
                  <td>{claimApprovers.approvedby}</td>
                  <td>
                    {claimApprovers.createdat ? <TextFormat type="date" value={claimApprovers.createdat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {claimApprovers.updatedat ? <TextFormat type="date" value={claimApprovers.updatedat} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {claimApprovers.status ? <Link to={`/claim-status/${claimApprovers.status.id}`}>{claimApprovers.status.id}</Link> : ''}
                  </td>
                  <td>
                    {claimApprovers.claimrequest ? (
                      <Link to={`/claim-requests/${claimApprovers.claimrequest.id}`}>{claimApprovers.claimrequest.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/claim-approvers/${claimApprovers.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/claim-approvers/${claimApprovers.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/claim-approvers/${claimApprovers.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Claim Approvers found</div>
        )}
      </div>
      {totalItems ? (
        <div className={claimApproversList && claimApproversList.length > 0 ? '' : 'd-none'}>
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

export default ClaimApprovers;
