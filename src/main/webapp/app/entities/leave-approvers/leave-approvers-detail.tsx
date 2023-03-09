import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-approvers.reducer';

export const LeaveApproversDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveApproversEntity = useAppSelector(state => state.leaveApprovers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveApproversDetailsHeading">Leave Approvers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveApproversEntity.id}</dd>
          <dt>
            <span id="source">Source</span>
          </dt>
          <dd>{leaveApproversEntity.source}</dd>
          <dt>
            <span id="minApprovals">Min Approvals</span>
          </dt>
          <dd>{leaveApproversEntity.minApprovals}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{leaveApproversEntity.priority}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveApproversEntity.effDate ? <TextFormat value={leaveApproversEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveApproversEntity.createdAt ? (
              <TextFormat value={leaveApproversEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveApproversEntity.updatedAt ? (
              <TextFormat value={leaveApproversEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveApproversEntity.endDate ? <TextFormat value={leaveApproversEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveApproversEntity.version}</dd>
          <dt>Leave Approval Criteria</dt>
          <dd>{leaveApproversEntity.leaveApprovalCriteria ? leaveApproversEntity.leaveApprovalCriteria.id : ''}</dd>
          <dt>Attribute</dt>
          <dd>{leaveApproversEntity.attribute ? leaveApproversEntity.attribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-approvers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-approvers/${leaveApproversEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveApproversDetail;
