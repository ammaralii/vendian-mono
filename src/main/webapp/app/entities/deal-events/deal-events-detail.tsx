import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-events.reducer';

export const DealEventsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealEventsEntity = useAppSelector(state => state.dealEvents.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealEventsDetailsHeading">Deal Events</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealEventsEntity.id}</dd>
          <dt>
            <span id="eventtype">Eventtype</span>
          </dt>
          <dd>{dealEventsEntity.eventtype}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealEventsEntity.createdat ? <TextFormat value={dealEventsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/deal-events" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-events/${dealEventsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealEventsDetail;
