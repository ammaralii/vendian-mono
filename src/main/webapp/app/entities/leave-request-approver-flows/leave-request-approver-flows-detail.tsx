import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-request-approver-flows.reducer';

export const LeaveRequestApproverFlowsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveRequestApproverFlowsEntity = useAppSelector(state => state.leaveRequestApproverFlows.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveRequestApproverFlowsDetailsHeading">Leave Request Approver Flows</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveRequestApproverFlowsEntity.id}</dd>
          <dt>
            <span id="approvals">Approvals</span>
          </dt>
          <dd>{leaveRequestApproverFlowsEntity.approvals}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveRequestApproverFlowsEntity.effDate ? (
              <TextFormat value={leaveRequestApproverFlowsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveRequestApproverFlowsEntity.createdAt ? (
              <TextFormat value={leaveRequestApproverFlowsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveRequestApproverFlowsEntity.updatedAt ? (
              <TextFormat value={leaveRequestApproverFlowsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveRequestApproverFlowsEntity.endDate ? (
              <TextFormat value={leaveRequestApproverFlowsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveRequestApproverFlowsEntity.version}</dd>
          <dt>Approver Status</dt>
          <dd>{leaveRequestApproverFlowsEntity.approverStatus ? leaveRequestApproverFlowsEntity.approverStatus.id : ''}</dd>
          <dt>Current Leave Request Status</dt>
          <dd>
            {leaveRequestApproverFlowsEntity.currentLeaveRequestStatus ? leaveRequestApproverFlowsEntity.currentLeaveRequestStatus.id : ''}
          </dd>
          <dt>Next Leave Request Status</dt>
          <dd>{leaveRequestApproverFlowsEntity.nextLeaveRequestStatus ? leaveRequestApproverFlowsEntity.nextLeaveRequestStatus.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-request-approver-flows" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-request-approver-flows/${leaveRequestApproverFlowsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveRequestApproverFlowsDetail;
