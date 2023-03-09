import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-requests.reducer';

export const LeaveRequestsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveRequestsEntity = useAppSelector(state => state.leaveRequests.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveRequestsDetailsHeading">Leave Requests</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveRequestsEntity.id}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveRequestsEntity.createdAt ? (
              <TextFormat value={leaveRequestsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="requestStartDate">Request Start Date</span>
          </dt>
          <dd>
            {leaveRequestsEntity.requestStartDate ? (
              <TextFormat value={leaveRequestsEntity.requestStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="requestEndDate">Request End Date</span>
          </dt>
          <dd>
            {leaveRequestsEntity.requestEndDate ? (
              <TextFormat value={leaveRequestsEntity.requestEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isHalfDay">Is Half Day</span>
          </dt>
          <dd>{leaveRequestsEntity.isHalfDay ? 'true' : 'false'}</dd>
          <dt>
            <span id="statusDate">Status Date</span>
          </dt>
          <dd>
            {leaveRequestsEntity.statusDate ? (
              <TextFormat value={leaveRequestsEntity.statusDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{leaveRequestsEntity.comments}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveRequestsEntity.updatedAt ? (
              <TextFormat value={leaveRequestsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {leaveRequestsEntity.deletedAt ? (
              <TextFormat value={leaveRequestsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveRequestsEntity.version}</dd>
          <dt>Leave Detail</dt>
          <dd>{leaveRequestsEntity.leaveDetail ? leaveRequestsEntity.leaveDetail.id : ''}</dd>
          <dt>Leave Status</dt>
          <dd>{leaveRequestsEntity.leaveStatus ? leaveRequestsEntity.leaveStatus.id : ''}</dd>
          <dt>Parent Leave Request</dt>
          <dd>{leaveRequestsEntity.parentLeaveRequest ? leaveRequestsEntity.parentLeaveRequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-requests" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-requests/${leaveRequestsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveRequestsDetail;
