import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-status-work-flows.reducer';

export const LeaveStatusWorkFlowsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveStatusWorkFlowsEntity = useAppSelector(state => state.leaveStatusWorkFlows.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveStatusWorkFlowsDetailsHeading">Leave Status Work Flows</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveStatusWorkFlowsEntity.id}</dd>
          <dt>
            <span id="ifApprovals">If Approvals</span>
          </dt>
          <dd>{leaveStatusWorkFlowsEntity.ifApprovals ? 'true' : 'false'}</dd>
          <dt>
            <span id="approvalRequired">Approval Required</span>
          </dt>
          <dd>{leaveStatusWorkFlowsEntity.approvalRequired ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveStatusWorkFlowsEntity.createdAt ? (
              <TextFormat value={leaveStatusWorkFlowsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveStatusWorkFlowsEntity.updatedAt ? (
              <TextFormat value={leaveStatusWorkFlowsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {leaveStatusWorkFlowsEntity.deletedAt ? (
              <TextFormat value={leaveStatusWorkFlowsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveStatusWorkFlowsEntity.version}</dd>
          <dt>Current Status</dt>
          <dd>{leaveStatusWorkFlowsEntity.currentStatus ? leaveStatusWorkFlowsEntity.currentStatus.id : ''}</dd>
          <dt>Next Status</dt>
          <dd>{leaveStatusWorkFlowsEntity.nextStatus ? leaveStatusWorkFlowsEntity.nextStatus.id : ''}</dd>
          <dt>Skip To Status</dt>
          <dd>{leaveStatusWorkFlowsEntity.skipToStatus ? leaveStatusWorkFlowsEntity.skipToStatus.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-status-work-flows" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-status-work-flows/${leaveStatusWorkFlowsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveStatusWorkFlowsDetail;
