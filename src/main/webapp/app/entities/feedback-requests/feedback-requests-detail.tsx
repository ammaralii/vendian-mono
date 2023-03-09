import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feedback-requests.reducer';

export const FeedbackRequestsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedbackRequestsEntity = useAppSelector(state => state.feedbackRequests.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedbackRequestsDetailsHeading">Feedback Requests</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{feedbackRequestsEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{feedbackRequestsEntity.status}</dd>
          <dt>
            <span id="isreportavailable">Isreportavailable</span>
          </dt>
          <dd>{feedbackRequestsEntity.isreportavailable ? 'true' : 'false'}</dd>
          <dt>
            <span id="reportpath">Reportpath</span>
          </dt>
          <dd>{feedbackRequestsEntity.reportpath}</dd>
          <dt>
            <span id="approvedat">Approvedat</span>
          </dt>
          <dd>
            {feedbackRequestsEntity.approvedat ? (
              <TextFormat value={feedbackRequestsEntity.approvedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expiredat">Expiredat</span>
          </dt>
          <dd>
            {feedbackRequestsEntity.expiredat ? (
              <TextFormat value={feedbackRequestsEntity.expiredat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {feedbackRequestsEntity.createdat ? (
              <TextFormat value={feedbackRequestsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {feedbackRequestsEntity.updatedat ? (
              <TextFormat value={feedbackRequestsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{feedbackRequestsEntity.employee ? feedbackRequestsEntity.employee.id : ''}</dd>
          <dt>Linemanager</dt>
          <dd>{feedbackRequestsEntity.linemanager ? feedbackRequestsEntity.linemanager.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/feedback-requests" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feedback-requests/${feedbackRequestsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedbackRequestsDetail;
