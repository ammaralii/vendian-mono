import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification-sent-email-logs.reducer';

export const NotificationSentEmailLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationSentEmailLogsEntity = useAppSelector(state => state.notificationSentEmailLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationSentEmailLogsDetailsHeading">Notification Sent Email Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notificationSentEmailLogsEntity.id}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{notificationSentEmailLogsEntity.email}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {notificationSentEmailLogsEntity.createdAt ? (
              <TextFormat value={notificationSentEmailLogsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {notificationSentEmailLogsEntity.updatedAt ? (
              <TextFormat value={notificationSentEmailLogsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {notificationSentEmailLogsEntity.deletedAt ? (
              <TextFormat value={notificationSentEmailLogsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{notificationSentEmailLogsEntity.version}</dd>
          <dt>Notification Template</dt>
          <dd>{notificationSentEmailLogsEntity.notificationTemplate ? notificationSentEmailLogsEntity.notificationTemplate.id : ''}</dd>
          <dt>Recipient</dt>
          <dd>{notificationSentEmailLogsEntity.recipient ? notificationSentEmailLogsEntity.recipient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification-sent-email-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification-sent-email-logs/${notificationSentEmailLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationSentEmailLogsDetail;
