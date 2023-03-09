import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approver-groups.reducer';

export const ApproverGroupsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approverGroupsEntity = useAppSelector(state => state.approverGroups.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approverGroupsDetailsHeading">Approver Groups</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{approverGroupsEntity.id}</dd>
          <dt>
            <span id="referenceId">Reference Id</span>
          </dt>
          <dd>{approverGroupsEntity.referenceId}</dd>
          <dt>
            <span id="referenceType">Reference Type</span>
          </dt>
          <dd>{approverGroupsEntity.referenceType}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {approverGroupsEntity.createdAt ? (
              <TextFormat value={approverGroupsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {approverGroupsEntity.updatedAt ? (
              <TextFormat value={approverGroupsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {approverGroupsEntity.deletedAt ? (
              <TextFormat value={approverGroupsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{approverGroupsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/approver-groups" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approver-groups/${approverGroupsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApproverGroupsDetail;
