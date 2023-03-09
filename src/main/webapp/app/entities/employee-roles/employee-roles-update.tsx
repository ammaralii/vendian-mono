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
import { IEmployeeRoles } from 'app/shared/model/employee-roles.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-roles.reducer';

export const EmployeeRolesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const roles = useAppSelector(state => state.roles.entities);
  const employeeRolesEntity = useAppSelector(state => state.employeeRoles.entity);
  const loading = useAppSelector(state => state.employeeRoles.loading);
  const updating = useAppSelector(state => state.employeeRoles.updating);
  const updateSuccess = useAppSelector(state => state.employeeRoles.updateSuccess);

  const handleClose = () => {
    navigate('/employee-roles' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRoles({}));
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
      ...employeeRolesEntity,
      ...values,
      role: roles.find(it => it.id.toString() === values.role.toString()),
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
          ...employeeRolesEntity,
          createdat: convertDateTimeFromServer(employeeRolesEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeRolesEntity.updatedat),
          role: employeeRolesEntity?.role?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeRoles.home.createOrEditLabel" data-cy="EmployeeRolesCreateUpdateHeading">
            Create or edit a Employee Roles
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
                <ValidatedField name="id" required readOnly id="employee-roles-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="employee-roles-createdat"
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
                id="employee-roles-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Employeeid" id="employee-roles-employeeid" name="employeeid" data-cy="employeeid" type="text" />
              <ValidatedField id="employee-roles-role" name="role" data-cy="role" label="Role" type="select">
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-roles" replace color="info">
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

export default EmployeeRolesUpdate;
