import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification-events.reducer';

export const NotificationEventsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationEventsEntity = useAppSelector(state => state.notificationEvents.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationEventsDetailsHeading">Notification Events</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notificationEventsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{notificationEventsEntity.name}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {notificationEventsEntity.effDate ? (
              <TextFormat value={notificationEventsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {notificationEventsEntity.createdAt ? (
              <TextFormat value={notificationEventsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {notificationEventsEntity.updatedAt ? (
              <TextFormat value={notificationEventsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {notificationEventsEntity.endDate ? (
              <TextFormat value={notificationEventsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{notificationEventsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/notification-events" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification-events/${notificationEventsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationEventsDetail;
