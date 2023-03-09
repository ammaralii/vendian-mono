import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './permissions.reducer';

export const PermissionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const permissionsEntity = useAppSelector(state => state.permissions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="permissionsDetailsHeading">Permissions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{permissionsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{permissionsEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{permissionsEntity.description}</dd>
          <dt>
            <span id="groupName">Group Name</span>
          </dt>
          <dd>{permissionsEntity.groupName}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{permissionsEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {permissionsEntity.createdat ? <TextFormat value={permissionsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {permissionsEntity.updatedat ? <TextFormat value={permissionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/permissions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/permissions/${permissionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PermissionsDetail;
