import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feedback-questions.reducer';

export const FeedbackQuestionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedbackQuestionsEntity = useAppSelector(state => state.feedbackQuestions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedbackQuestionsDetailsHeading">Feedback Questions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{feedbackQuestionsEntity.id}</dd>
          <dt>
            <span id="question">Question</span>
          </dt>
          <dd>{feedbackQuestionsEntity.question}</dd>
          <dt>
            <span id="isdefault">Isdefault</span>
          </dt>
          <dd>{feedbackQuestionsEntity.isdefault ? 'true' : 'false'}</dd>
          <dt>
            <span id="area">Area</span>
          </dt>
          <dd>{feedbackQuestionsEntity.area}</dd>
          <dt>
            <span id="competency">Competency</span>
          </dt>
          <dd>{feedbackQuestionsEntity.competency}</dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{feedbackQuestionsEntity.category}</dd>
          <dt>
            <span id="isskill">Isskill</span>
          </dt>
          <dd>{feedbackQuestionsEntity.isskill ? 'true' : 'false'}</dd>
          <dt>
            <span id="skilltype">Skilltype</span>
          </dt>
          <dd>{feedbackQuestionsEntity.skilltype}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {feedbackQuestionsEntity.createdat ? (
              <TextFormat value={feedbackQuestionsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {feedbackQuestionsEntity.updatedat ? (
              <TextFormat value={feedbackQuestionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{feedbackQuestionsEntity.employee ? feedbackQuestionsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/feedback-questions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feedback-questions/${feedbackQuestionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedbackQuestionsDetail;
