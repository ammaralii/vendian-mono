import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-types.reducer';

export const LeaveTypesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypesEntity = useAppSelector(state => state.leaveTypes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypesDetailsHeading">Leave Types</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{leaveTypesEntity.name}</dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{leaveTypesEntity.category}</dd>
          <dt>
            <span id="cycleType">Cycle Type</span>
          </dt>
          <dd>{leaveTypesEntity.cycleType}</dd>
          <dt>
            <span id="cycleCount">Cycle Count</span>
          </dt>
          <dd>{leaveTypesEntity.cycleCount}</dd>
          <dt>
            <span id="maxQuota">Max Quota</span>
          </dt>
          <dd>{leaveTypesEntity.maxQuota}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>{leaveTypesEntity.effDate ? <TextFormat value={leaveTypesEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveTypesEntity.createdAt ? <TextFormat value={leaveTypesEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveTypesEntity.updatedAt ? <TextFormat value={leaveTypesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{leaveTypesEntity.endDate ? <TextFormat value={leaveTypesEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveTypesEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/leave-types" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-types/${leaveTypesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypesDetail;
