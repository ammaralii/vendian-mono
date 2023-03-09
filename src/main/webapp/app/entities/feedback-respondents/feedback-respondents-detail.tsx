import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feedback-respondents.reducer';

export const FeedbackRespondentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedbackRespondentsEntity = useAppSelector(state => state.feedbackRespondents.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedbackRespondentsDetailsHeading">Feedback Respondents</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{feedbackRespondentsEntity.id}</dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{feedbackRespondentsEntity.category}</dd>
          <dt>
            <span id="hasresponded">Hasresponded</span>
          </dt>
          <dd>{feedbackRespondentsEntity.hasresponded ? 'true' : 'false'}</dd>
          <dt>
            <span id="respondedat">Respondedat</span>
          </dt>
          <dd>
            {feedbackRespondentsEntity.respondedat ? (
              <TextFormat value={feedbackRespondentsEntity.respondedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {feedbackRespondentsEntity.createdat ? (
              <TextFormat value={feedbackRespondentsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {feedbackRespondentsEntity.updatedat ? (
              <TextFormat value={feedbackRespondentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{feedbackRespondentsEntity.employee ? feedbackRespondentsEntity.employee.id : ''}</dd>
          <dt>Request</dt>
          <dd>{feedbackRespondentsEntity.request ? feedbackRespondentsEntity.request.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/feedback-respondents" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feedback-respondents/${feedbackRespondentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedbackRespondentsDetail;
