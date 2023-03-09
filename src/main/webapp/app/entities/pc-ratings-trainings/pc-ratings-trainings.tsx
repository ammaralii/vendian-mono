import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcRatingsTrainings } from 'app/shared/model/pc-ratings-trainings.model';
import { getEntities } from './pc-ratings-trainings.reducer';

export const PcRatingsTrainings = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const pcRatingsTrainingsList = useAppSelector(state => state.pcRatingsTrainings.entities);
  const loading = useAppSelector(state => state.pcRatingsTrainings.loading);
  const totalItems = useAppSelector(state => state.pcRatingsTrainings.totalItems);

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
      <h2 id="pc-ratings-trainings-heading" data-cy="PcRatingsTrainingsHeading">
        Pc Ratings Trainings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/pc-ratings-trainings/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pc Ratings Trainings
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pcRatingsTrainingsList && pcRatingsTrainingsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('strength')}>
                  Strength <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('developmentArea')}>
                  Development Area <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('careerAmbition')}>
                  Career Ambition <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('shortCourse')}>
                  Short Course <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('technicalTraining')}>
                  Technical Training <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('softSkillsTraining')}>
                  Soft Skills Training <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('criticalPosition')}>
                  Critical Position <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('highPotential')}>
                  High Potential <FontAwesomeIcon icon="sort" />
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
                  Successor For <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rating <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pcRatingsTrainingsList.map((pcRatingsTrainings, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pc-ratings-trainings/${pcRatingsTrainings.id}`} color="link" size="sm">
                      {pcRatingsTrainings.id}
                    </Button>
                  </td>
                  <td>{pcRatingsTrainings.strength}</td>
                  <td>{pcRatingsTrainings.developmentArea}</td>
                  <td>{pcRatingsTrainings.careerAmbition}</td>
                  <td>{pcRatingsTrainings.shortCourse}</td>
                  <td>{pcRatingsTrainings.technicalTraining}</td>
                  <td>{pcRatingsTrainings.softSkillsTraining}</td>
                  <td>{pcRatingsTrainings.criticalPosition ? 'true' : 'false'}</td>
                  <td>{pcRatingsTrainings.highPotential ? 'true' : 'false'}</td>
                  <td>
                    {pcRatingsTrainings.createdAt ? (
                      <TextFormat type="date" value={pcRatingsTrainings.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRatingsTrainings.updatedAt ? (
                      <TextFormat type="date" value={pcRatingsTrainings.updatedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {pcRatingsTrainings.deletedAt ? (
                      <TextFormat type="date" value={pcRatingsTrainings.deletedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pcRatingsTrainings.version}</td>
                  <td>
                    {pcRatingsTrainings.successorFor ? (
                      <Link to={`/employees/${pcRatingsTrainings.successorFor.id}`}>{pcRatingsTrainings.successorFor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pcRatingsTrainings.rating ? (
                      <Link to={`/pc-ratings/${pcRatingsTrainings.rating.id}`}>{pcRatingsTrainings.rating.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/pc-ratings-trainings/${pcRatingsTrainings.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-ratings-trainings/${pcRatingsTrainings.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/pc-ratings-trainings/${pcRatingsTrainings.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Pc Ratings Trainings found</div>
        )}
      </div>
      {totalItems ? (
        <div className={pcRatingsTrainingsList && pcRatingsTrainingsList.length > 0 ? '' : 'd-none'}>
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

export default PcRatingsTrainings;
