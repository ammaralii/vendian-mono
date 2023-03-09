import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IRatings } from 'app/shared/model/ratings.model';
import { getEntities as getRatings } from 'app/entities/ratings/ratings.reducer';
import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';
import { getEntities as getPerformanceCycles } from 'app/entities/performance-cycles/performance-cycles.reducer';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { getEntity, updateEntity, createEntity, reset } from './project-cycles.reducer';

export const ProjectCyclesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projects = useAppSelector(state => state.projects.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const ratings = useAppSelector(state => state.ratings.entities);
  const performanceCycles = useAppSelector(state => state.performanceCycles.entities);
  const projectCyclesEntity = useAppSelector(state => state.projectCycles.entity);
  const loading = useAppSelector(state => state.projectCycles.loading);
  const updating = useAppSelector(state => state.projectCycles.updating);
  const updateSuccess = useAppSelector(state => state.projectCycles.updateSuccess);

  const handleClose = () => {
    navigate('/project-cycles' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjects({}));
    dispatch(getEmployees({}));
    dispatch(getRatings({}));
    dispatch(getPerformanceCycles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.allowedafterduedateat = convertDateTimeToServer(values.allowedafterduedateat);
    values.duedate = convertDateTimeToServer(values.duedate);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...projectCyclesEntity,
      ...values,
      ratings: mapIdList(values.ratings),
      project: projects.find(it => it.id.toString() === values.project.toString()),
      allowedafterduedateby: employees.find(it => it.id.toString() === values.allowedafterduedateby.toString()),
      architect: employees.find(it => it.id.toString() === values.architect.toString()),
      projectmanager: employees.find(it => it.id.toString() === values.projectmanager.toString()),
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
          allowedafterduedateat: displayDefaultDateTime(),
          duedate: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...projectCyclesEntity,
          createdat: convertDateTimeFromServer(projectCyclesEntity.createdat),
          updatedat: convertDateTimeFromServer(projectCyclesEntity.updatedat),
          allowedafterduedateat: convertDateTimeFromServer(projectCyclesEntity.allowedafterduedateat),
          duedate: convertDateTimeFromServer(projectCyclesEntity.duedate),
          deletedat: convertDateTimeFromServer(projectCyclesEntity.deletedat),
          project: projectCyclesEntity?.project?.id,
          allowedafterduedateby: projectCyclesEntity?.allowedafterduedateby?.id,
          architect: projectCyclesEntity?.architect?.id,
          projectmanager: projectCyclesEntity?.projectmanager?.id,
          ratings: projectCyclesEntity?.ratings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.projectCycles.home.createOrEditLabel" data-cy="ProjectCyclesCreateUpdateHeading">
            Create or edit a Project Cycles
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
                <ValidatedField name="id" required readOnly id="project-cycles-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Isactive" id="project-cycles-isactive" name="isactive" data-cy="isactive" check type="checkbox" />
              <ValidatedField
                label="Createdat"
                id="project-cycles-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="project-cycles-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Allowedafterduedateat"
                id="project-cycles-allowedafterduedateat"
                name="allowedafterduedateat"
                data-cy="allowedafterduedateat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Duedate"
                id="project-cycles-duedate"
                name="duedate"
                data-cy="duedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Auditlogs" id="project-cycles-auditlogs" name="auditlogs" data-cy="auditlogs" type="text" />
              <ValidatedField
                label="Deletedat"
                id="project-cycles-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="project-cycles-project" name="project" data-cy="project" label="Project" type="select">
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
                id="project-cycles-allowedafterduedateby"
                name="allowedafterduedateby"
                data-cy="allowedafterduedateby"
                label="Allowedafterduedateby"
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
              <ValidatedField id="project-cycles-architect" name="architect" data-cy="architect" label="Architect" type="select">
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
                id="project-cycles-projectmanager"
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
              <ValidatedField label="Rating" id="project-cycles-rating" data-cy="rating" type="select" multiple name="ratings">
                <option value="" key="0" />
                {ratings
                  ? ratings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project-cycles" replace color="info">
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

export default ProjectCyclesUpdate;
