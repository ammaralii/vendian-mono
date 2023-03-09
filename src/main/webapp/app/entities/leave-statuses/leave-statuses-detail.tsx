import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-statuses.reducer';

export const LeaveStatusesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveStatusesEntity = useAppSelector(state => state.leaveStatuses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveStatusesDetailsHeading">Leave Statuses</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveStatusesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{leaveStatusesEntity.name}</dd>
          <dt>
            <span id="leaveGroup">Leave Group</span>
          </dt>
          <dd>{leaveStatusesEntity.leaveGroup}</dd>
          <dt>
            <span id="systemKey">System Key</span>
          </dt>
          <dd>{leaveStatusesEntity.systemKey}</dd>
          <dt>
            <span id="isDefault">Is Default</span>
          </dt>
          <dd>{leaveStatusesEntity.isDefault ? 'true' : 'false'}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveStatusesEntity.effDate ? <TextFormat value={leaveStatusesEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveStatusesEntity.createdAt ? (
              <TextFormat value={leaveStatusesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveStatusesEntity.updatedAt ? (
              <TextFormat value={leaveStatusesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveStatusesEntity.endDate ? <TextFormat value={leaveStatusesEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveStatusesEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/leave-statuses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-statuses/${leaveStatusesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveStatusesDetail;
