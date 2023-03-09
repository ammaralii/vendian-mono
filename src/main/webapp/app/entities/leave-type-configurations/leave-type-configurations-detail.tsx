import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-type-configurations.reducer';

export const LeaveTypeConfigurationsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypeConfigurationsEntity = useAppSelector(state => state.leaveTypeConfigurations.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypeConfigurationsDetailsHeading">Leave Type Configurations</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.id}</dd>
          <dt>
            <span id="noOfLeaves">No Of Leaves</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.noOfLeaves}</dd>
          <dt>
            <span id="tenureCycle">Tenure Cycle</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.tenureCycle}</dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.to}</dd>
          <dt>
            <span id="from">From</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.from}</dd>
          <dt>
            <span id="inclusivity">Inclusivity</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.inclusivity}</dd>
          <dt>
            <span id="maxQuota">Max Quota</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.maxQuota}</dd>
          <dt>
            <span id="isAccured">Is Accured</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.isAccured ? 'true' : 'false'}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveTypeConfigurationsEntity.effDate ? (
              <TextFormat value={leaveTypeConfigurationsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveTypeConfigurationsEntity.createdAt ? (
              <TextFormat value={leaveTypeConfigurationsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveTypeConfigurationsEntity.updatedAt ? (
              <TextFormat value={leaveTypeConfigurationsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveTypeConfigurationsEntity.endDate ? (
              <TextFormat value={leaveTypeConfigurationsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveTypeConfigurationsEntity.version}</dd>
          <dt>Leave Type</dt>
          <dd>{leaveTypeConfigurationsEntity.leaveType ? leaveTypeConfigurationsEntity.leaveType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-type-configurations" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-type-configurations/${leaveTypeConfigurationsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypeConfigurationsDetail;
