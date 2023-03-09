import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hr-performance-cycles.reducer';

export const HrPerformanceCyclesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hrPerformanceCyclesEntity = useAppSelector(state => state.hrPerformanceCycles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hrPerformanceCyclesDetailsHeading">Hr Performance Cycles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{hrPerformanceCyclesEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{hrPerformanceCyclesEntity.title}</dd>
          <dt>
            <span id="freeze">Freeze</span>
          </dt>
          <dd>{hrPerformanceCyclesEntity.freeze ? 'true' : 'false'}</dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.dueDate ? (
              <TextFormat value={hrPerformanceCyclesEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.startDate ? (
              <TextFormat value={hrPerformanceCyclesEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.endDate ? (
              <TextFormat value={hrPerformanceCyclesEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.createdAt ? (
              <TextFormat value={hrPerformanceCyclesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.updatedAt ? (
              <TextFormat value={hrPerformanceCyclesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {hrPerformanceCyclesEntity.deletedAt ? (
              <TextFormat value={hrPerformanceCyclesEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{hrPerformanceCyclesEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/hr-performance-cycles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hr-performance-cycles/${hrPerformanceCyclesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HrPerformanceCyclesDetail;
