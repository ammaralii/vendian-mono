import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feedback-emails.reducer';

export const FeedbackEmailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedbackEmailsEntity = useAppSelector(state => state.feedbackEmails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedbackEmailsDetailsHeading">Feedback Emails</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{feedbackEmailsEntity.id}</dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>{feedbackEmailsEntity.to}</dd>
          <dt>
            <span id="body">Body</span>
          </dt>
          <dd>{feedbackEmailsEntity.body}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{feedbackEmailsEntity.status}</dd>
          <dt>
            <span id="sentat">Sentat</span>
          </dt>
          <dd>
            {feedbackEmailsEntity.sentat ? <TextFormat value={feedbackEmailsEntity.sentat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {feedbackEmailsEntity.createdat ? (
              <TextFormat value={feedbackEmailsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {feedbackEmailsEntity.updatedat ? (
              <TextFormat value={feedbackEmailsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Respondent</dt>
          <dd>{feedbackEmailsEntity.respondent ? feedbackEmailsEntity.respondent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/feedback-emails" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feedback-emails/${feedbackEmailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedbackEmailsDetail;
