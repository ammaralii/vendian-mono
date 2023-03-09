import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './questions-frequency-per-track.reducer';

export const QuestionsFrequencyPerTrackDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionsFrequencyPerTrackEntity = useAppSelector(state => state.questionsFrequencyPerTrack.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsFrequencyPerTrackDetailsHeading">Questions Frequency Per Track</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{questionsFrequencyPerTrackEntity.id}</dd>
          <dt>
            <span id="question">Question</span>
          </dt>
          <dd>{questionsFrequencyPerTrackEntity.question}</dd>
          <dt>
            <span id="frequency">Frequency</span>
          </dt>
          <dd>{questionsFrequencyPerTrackEntity.frequency}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {questionsFrequencyPerTrackEntity.createdat ? (
              <TextFormat value={questionsFrequencyPerTrackEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {questionsFrequencyPerTrackEntity.updatedat ? (
              <TextFormat value={questionsFrequencyPerTrackEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Track</dt>
          <dd>{questionsFrequencyPerTrackEntity.track ? questionsFrequencyPerTrackEntity.track.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/questions-frequency-per-track" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/questions-frequency-per-track/${questionsFrequencyPerTrackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsFrequencyPerTrackDetail;
