import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-type-approval-rules.reducer';

export const LeaveTypeApprovalRulesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypeApprovalRulesEntity = useAppSelector(state => state.leaveTypeApprovalRules.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypeApprovalRulesDetailsHeading">Leave Type Approval Rules</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypeApprovalRulesEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveTypeApprovalRulesEntity.effDate ? (
              <TextFormat value={leaveTypeApprovalRulesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveTypeApprovalRulesEntity.createdAt ? (
              <TextFormat value={leaveTypeApprovalRulesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveTypeApprovalRulesEntity.updatedAt ? (
              <TextFormat value={leaveTypeApprovalRulesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {leaveTypeApprovalRulesEntity.deletedAt ? (
              <TextFormat value={leaveTypeApprovalRulesEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveTypeApprovalRulesEntity.version}</dd>
          <dt>Leave Approval Criteria</dt>
          <dd>{leaveTypeApprovalRulesEntity.leaveApprovalCriteria ? leaveTypeApprovalRulesEntity.leaveApprovalCriteria.id : ''}</dd>
          <dt>Leave Type</dt>
          <dd>{leaveTypeApprovalRulesEntity.leaveType ? leaveTypeApprovalRulesEntity.leaveType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-type-approval-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-type-approval-rules/${leaveTypeApprovalRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypeApprovalRulesDetail;
