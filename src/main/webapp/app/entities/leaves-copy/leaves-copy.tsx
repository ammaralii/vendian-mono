import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeavesCopy } from 'app/shared/model/leaves-copy.model';
import { getEntities } from './leaves-copy.reducer';

export const LeavesCopy = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leavesCopyList = useAppSelector(state => state.leavesCopy.entities);
  const loading = useAppSelector(state => state.leavesCopy.loading);
  const totalItems = useAppSelector(state => state.leavesCopy.totalItems);

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
      <h2 id="leaves-copy-heading" data-cy="LeavesCopyHeading">
        Leaves Copies
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/leaves-copy/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leaves Copy
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leavesCopyList && leavesCopyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('annualtotal')}>
                  Annualtotal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('annualused')}>
                  Annualused <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('annualadjustments')}>
                  Annualadjustments <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('casualtotal')}>
                  Casualtotal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('casualused')}>
                  Casualused <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sicktotal')}>
                  Sicktotal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sickused')}>
                  Sickused <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('annualtotalnextyear')}>
                  Annualtotalnextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('annualusednextyear')}>
                  Annualusednextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('casualtotalnextyear')}>
                  Casualtotalnextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('casualusednextyear')}>
                  Casualusednextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sicktotalnextyear')}>
                  Sicktotalnextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sickusednextyear')}>
                  Sickusednextyear <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('carryforward')}>
                  Carryforward <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leavesCopyList.map((leavesCopy, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leaves-copy/${leavesCopy.id}`} color="link" size="sm">
                      {leavesCopy.id}
                    </Button>
                  </td>
                  <td>{leavesCopy.annualtotal}</td>
                  <td>{leavesCopy.annualused}</td>
                  <td>{leavesCopy.annualadjustments}</td>
                  <td>{leavesCopy.casualtotal}</td>
                  <td>{leavesCopy.casualused}</td>
                  <td>{leavesCopy.sicktotal}</td>
                  <td>{leavesCopy.sickused}</td>
                  <td>{leavesCopy.annualtotalnextyear}</td>
                  <td>{leavesCopy.annualusednextyear}</td>
                  <td>{leavesCopy.casualtotalnextyear}</td>
                  <td>{leavesCopy.casualusednextyear}</td>
                  <td>{leavesCopy.sicktotalnextyear}</td>
                  <td>{leavesCopy.sickusednextyear}</td>
                  <td>{leavesCopy.carryforward}</td>
                  <td>{leavesCopy.createdat ? <TextFormat type="date" value={leavesCopy.createdat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{leavesCopy.updatedat ? <TextFormat type="date" value={leavesCopy.updatedat} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/leaves-copy/${leavesCopy.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leaves-copy/${leavesCopy.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leaves-copy/${leavesCopy.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leaves Copies found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leavesCopyList && leavesCopyList.length > 0 ? '' : 'd-none'}>
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

export default LeavesCopy;
