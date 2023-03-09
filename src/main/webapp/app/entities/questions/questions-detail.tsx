import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './questions.reducer';

export const QuestionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionsEntity = useAppSelector(state => state.questions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsDetailsHeading">Questions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{questionsEntity.id}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{questionsEntity.text}</dd>
          <dt>
            <span id="answer">Answer</span>
          </dt>
          <dd>{questionsEntity.answer}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {questionsEntity.createdat ? <TextFormat value={questionsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {questionsEntity.updatedat ? <TextFormat value={questionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {questionsEntity.deletedat ? <TextFormat value={questionsEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="cleaneduptext">Cleaneduptext</span>
          </dt>
          <dd>{questionsEntity.cleaneduptext}</dd>
          <dt>Interview</dt>
          <dd>{questionsEntity.interview ? questionsEntity.interview.id : ''}</dd>
          <dt>Project</dt>
          <dd>{questionsEntity.project ? questionsEntity.project.id : ''}</dd>
          <dt>Track</dt>
          <dd>{questionsEntity.track ? questionsEntity.track.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/questions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/questions/${questionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsDetail;
