import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-attribute-escalation-rules.reducer';

export const UserAttributeEscalationRulesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userAttributeEscalationRulesEntity = useAppSelector(state => state.userAttributeEscalationRules.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAttributeEscalationRulesDetailsHeading">User Attribute Escalation Rules</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userAttributeEscalationRulesEntity.id}</dd>
          <dt>
            <span id="leaveEscalationCriteriaId">Leave Escalation Criteria Id</span>
          </dt>
          <dd>{userAttributeEscalationRulesEntity.leaveEscalationCriteriaId}</dd>
          <dt>
            <span id="noOfDays">No Of Days</span>
          </dt>
          <dd>{userAttributeEscalationRulesEntity.noOfDays}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {userAttributeEscalationRulesEntity.effDate ? (
              <TextFormat value={userAttributeEscalationRulesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userAttributeEscalationRulesEntity.createdAt ? (
              <TextFormat value={userAttributeEscalationRulesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userAttributeEscalationRulesEntity.updatedAt ? (
              <TextFormat value={userAttributeEscalationRulesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userAttributeEscalationRulesEntity.endDate ? (
              <TextFormat value={userAttributeEscalationRulesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userAttributeEscalationRulesEntity.version}</dd>
          <dt>Attribute</dt>
          <dd>{userAttributeEscalationRulesEntity.attribute ? userAttributeEscalationRulesEntity.attribute.id : ''}</dd>
          <dt>Approver Status</dt>
          <dd>{userAttributeEscalationRulesEntity.approverStatus ? userAttributeEscalationRulesEntity.approverStatus.id : ''}</dd>
          <dt>Leaveescalationcriteria</dt>
          <dd>
            {userAttributeEscalationRulesEntity.leaveescalationcriteria
              ? userAttributeEscalationRulesEntity.leaveescalationcriteria.id
              : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/user-attribute-escalation-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-attribute-escalation-rules/${userAttributeEscalationRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAttributeEscalationRulesDetail;
