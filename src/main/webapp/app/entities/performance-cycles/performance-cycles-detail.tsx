import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './performance-cycles.reducer';

export const PerformanceCyclesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const performanceCyclesEntity = useAppSelector(state => state.performanceCycles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="performanceCyclesDetailsHeading">Performance Cycles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{performanceCyclesEntity.id}</dd>
          <dt>
            <span id="month">Month</span>
          </dt>
          <dd>{performanceCyclesEntity.month ? 'true' : 'false'}</dd>
          <dt>
            <span id="year">Year</span>
          </dt>
          <dd>{performanceCyclesEntity.year ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalresources">Totalresources</span>
          </dt>
          <dd>{performanceCyclesEntity.totalresources ? 'true' : 'false'}</dd>
          <dt>
            <span id="pmreviewed">Pmreviewed</span>
          </dt>
          <dd>{performanceCyclesEntity.pmreviewed ? 'true' : 'false'}</dd>
          <dt>
            <span id="archreviewed">Archreviewed</span>
          </dt>
          <dd>{performanceCyclesEntity.archreviewed ? 'true' : 'false'}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {performanceCyclesEntity.startdate ? (
              <TextFormat value={performanceCyclesEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {performanceCyclesEntity.enddate ? (
              <TextFormat value={performanceCyclesEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{performanceCyclesEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {performanceCyclesEntity.createdat ? (
              <TextFormat value={performanceCyclesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {performanceCyclesEntity.updatedat ? (
              <TextFormat value={performanceCyclesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="projectcount">Projectcount</span>
          </dt>
          <dd>{performanceCyclesEntity.projectcount ? 'true' : 'false'}</dd>
          <dt>
            <span id="criteria">Criteria</span>
          </dt>
          <dd>{performanceCyclesEntity.criteria}</dd>
          <dt>
            <span id="notificationsent">Notificationsent</span>
          </dt>
          <dd>{performanceCyclesEntity.notificationsent ? 'true' : 'false'}</dd>
          <dt>
            <span id="duedate">Duedate</span>
          </dt>
          <dd>
            {performanceCyclesEntity.duedate ? (
              <TextFormat value={performanceCyclesEntity.duedate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="initiationdate">Initiationdate</span>
          </dt>
          <dd>
            {performanceCyclesEntity.initiationdate ? (
              <TextFormat value={performanceCyclesEntity.initiationdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Projectcycle</dt>
          <dd>
            {performanceCyclesEntity.projectcycles
              ? performanceCyclesEntity.projectcycles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {performanceCyclesEntity.projectcycles && i === performanceCyclesEntity.projectcycles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Employeerating</dt>
          <dd>
            {performanceCyclesEntity.employeeratings
              ? performanceCyclesEntity.employeeratings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {performanceCyclesEntity.employeeratings && i === performanceCyclesEntity.employeeratings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/performance-cycles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/performance-cycles/${performanceCyclesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PerformanceCyclesDetail;
