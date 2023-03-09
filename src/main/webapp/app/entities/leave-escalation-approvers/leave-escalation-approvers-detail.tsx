import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-escalation-approvers.reducer';

export const LeaveEscalationApproversDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveEscalationApproversEntity = useAppSelector(state => state.leaveEscalationApprovers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveEscalationApproversDetailsHeading">Leave Escalation Approvers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveEscalationApproversEntity.id}</dd>
          <dt>
            <span id="source">Source</span>
          </dt>
          <dd>{leaveEscalationApproversEntity.source}</dd>
          <dt>
            <span id="minApprovals">Min Approvals</span>
          </dt>
          <dd>{leaveEscalationApproversEntity.minApprovals}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{leaveEscalationApproversEntity.priority}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveEscalationApproversEntity.effDate ? (
              <TextFormat value={leaveEscalationApproversEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveEscalationApproversEntity.createdAt ? (
              <TextFormat value={leaveEscalationApproversEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveEscalationApproversEntity.updatedAt ? (
              <TextFormat value={leaveEscalationApproversEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveEscalationApproversEntity.endDate ? (
              <TextFormat value={leaveEscalationApproversEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveEscalationApproversEntity.version}</dd>
          <dt>Leave Escalation Criteria</dt>
          <dd>{leaveEscalationApproversEntity.leaveEscalationCriteria ? leaveEscalationApproversEntity.leaveEscalationCriteria.id : ''}</dd>
          <dt>Attribute</dt>
          <dd>{leaveEscalationApproversEntity.attribute ? leaveEscalationApproversEntity.attribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-escalation-approvers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-escalation-approvers/${leaveEscalationApproversEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveEscalationApproversDetail;
