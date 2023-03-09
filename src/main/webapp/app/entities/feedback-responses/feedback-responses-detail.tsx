import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './feedback-responses.reducer';

export const FeedbackResponsesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const feedbackResponsesEntity = useAppSelector(state => state.feedbackResponses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="feedbackResponsesDetailsHeading">Feedback Responses</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{feedbackResponsesEntity.id}</dd>
          <dt>
            <span id="answer">Answer</span>
          </dt>
          <dd>
            {feedbackResponsesEntity.answer ? (
              <div>
                {feedbackResponsesEntity.answerContentType ? (
                  <a onClick={openFile(feedbackResponsesEntity.answerContentType, feedbackResponsesEntity.answer)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {feedbackResponsesEntity.answerContentType}, {byteSize(feedbackResponsesEntity.answer)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>
            {feedbackResponsesEntity.rating ? (
              <div>
                {feedbackResponsesEntity.ratingContentType ? (
                  <a onClick={openFile(feedbackResponsesEntity.ratingContentType, feedbackResponsesEntity.rating)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {feedbackResponsesEntity.ratingContentType}, {byteSize(feedbackResponsesEntity.rating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {feedbackResponsesEntity.createdat ? (
              <TextFormat value={feedbackResponsesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {feedbackResponsesEntity.updatedat ? (
              <TextFormat value={feedbackResponsesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Respondent</dt>
          <dd>{feedbackResponsesEntity.respondent ? feedbackResponsesEntity.respondent.id : ''}</dd>
          <dt>Question</dt>
          <dd>{feedbackResponsesEntity.question ? feedbackResponsesEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/feedback-responses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/feedback-responses/${feedbackResponsesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FeedbackResponsesDetail;
