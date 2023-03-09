import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-type-restrictions.reducer';

export const LeaveTypeRestrictionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypeRestrictionsEntity = useAppSelector(state => state.leaveTypeRestrictions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypeRestrictionsDetailsHeading">Leave Type Restrictions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypeRestrictionsEntity.id}</dd>
          <dt>
            <span id="restrictionJson">Restriction Json</span>
          </dt>
          <dd>{leaveTypeRestrictionsEntity.restrictionJson}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveTypeRestrictionsEntity.effDate ? (
              <TextFormat value={leaveTypeRestrictionsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveTypeRestrictionsEntity.createdAt ? (
              <TextFormat value={leaveTypeRestrictionsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveTypeRestrictionsEntity.updatedAt ? (
              <TextFormat value={leaveTypeRestrictionsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveTypeRestrictionsEntity.endDate ? (
              <TextFormat value={leaveTypeRestrictionsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveTypeRestrictionsEntity.version}</dd>
          <dt>Leave Type</dt>
          <dd>{leaveTypeRestrictionsEntity.leaveType ? leaveTypeRestrictionsEntity.leaveType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-type-restrictions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-type-restrictions/${leaveTypeRestrictionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypeRestrictionsDetail;
