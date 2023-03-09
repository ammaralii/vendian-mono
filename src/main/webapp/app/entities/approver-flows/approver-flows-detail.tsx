import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approver-flows.reducer';

export const ApproverFlowsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approverFlowsEntity = useAppSelector(state => state.approverFlows.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approverFlowsDetailsHeading">Approver Flows</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{approverFlowsEntity.id}</dd>
          <dt>
            <span id="referenceType">Reference Type</span>
          </dt>
          <dd>{approverFlowsEntity.referenceType}</dd>
          <dt>
            <span id="approverStatus">Approver Status</span>
          </dt>
          <dd>{approverFlowsEntity.approverStatus}</dd>
          <dt>
            <span id="approval">Approval</span>
          </dt>
          <dd>{approverFlowsEntity.approval}</dd>
          <dt>
            <span id="currentStatus">Current Status</span>
          </dt>
          <dd>{approverFlowsEntity.currentStatus}</dd>
          <dt>
            <span id="nextStatus">Next Status</span>
          </dt>
          <dd>{approverFlowsEntity.nextStatus}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {approverFlowsEntity.effDate ? <TextFormat value={approverFlowsEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {approverFlowsEntity.createdAt ? (
              <TextFormat value={approverFlowsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {approverFlowsEntity.updatedAt ? (
              <TextFormat value={approverFlowsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{approverFlowsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/approver-flows" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approver-flows/${approverFlowsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApproverFlowsDetail;
