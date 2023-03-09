import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-type-escalation-rules.reducer';

export const LeaveTypeEscalationRulesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypeEscalationRulesEntity = useAppSelector(state => state.leaveTypeEscalationRules.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypeEscalationRulesDetailsHeading">Leave Type Escalation Rules</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypeEscalationRulesEntity.id}</dd>
          <dt>
            <span id="noOfDays">No Of Days</span>
          </dt>
          <dd>{leaveTypeEscalationRulesEntity.noOfDays}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveTypeEscalationRulesEntity.effDate ? (
              <TextFormat value={leaveTypeEscalationRulesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveTypeEscalationRulesEntity.createdAt ? (
              <TextFormat value={leaveTypeEscalationRulesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveTypeEscalationRulesEntity.updatedAt ? (
              <TextFormat value={leaveTypeEscalationRulesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveTypeEscalationRulesEntity.endDate ? (
              <TextFormat value={leaveTypeEscalationRulesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveTypeEscalationRulesEntity.version}</dd>
          <dt>Leave Escalation Criteria</dt>
          <dd>{leaveTypeEscalationRulesEntity.leaveEscalationCriteria ? leaveTypeEscalationRulesEntity.leaveEscalationCriteria.id : ''}</dd>
          <dt>Leave Request Status</dt>
          <dd>{leaveTypeEscalationRulesEntity.leaveRequestStatus ? leaveTypeEscalationRulesEntity.leaveRequestStatus.id : ''}</dd>
          <dt>Leave Type</dt>
          <dd>{leaveTypeEscalationRulesEntity.leaveType ? leaveTypeEscalationRulesEntity.leaveType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-type-escalation-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-type-escalation-rules/${leaveTypeEscalationRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypeEscalationRulesDetail;
