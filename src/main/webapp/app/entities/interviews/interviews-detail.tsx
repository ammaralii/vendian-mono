import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './interviews.reducer';

export const InterviewsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const interviewsEntity = useAppSelector(state => state.interviews.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="interviewsDetailsHeading">Interviews</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{interviewsEntity.id}</dd>
          <dt>
            <span id="result">Result</span>
          </dt>
          <dd>{interviewsEntity.result}</dd>
          <dt>
            <span id="clientcomments">Clientcomments</span>
          </dt>
          <dd>{interviewsEntity.clientcomments}</dd>
          <dt>
            <span id="lmcomments">Lmcomments</span>
          </dt>
          <dd>{interviewsEntity.lmcomments}</dd>
          <dt>
            <span id="scheduledat">Scheduledat</span>
          </dt>
          <dd>
            {interviewsEntity.scheduledat ? <TextFormat value={interviewsEntity.scheduledat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {interviewsEntity.createdat ? <TextFormat value={interviewsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {interviewsEntity.updatedat ? <TextFormat value={interviewsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {interviewsEntity.deletedat ? <TextFormat value={interviewsEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Employee</dt>
          <dd>{interviewsEntity.employee ? interviewsEntity.employee.id : ''}</dd>
          <dt>Project</dt>
          <dd>{interviewsEntity.project ? interviewsEntity.project.id : ''}</dd>
          <dt>Track</dt>
          <dd>{interviewsEntity.track ? interviewsEntity.track.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/interviews" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interviews/${interviewsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InterviewsDetail;
