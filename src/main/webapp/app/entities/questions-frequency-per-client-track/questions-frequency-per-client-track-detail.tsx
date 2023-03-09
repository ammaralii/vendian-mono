import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './questions-frequency-per-client-track.reducer';

export const QuestionsFrequencyPerClientTrackDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionsFrequencyPerClientTrackEntity = useAppSelector(state => state.questionsFrequencyPerClientTrack.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsFrequencyPerClientTrackDetailsHeading">Questions Frequency Per Client Track</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{questionsFrequencyPerClientTrackEntity.id}</dd>
          <dt>
            <span id="question">Question</span>
          </dt>
          <dd>{questionsFrequencyPerClientTrackEntity.question}</dd>
          <dt>
            <span id="frequency">Frequency</span>
          </dt>
          <dd>{questionsFrequencyPerClientTrackEntity.frequency}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {questionsFrequencyPerClientTrackEntity.createdat ? (
              <TextFormat value={questionsFrequencyPerClientTrackEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {questionsFrequencyPerClientTrackEntity.updatedat ? (
              <TextFormat value={questionsFrequencyPerClientTrackEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Project</dt>
          <dd>{questionsFrequencyPerClientTrackEntity.project ? questionsFrequencyPerClientTrackEntity.project.id : ''}</dd>
          <dt>Track</dt>
          <dd>{questionsFrequencyPerClientTrackEntity.track ? questionsFrequencyPerClientTrackEntity.track.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/questions-frequency-per-client-track" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/questions-frequency-per-client-track/${questionsFrequencyPerClientTrackEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsFrequencyPerClientTrackDetail;
