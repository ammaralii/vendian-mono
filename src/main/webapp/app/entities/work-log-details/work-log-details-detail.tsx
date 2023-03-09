import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './work-log-details.reducer';

export const WorkLogDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workLogDetailsEntity = useAppSelector(state => state.workLogDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workLogDetailsDetailsHeading">Work Log Details</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{workLogDetailsEntity.id}</dd>
          <dt>
            <span id="percentage">Percentage</span>
          </dt>
          <dd>{workLogDetailsEntity.percentage}</dd>
          <dt>
            <span id="hours">Hours</span>
          </dt>
          <dd>{workLogDetailsEntity.hours}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {workLogDetailsEntity.createdat ? (
              <TextFormat value={workLogDetailsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {workLogDetailsEntity.updatedat ? (
              <TextFormat value={workLogDetailsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Worklog</dt>
          <dd>{workLogDetailsEntity.worklog ? workLogDetailsEntity.worklog.id : ''}</dd>
          <dt>Project</dt>
          <dd>{workLogDetailsEntity.project ? workLogDetailsEntity.project.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/work-log-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/work-log-details/${workLogDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkLogDetailsDetail;
