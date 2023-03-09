import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './work-logs.reducer';

export const WorkLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workLogsEntity = useAppSelector(state => state.workLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workLogsDetailsHeading">Work Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{workLogsEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{workLogsEntity.date ? <TextFormat value={workLogsEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="mood">Mood</span>
          </dt>
          <dd>{workLogsEntity.mood}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{workLogsEntity.createdat ? <TextFormat value={workLogsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{workLogsEntity.updatedat ? <TextFormat value={workLogsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Employee</dt>
          <dd>{workLogsEntity.employee ? workLogsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/work-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/work-logs/${workLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkLogsDetail;
