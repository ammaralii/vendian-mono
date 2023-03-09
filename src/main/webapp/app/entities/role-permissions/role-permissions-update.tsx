import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoles } from 'app/shared/model/roles.model';
import { getEntities as getRoles } from 'app/entities/roles/roles.reducer';
import { IPermissions } from 'app/shared/model/permissions.model';
import { getEntities as getPermissions } from 'app/entities/permissions/permissions.reducer';
import { IRolePermissions } from 'app/shared/model/role-permissions.model';
import { getEntity, updateEntity, createEntity, reset } from './role-permissions.reducer';

export const RolePermissionsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const roles = useAppSelector(state => state.roles.entities);
  const permissions = useAppSelector(state => state.permissions.entities);
  const rolePermissionsEntity = useAppSelector(state => state.rolePermissions.entity);
  const loading = useAppSelector(state => state.rolePermissions.loading);
  const updating = useAppSelector(state => state.rolePermissions.updating);
  const updateSuccess = useAppSelector(state => state.rolePermissions.updateSuccess);

  const handleClose = () => {
    navigate('/role-permissions' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRoles({}));
    dispatch(getPermissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...rolePermissionsEntity,
      ...values,
      role: roles.find(it => it.id.toString() === values.role.toString()),
      permission: permissions.find(it => it.id.toString() === values.permission.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...rolePermissionsEntity,
          createdat: convertDateTimeFromServer(rolePermissionsEntity.createdat),
          updatedat: convertDateTimeFromServer(rolePermissionsEntity.updatedat),
          role: rolePermissionsEntity?.role?.id,
          permission: rolePermissionsEntity?.permission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.rolePermissions.home.createOrEditLabel" data-cy="RolePermissionsCreateUpdateHeading">
            Create or edit a Role Permissions
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="role-permissions-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="role-permissions-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updatedat"
                id="role-permissions-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="role-permissions-role" name="role" data-cy="role" label="Role" type="select">
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="role-permissions-permission" name="permission" data-cy="permission" label="Permission" type="select">
                <option value="" key="0" />
                {permissions
                  ? permissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/role-permissions" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RolePermissionsUpdate;
