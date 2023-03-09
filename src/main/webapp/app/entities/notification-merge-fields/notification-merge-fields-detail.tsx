import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification-merge-fields.reducer';

export const NotificationMergeFieldsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationMergeFieldsEntity = useAppSelector(state => state.notificationMergeFields.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationMergeFieldsDetailsHeading">Notification Merge Fields</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notificationMergeFieldsEntity.id}</dd>
          <dt>
            <span id="field">Field</span>
          </dt>
          <dd>{notificationMergeFieldsEntity.field}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {notificationMergeFieldsEntity.effDate ? (
              <TextFormat value={notificationMergeFieldsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {notificationMergeFieldsEntity.createdAt ? (
              <TextFormat value={notificationMergeFieldsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {notificationMergeFieldsEntity.updatedAt ? (
              <TextFormat value={notificationMergeFieldsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {notificationMergeFieldsEntity.endDate ? (
              <TextFormat value={notificationMergeFieldsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{notificationMergeFieldsEntity.version}</dd>
          <dt>Notification Event</dt>
          <dd>{notificationMergeFieldsEntity.notificationEvent ? notificationMergeFieldsEntity.notificationEvent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification-merge-fields" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification-merge-fields/${notificationMergeFieldsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationMergeFieldsDetail;
