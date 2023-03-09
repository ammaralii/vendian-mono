import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification-templates.reducer';

export const NotificationTemplatesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationTemplatesEntity = useAppSelector(state => state.notificationTemplates.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationTemplatesDetailsHeading">Notification Templates</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notificationTemplatesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{notificationTemplatesEntity.name}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{notificationTemplatesEntity.type}</dd>
          <dt>
            <span id="subject">Subject</span>
          </dt>
          <dd>{notificationTemplatesEntity.subject}</dd>
          <dt>
            <span id="template">Template</span>
          </dt>
          <dd>
            {notificationTemplatesEntity.template ? (
              <div>
                {notificationTemplatesEntity.templateContentType ? (
                  <a onClick={openFile(notificationTemplatesEntity.templateContentType, notificationTemplatesEntity.template)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {notificationTemplatesEntity.templateContentType}, {byteSize(notificationTemplatesEntity.template)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {notificationTemplatesEntity.effDate ? (
              <TextFormat value={notificationTemplatesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {notificationTemplatesEntity.createdAt ? (
              <TextFormat value={notificationTemplatesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {notificationTemplatesEntity.updatedAt ? (
              <TextFormat value={notificationTemplatesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {notificationTemplatesEntity.endDate ? (
              <TextFormat value={notificationTemplatesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{notificationTemplatesEntity.version}</dd>
          <dt>Notification Event</dt>
          <dd>{notificationTemplatesEntity.notificationEvent ? notificationTemplatesEntity.notificationEvent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification-templates" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification-templates/${notificationTemplatesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationTemplatesDetail;
