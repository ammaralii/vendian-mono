import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-detail-adjustment-logs.reducer';

export const LeaveDetailAdjustmentLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveDetailAdjustmentLogsEntity = useAppSelector(state => state.leaveDetailAdjustmentLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveDetailAdjustmentLogsDetailsHeading">Leave Detail Adjustment Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.id}</dd>
          <dt>
            <span id="action">Action</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.action}</dd>
          <dt>
            <span id="count">Count</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.count}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveDetailAdjustmentLogsEntity.createdAt ? (
              <TextFormat value={leaveDetailAdjustmentLogsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveDetailAdjustmentLogsEntity.updatedAt ? (
              <TextFormat value={leaveDetailAdjustmentLogsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.version}</dd>
          <dt>
            <span id="quotaBeforeAdjustment">Quota Before Adjustment</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.quotaBeforeAdjustment}</dd>
          <dt>
            <span id="quotaAfterAdjustment">Quota After Adjustment</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.quotaAfterAdjustment}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{leaveDetailAdjustmentLogsEntity.comment}</dd>
          <dt>Leave Detail</dt>
          <dd>{leaveDetailAdjustmentLogsEntity.leaveDetail ? leaveDetailAdjustmentLogsEntity.leaveDetail.id : ''}</dd>
          <dt>Adjusted By</dt>
          <dd>{leaveDetailAdjustmentLogsEntity.adjustedBy ? leaveDetailAdjustmentLogsEntity.adjustedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-detail-adjustment-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-detail-adjustment-logs/${leaveDetailAdjustmentLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveDetailAdjustmentLogsDetail;
