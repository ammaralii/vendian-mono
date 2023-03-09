import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-escalation-criterias.reducer';

export const LeaveEscalationCriteriasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveEscalationCriteriasEntity = useAppSelector(state => state.leaveEscalationCriterias.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveEscalationCriteriasDetailsHeading">Leave Escalation Criterias</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveEscalationCriteriasEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{leaveEscalationCriteriasEntity.name}</dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{leaveEscalationCriteriasEntity.priority}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{leaveEscalationCriteriasEntity.total}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {leaveEscalationCriteriasEntity.effDate ? (
              <TextFormat value={leaveEscalationCriteriasEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {leaveEscalationCriteriasEntity.createdAt ? (
              <TextFormat value={leaveEscalationCriteriasEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {leaveEscalationCriteriasEntity.updatedAt ? (
              <TextFormat value={leaveEscalationCriteriasEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {leaveEscalationCriteriasEntity.endDate ? (
              <TextFormat value={leaveEscalationCriteriasEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leaveEscalationCriteriasEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/leave-escalation-criterias" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-escalation-criterias/${leaveEscalationCriteriasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveEscalationCriteriasDetail;
