import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-resource-event-logs.reducer';

export const DealResourceEventLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealResourceEventLogsEntity = useAppSelector(state => state.dealResourceEventLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealResourceEventLogsDetailsHeading">Deal Resource Event Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealResourceEventLogsEntity.id}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{dealResourceEventLogsEntity.comments}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealResourceEventLogsEntity.createdat ? (
              <TextFormat value={dealResourceEventLogsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Matchingresource</dt>
          <dd>{dealResourceEventLogsEntity.matchingresource ? dealResourceEventLogsEntity.matchingresource.id : ''}</dd>
          <dt>Resourcestatus</dt>
          <dd>{dealResourceEventLogsEntity.resourcestatus ? dealResourceEventLogsEntity.resourcestatus.systemKey : ''}</dd>
          <dt>Systemstatus</dt>
          <dd>{dealResourceEventLogsEntity.systemstatus ? dealResourceEventLogsEntity.systemstatus.systemKey : ''}</dd>
        </dl>
        <Button tag={Link} to="/deal-resource-event-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-resource-event-logs/${dealResourceEventLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealResourceEventLogsDetail;
