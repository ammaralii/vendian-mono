import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './questions-processing-event-logs.reducer';

export const QuestionsProcessingEventLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionsProcessingEventLogsEntity = useAppSelector(state => state.questionsProcessingEventLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsProcessingEventLogsDetailsHeading">Questions Processing Event Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{questionsProcessingEventLogsEntity.id}</dd>
          <dt>
            <span id="processedOn">Processed On</span>
          </dt>
          <dd>{questionsProcessingEventLogsEntity.processedOn}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {questionsProcessingEventLogsEntity.createdat ? (
              <TextFormat value={questionsProcessingEventLogsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {questionsProcessingEventLogsEntity.updatedat ? (
              <TextFormat value={questionsProcessingEventLogsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/questions-processing-event-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/questions-processing-event-logs/${questionsProcessingEventLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsProcessingEventLogsDetail;
