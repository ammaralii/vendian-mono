import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveRequestsOlds } from 'app/shared/model/leave-requests-olds.model';
import { getEntities } from './leave-requests-olds.reducer';

export const LeaveRequestsOlds = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const leaveRequestsOldsList = useAppSelector(state => state.leaveRequestsOlds.entities);
  const loading = useAppSelector(state => state.leaveRequestsOlds.loading);
  const totalItems = useAppSelector(state => state.leaveRequestsOlds.totalItems);

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
      <h2 id="leave-requests-olds-heading" data-cy="LeaveRequestsOldsHeading">
        Leave Requests Olds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/leave-requests-olds/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leave Requests Olds
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leaveRequestsOldsList && leaveRequestsOldsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startdate')}>
                  Startdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enddate')}>
                  Enddate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requesternote')}>
                  Requesternote <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('managernote')}>
                  Managernote <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentstatus')}>
                  Currentstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leavescanceled')}>
                  Leavescanceled <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestdate')}>
                  Requestdate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkstring')}>
                  Linkstring <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkused')}>
                  Linkused <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdat')}>
                  Createdat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedat')}>
                  Updatedat <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ishalfday')}>
                  Ishalfday <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('actiondate')}>
                  Actiondate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lmstatus')}>
                  Lmstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pmstatus')}>
                  Pmstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isbench')}>
                  Isbench <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isescalated')}>
                  Isescalated <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('iscommented')}>
                  Iscommented <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isreminded')}>
                  Isreminded <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isnotified')}>
                  Isnotified <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isremindedhr')}>
                  Isremindedhr <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isdm')}>
                  Isdm <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Leavetype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Employee <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveRequestsOldsList.map((leaveRequestsOlds, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leave-requests-olds/${leaveRequestsOlds.id}`} color="link" size="sm">
                      {leaveRequestsOlds.id}
                    </Button>
                  </td>
                  <td>
                    {leaveRequestsOlds.startdate ? (
                      <TextFormat type="date" value={leaveRequestsOlds.startdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequestsOlds.enddate ? (
                      <TextFormat type="date" value={leaveRequestsOlds.enddate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequestsOlds.requesternote}</td>
                  <td>{leaveRequestsOlds.managernote}</td>
                  <td>{leaveRequestsOlds.currentstatus}</td>
                  <td>{leaveRequestsOlds.leavescanceled ? 'true' : 'false'}</td>
                  <td>
                    {leaveRequestsOlds.requestdate ? (
                      <TextFormat type="date" value={leaveRequestsOlds.requestdate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequestsOlds.linkstring}</td>
                  <td>{leaveRequestsOlds.linkused ? 'true' : 'false'}</td>
                  <td>
                    {leaveRequestsOlds.createdat ? (
                      <TextFormat type="date" value={leaveRequestsOlds.createdat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {leaveRequestsOlds.updatedat ? (
                      <TextFormat type="date" value={leaveRequestsOlds.updatedat} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequestsOlds.ishalfday ? 'true' : 'false'}</td>
                  <td>
                    {leaveRequestsOlds.actiondate ? (
                      <TextFormat type="date" value={leaveRequestsOlds.actiondate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{leaveRequestsOlds.lmstatus}</td>
                  <td>{leaveRequestsOlds.pmstatus}</td>
                  <td>{leaveRequestsOlds.isbench ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.isescalated ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.iscommented ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.isreminded ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.isnotified ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.isremindedhr ? 'true' : 'false'}</td>
                  <td>{leaveRequestsOlds.isdm ? 'true' : 'false'}</td>
                  <td>
                    {leaveRequestsOlds.leavetype ? (
                      <Link to={`/leave-types-olds/${leaveRequestsOlds.leavetype.id}`}>{leaveRequestsOlds.leavetype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequestsOlds.manager ? (
                      <Link to={`/employees/${leaveRequestsOlds.manager.id}`}>{leaveRequestsOlds.manager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {leaveRequestsOlds.employee ? (
                      <Link to={`/employees/${leaveRequestsOlds.employee.id}`}>{leaveRequestsOlds.employee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/leave-requests-olds/${leaveRequestsOlds.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-requests-olds/${leaveRequestsOlds.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/leave-requests-olds/${leaveRequestsOlds.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Leave Requests Olds found</div>
        )}
      </div>
      {totalItems ? (
        <div className={leaveRequestsOldsList && leaveRequestsOldsList.length > 0 ? '' : 'd-none'}>
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

export default LeaveRequestsOlds;
