import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-attribute-approval-rules.reducer';

export const UserAttributeApprovalRulesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userAttributeApprovalRulesEntity = useAppSelector(state => state.userAttributeApprovalRules.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAttributeApprovalRulesDetailsHeading">User Attribute Approval Rules</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userAttributeApprovalRulesEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {userAttributeApprovalRulesEntity.effDate ? (
              <TextFormat value={userAttributeApprovalRulesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userAttributeApprovalRulesEntity.createdAt ? (
              <TextFormat value={userAttributeApprovalRulesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userAttributeApprovalRulesEntity.updatedAt ? (
              <TextFormat value={userAttributeApprovalRulesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userAttributeApprovalRulesEntity.endDate ? (
              <TextFormat value={userAttributeApprovalRulesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userAttributeApprovalRulesEntity.version}</dd>
          <dt>Attribute</dt>
          <dd>{userAttributeApprovalRulesEntity.attribute ? userAttributeApprovalRulesEntity.attribute.id : ''}</dd>
          <dt>Leave Approval Criteria</dt>
          <dd>{userAttributeApprovalRulesEntity.leaveApprovalCriteria ? userAttributeApprovalRulesEntity.leaveApprovalCriteria.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-attribute-approval-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-attribute-approval-rules/${userAttributeApprovalRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAttributeApprovalRulesDetail;
