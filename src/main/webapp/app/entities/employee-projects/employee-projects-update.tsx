import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-projects.reducer';

export const EmployeeProjectsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const projects = useAppSelector(state => state.projects.entities);
  const employeeProjectsEntity = useAppSelector(state => state.employeeProjects.entity);
  const loading = useAppSelector(state => state.employeeProjects.loading);
  const updating = useAppSelector(state => state.employeeProjects.updating);
  const updateSuccess = useAppSelector(state => state.employeeProjects.updateSuccess);

  const handleClose = () => {
    navigate('/employee-projects' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getProjects({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.extendedenddate = convertDateTimeToServer(values.extendedenddate);

    const entity = {
      ...employeeProjectsEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      assignor: employees.find(it => it.id.toString() === values.assignor.toString()),
      project: projects.find(it => it.id.toString() === values.project.toString()),
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          extendedenddate: displayDefaultDateTime(),
        }
      : {
          ...employeeProjectsEntity,
          startdate: convertDateTimeFromServer(employeeProjectsEntity.startdate),
          enddate: convertDateTimeFromServer(employeeProjectsEntity.enddate),
          createdat: convertDateTimeFromServer(employeeProjectsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeProjectsEntity.updatedat),
          extendedenddate: convertDateTimeFromServer(employeeProjectsEntity.extendedenddate),
          employee: employeeProjectsEntity?.employee?.id,
          project: employeeProjectsEntity?.project?.id,
          assignor: employeeProjectsEntity?.assignor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeProjects.home.createOrEditLabel" data-cy="EmployeeProjectsCreateUpdateHeading">
            Create or edit a Employee Projects
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
                <ValidatedField name="id" required readOnly id="employee-projects-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Status" id="employee-projects-status" name="status" data-cy="status" check type="checkbox" />
              <ValidatedField
                label="Type"
                id="employee-projects-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  maxLength: { value: 12, message: 'This field cannot be longer than 12 characters.' },
                }}
              />
              <ValidatedField
                label="Startdate"
                id="employee-projects-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="employee-projects-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Allocation"
                id="employee-projects-allocation"
                name="allocation"
                data-cy="allocation"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Billed"
                id="employee-projects-billed"
                name="billed"
                data-cy="billed"
                type="text"
                validate={{
                  maxLength: { value: 15, message: 'This field cannot be longer than 15 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employee-projects-createdat"
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
                id="employee-projects-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Roleid" id="employee-projects-roleid" name="roleid" data-cy="roleid" type="text" />
              <ValidatedField
                label="Notes"
                id="employee-projects-notes"
                name="notes"
                data-cy="notes"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Extendedenddate"
                id="employee-projects-extendedenddate"
                name="extendedenddate"
                data-cy="extendedenddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Probability"
                id="employee-projects-probability"
                name="probability"
                data-cy="probability"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField id="employee-projects-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-projects-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-projects-assignor" name="assignor" data-cy="assignor" label="Assignor" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-projects" replace color="info">
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

export default EmployeeProjectsUpdate;
