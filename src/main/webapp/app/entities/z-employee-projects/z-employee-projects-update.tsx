import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvents } from 'app/shared/model/events.model';
import { getEntities as getEvents } from 'app/entities/events/events.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';
import { getEntities as getEmployeeProjects } from 'app/entities/employee-projects/employee-projects.reducer';
import { IZEmployeeProjects } from 'app/shared/model/z-employee-projects.model';
import { getEntity, updateEntity, createEntity, reset } from './z-employee-projects.reducer';

export const ZEmployeeProjectsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const events = useAppSelector(state => state.events.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const projects = useAppSelector(state => state.projects.entities);
  const employeeProjects = useAppSelector(state => state.employeeProjects.entities);
  const zEmployeeProjectsEntity = useAppSelector(state => state.zEmployeeProjects.entity);
  const loading = useAppSelector(state => state.zEmployeeProjects.loading);
  const updating = useAppSelector(state => state.zEmployeeProjects.updating);
  const updateSuccess = useAppSelector(state => state.zEmployeeProjects.updateSuccess);

  const handleClose = () => {
    navigate('/z-employee-projects' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEvents({}));
    dispatch(getEmployees({}));
    dispatch(getProjects({}));
    dispatch(getEmployeeProjects({}));
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
    values.previousenddate = convertDateTimeToServer(values.previousenddate);
    values.extendedenddate = convertDateTimeToServer(values.extendedenddate);

    const entity = {
      ...zEmployeeProjectsEntity,
      ...values,
      event: events.find(it => it.id.toString() === values.event.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      assignor: employees.find(it => it.id.toString() === values.assignor.toString()),
      projectmanager: employees.find(it => it.id.toString() === values.projectmanager.toString()),
      project: projects.find(it => it.id.toString() === values.project.toString()),
      employeeproject: employeeProjects.find(it => it.id.toString() === values.employeeproject.toString()),
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
          previousenddate: displayDefaultDateTime(),
          extendedenddate: displayDefaultDateTime(),
        }
      : {
          ...zEmployeeProjectsEntity,
          startdate: convertDateTimeFromServer(zEmployeeProjectsEntity.startdate),
          enddate: convertDateTimeFromServer(zEmployeeProjectsEntity.enddate),
          createdat: convertDateTimeFromServer(zEmployeeProjectsEntity.createdat),
          updatedat: convertDateTimeFromServer(zEmployeeProjectsEntity.updatedat),
          previousenddate: convertDateTimeFromServer(zEmployeeProjectsEntity.previousenddate),
          extendedenddate: convertDateTimeFromServer(zEmployeeProjectsEntity.extendedenddate),
          event: zEmployeeProjectsEntity?.event?.id,
          employee: zEmployeeProjectsEntity?.employee?.id,
          project: zEmployeeProjectsEntity?.project?.id,
          employeeproject: zEmployeeProjectsEntity?.employeeproject?.id,
          assignor: zEmployeeProjectsEntity?.assignor?.id,
          projectmanager: zEmployeeProjectsEntity?.projectmanager?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.zEmployeeProjects.home.createOrEditLabel" data-cy="ZEmployeeProjectsCreateUpdateHeading">
            Create or edit a Z Employee Projects
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
                <ValidatedField name="id" required readOnly id="z-employee-projects-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Status" id="z-employee-projects-status" name="status" data-cy="status" check type="checkbox" />
              <ValidatedField
                label="Type"
                id="z-employee-projects-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  maxLength: { value: 12, message: 'This field cannot be longer than 12 characters.' },
                }}
              />
              <ValidatedField
                label="Startdate"
                id="z-employee-projects-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="z-employee-projects-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Name"
                id="z-employee-projects-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Allocation"
                id="z-employee-projects-allocation"
                name="allocation"
                data-cy="allocation"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Billed"
                id="z-employee-projects-billed"
                name="billed"
                data-cy="billed"
                type="text"
                validate={{
                  maxLength: { value: 15, message: 'This field cannot be longer than 15 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="z-employee-projects-createdat"
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
                id="z-employee-projects-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deliverymanagerid"
                id="z-employee-projects-deliverymanagerid"
                name="deliverymanagerid"
                data-cy="deliverymanagerid"
                type="text"
              />
              <ValidatedField
                label="Architectid"
                id="z-employee-projects-architectid"
                name="architectid"
                data-cy="architectid"
                type="text"
              />
              <ValidatedField
                label="Notes"
                id="z-employee-projects-notes"
                name="notes"
                data-cy="notes"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Previousenddate"
                id="z-employee-projects-previousenddate"
                name="previousenddate"
                data-cy="previousenddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Data" id="z-employee-projects-data" name="data" data-cy="data" type="text" />
              <ValidatedField
                label="Extendedenddate"
                id="z-employee-projects-extendedenddate"
                name="extendedenddate"
                data-cy="extendedenddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Probability"
                id="z-employee-projects-probability"
                name="probability"
                data-cy="probability"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField id="z-employee-projects-event" name="event" data-cy="event" label="Event" type="select">
                <option value="" key="0" />
                {events
                  ? events.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="z-employee-projects-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="z-employee-projects-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="z-employee-projects-employeeproject"
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
              <ValidatedField id="z-employee-projects-assignor" name="assignor" data-cy="assignor" label="Assignor" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="z-employee-projects-projectmanager"
                name="projectmanager"
                data-cy="projectmanager"
                label="Projectmanager"
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/z-employee-projects" replace color="info">
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

export default ZEmployeeProjectsUpdate;
