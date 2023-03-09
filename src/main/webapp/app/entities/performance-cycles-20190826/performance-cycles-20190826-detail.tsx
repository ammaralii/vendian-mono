import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './performance-cycles-20190826.reducer';

export const PerformanceCycles20190826Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const performanceCycles20190826Entity = useAppSelector(state => state.performanceCycles20190826.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="performanceCycles20190826DetailsHeading">Performance Cycles 20190826</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{performanceCycles20190826Entity.id}</dd>
          <dt>
            <span id="month">Month</span>
          </dt>
          <dd>{performanceCycles20190826Entity.month ? 'true' : 'false'}</dd>
          <dt>
            <span id="year">Year</span>
          </dt>
          <dd>{performanceCycles20190826Entity.year ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalresources">Totalresources</span>
          </dt>
          <dd>{performanceCycles20190826Entity.totalresources ? 'true' : 'false'}</dd>
          <dt>
            <span id="pmreviewed">Pmreviewed</span>
          </dt>
          <dd>{performanceCycles20190826Entity.pmreviewed ? 'true' : 'false'}</dd>
          <dt>
            <span id="archreviewed">Archreviewed</span>
          </dt>
          <dd>{performanceCycles20190826Entity.archreviewed ? 'true' : 'false'}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.startdate ? (
              <TextFormat value={performanceCycles20190826Entity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.enddate ? (
              <TextFormat value={performanceCycles20190826Entity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{performanceCycles20190826Entity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.createdat ? (
              <TextFormat value={performanceCycles20190826Entity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.updatedat ? (
              <TextFormat value={performanceCycles20190826Entity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="projectcount">Projectcount</span>
          </dt>
          <dd>{performanceCycles20190826Entity.projectcount ? 'true' : 'false'}</dd>
          <dt>
            <span id="criteria">Criteria</span>
          </dt>
          <dd>{performanceCycles20190826Entity.criteria}</dd>
          <dt>
            <span id="notificationsent">Notificationsent</span>
          </dt>
          <dd>{performanceCycles20190826Entity.notificationsent ? 'true' : 'false'}</dd>
          <dt>
            <span id="duedate">Duedate</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.duedate ? (
              <TextFormat value={performanceCycles20190826Entity.duedate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="initiationdate">Initiationdate</span>
          </dt>
          <dd>
            {performanceCycles20190826Entity.initiationdate ? (
              <TextFormat value={performanceCycles20190826Entity.initiationdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/performance-cycles-20190826" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/performance-cycles-20190826/${performanceCycles20190826Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PerformanceCycles20190826Detail;
