import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './role-permissions.reducer';

export const RolePermissionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rolePermissionsEntity = useAppSelector(state => state.rolePermissions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rolePermissionsDetailsHeading">Role Permissions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rolePermissionsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {rolePermissionsEntity.createdat ? (
              <TextFormat value={rolePermissionsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {rolePermissionsEntity.updatedat ? (
              <TextFormat value={rolePermissionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Role</dt>
          <dd>{rolePermissionsEntity.role ? rolePermissionsEntity.role.id : ''}</dd>
          <dt>Permission</dt>
          <dd>{rolePermissionsEntity.permission ? rolePermissionsEntity.permission.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/role-permissions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/role-permissions/${rolePermissionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RolePermissionsDetail;
