import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-request-approvers.reducer';

export const LeaveRequestApproversDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveRequestApproversEntity = useAppSelector(state => state.leaveRequestApprovers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveRequestApproversDetailsHeading">Leave Request Approvers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveRequestApproversEntity.id}</dd>
          <dt>
            <span id="reference">Reference</span>
          </dt>
          <dd>{leaveRequestApproversEntity.reference}</dd>
          <dt>
            <span id="as">As</span>
          </dt>
          <dd>{leaveRequestApproversEntity.as}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{leaveRequestApproversEntity.comments}</dd>
          <dt>
            <span id="approverGroup">Approver Group</span>
          </dt>
          <dd>{leaveRequestApproversEntity.approverGroup}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{leaveRequestApproversEntity.priority}</dd>
          <dt>
            <span id="statusDate">Status Date</span>
          </dt>
          <dd>
            {leaveRequestApproversEntity.statusDate ? (
              <TextFormat value={leaveRequestApproversEntity.statusDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveRequestApproversEntity.createdAt ? (
              <TextFormat value={leaveRequestApproversEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveRequestApproversEntity.updatedAt ? (
              <TextFormat value={leaveRequestApproversEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {leaveRequestApproversEntity.deletedAt ? (
              <TextFormat value={leaveRequestApproversEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveRequestApproversEntity.version}</dd>
          <dt>Leave Request</dt>
          <dd>{leaveRequestApproversEntity.leaveRequest ? leaveRequestApproversEntity.leaveRequest.id : ''}</dd>
          <dt>User</dt>
          <dd>{leaveRequestApproversEntity.user ? leaveRequestApproversEntity.user.id : ''}</dd>
          <dt>Status</dt>
          <dd>{leaveRequestApproversEntity.status ? leaveRequestApproversEntity.status.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-request-approvers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-request-approvers/${leaveRequestApproversEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveRequestApproversDetail;
