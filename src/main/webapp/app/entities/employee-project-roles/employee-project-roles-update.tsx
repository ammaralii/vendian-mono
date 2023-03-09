import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';
import { getEntities as getEmployeeProjects } from 'app/entities/employee-projects/employee-projects.reducer';
import { IProjectRoles } from 'app/shared/model/project-roles.model';
import { getEntities as getProjectRoles } from 'app/entities/project-roles/project-roles.reducer';
import { IEmployeeProjectRoles } from 'app/shared/model/employee-project-roles.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-project-roles.reducer';

export const EmployeeProjectRolesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employeeProjects = useAppSelector(state => state.employeeProjects.entities);
  const projectRoles = useAppSelector(state => state.projectRoles.entities);
  const employeeProjectRolesEntity = useAppSelector(state => state.employeeProjectRoles.entity);
  const loading = useAppSelector(state => state.employeeProjectRoles.loading);
  const updating = useAppSelector(state => state.employeeProjectRoles.updating);
  const updateSuccess = useAppSelector(state => state.employeeProjectRoles.updateSuccess);

  const handleClose = () => {
    navigate('/employee-project-roles' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployeeProjects({}));
    dispatch(getProjectRoles({}));
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
      ...employeeProjectRolesEntity,
      ...values,
      employeeproject: employeeProjects.find(it => it.id.toString() === values.employeeproject.toString()),
      projectrole: projectRoles.find(it => it.id.toString() === values.projectrole.toString()),
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
          ...employeeProjectRolesEntity,
          createdat: convertDateTimeFromServer(employeeProjectRolesEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeProjectRolesEntity.updatedat),
          employeeproject: employeeProjectRolesEntity?.employeeproject?.id,
          projectrole: employeeProjectRolesEntity?.projectrole?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeProjectRoles.home.createOrEditLabel" data-cy="EmployeeProjectRolesCreateUpdateHeading">
            Create or edit a Employee Project Roles
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
                <ValidatedField name="id" required readOnly id="employee-project-roles-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Status" id="employee-project-roles-status" name="status" data-cy="status" check type="checkbox" />
              <ValidatedField
                label="Createdat"
                id="employee-project-roles-createdat"
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
                id="employee-project-roles-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="employee-project-roles-employeeproject"
                name="employeeproject"
                data-cy="employeeproject"
                label="Employeeproject"
                type="select"
              >
                <option value="" key="0" />
                {employeeProjects
                  ? employeeProjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-project-roles-projectrole"
                name="projectrole"
                data-cy="projectrole"
                label="Projectrole"
                type="select"
              >
                <option value="" key="0" />
                {projectRoles
                  ? projectRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-project-roles" replace color="info">
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

export default EmployeeProjectRolesUpdate;
