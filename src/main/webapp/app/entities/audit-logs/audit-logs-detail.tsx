import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './audit-logs.reducer';

export const AuditLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const auditLogsEntity = useAppSelector(state => state.auditLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="auditLogsDetailsHeading">Audit Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{auditLogsEntity.id}</dd>
          <dt>
            <span id="event">Event</span>
          </dt>
          <dd>{auditLogsEntity.event}</dd>
          <dt>
            <span id="eventTime">Event Time</span>
          </dt>
          <dd>
            {auditLogsEntity.eventTime ? <TextFormat value={auditLogsEntity.eventTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{auditLogsEntity.description}</dd>
          <dt>
            <span id="oldChange">Old Change</span>
          </dt>
          <dd>{auditLogsEntity.oldChange}</dd>
          <dt>
            <span id="newChange">New Change</span>
          </dt>
          <dd>{auditLogsEntity.newChange}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {auditLogsEntity.createdAt ? <TextFormat value={auditLogsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {auditLogsEntity.updatedAt ? <TextFormat value={auditLogsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{auditLogsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/audit-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/audit-logs/${auditLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuditLogsDetail;
