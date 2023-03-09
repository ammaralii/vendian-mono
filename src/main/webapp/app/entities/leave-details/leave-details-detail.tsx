import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-details.reducer';

export const LeaveDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveDetailsEntity = useAppSelector(state => state.leaveDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveDetailsDetailsHeading">Leave Details</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveDetailsEntity.id}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{leaveDetailsEntity.total}</dd>
          <dt>
            <span id="used">Used</span>
          </dt>
          <dd>{leaveDetailsEntity.used}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveDetailsEntity.effDate ? <TextFormat value={leaveDetailsEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveDetailsEntity.createdAt ? <TextFormat value={leaveDetailsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveDetailsEntity.updatedAt ? <TextFormat value={leaveDetailsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveDetailsEntity.endDate ? <TextFormat value={leaveDetailsEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveDetailsEntity.version}</dd>
          <dt>Leave</dt>
          <dd>{leaveDetailsEntity.leave ? leaveDetailsEntity.leave.id : ''}</dd>
          <dt>Leave Type</dt>
          <dd>{leaveDetailsEntity.leaveType ? leaveDetailsEntity.leaveType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-details/${leaveDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveDetailsDetail;
