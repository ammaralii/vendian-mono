import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-approval-criterias.reducer';

export const LeaveApprovalCriteriasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveApprovalCriteriasEntity = useAppSelector(state => state.leaveApprovalCriterias.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveApprovalCriteriasDetailsHeading">Leave Approval Criterias</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveApprovalCriteriasEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{leaveApprovalCriteriasEntity.name}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{leaveApprovalCriteriasEntity.priority}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveApprovalCriteriasEntity.effDate ? (
              <TextFormat value={leaveApprovalCriteriasEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveApprovalCriteriasEntity.createdAt ? (
              <TextFormat value={leaveApprovalCriteriasEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveApprovalCriteriasEntity.updatedAt ? (
              <TextFormat value={leaveApprovalCriteriasEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveApprovalCriteriasEntity.endDate ? (
              <TextFormat value={leaveApprovalCriteriasEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveApprovalCriteriasEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/leave-approval-criterias" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-approval-criterias/${leaveApprovalCriteriasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveApprovalCriteriasDetail;
