import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-relation-approval-rules.reducer';

export const UserRelationApprovalRulesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userRelationApprovalRulesEntity = useAppSelector(state => state.userRelationApprovalRules.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRelationApprovalRulesDetailsHeading">User Relation Approval Rules</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userRelationApprovalRulesEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {userRelationApprovalRulesEntity.effDate ? (
              <TextFormat value={userRelationApprovalRulesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userRelationApprovalRulesEntity.createdAt ? (
              <TextFormat value={userRelationApprovalRulesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userRelationApprovalRulesEntity.updatedAt ? (
              <TextFormat value={userRelationApprovalRulesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userRelationApprovalRulesEntity.endDate ? (
              <TextFormat value={userRelationApprovalRulesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userRelationApprovalRulesEntity.version}</dd>
          <dt>Attribute</dt>
          <dd>{userRelationApprovalRulesEntity.attribute ? userRelationApprovalRulesEntity.attribute.id : ''}</dd>
          <dt>Leave Approval Criteria</dt>
          <dd>{userRelationApprovalRulesEntity.leaveApprovalCriteria ? userRelationApprovalRulesEntity.leaveApprovalCriteria.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-relation-approval-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-relation-approval-rules/${userRelationApprovalRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRelationApprovalRulesDetail;
