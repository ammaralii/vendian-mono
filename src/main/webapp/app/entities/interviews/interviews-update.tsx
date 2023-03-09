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
import { ITracks } from 'app/shared/model/tracks.model';
import { getEntities as getTracks } from 'app/entities/tracks/tracks.reducer';
import { IInterviews } from 'app/shared/model/interviews.model';
import { getEntity, updateEntity, createEntity, reset } from './interviews.reducer';

export const InterviewsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const projects = useAppSelector(state => state.projects.entities);
  const tracks = useAppSelector(state => state.tracks.entities);
  const interviewsEntity = useAppSelector(state => state.interviews.entity);
  const loading = useAppSelector(state => state.interviews.loading);
  const updating = useAppSelector(state => state.interviews.updating);
  const updateSuccess = useAppSelector(state => state.interviews.updateSuccess);

  const handleClose = () => {
    navigate('/interviews' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getProjects({}));
    dispatch(getTracks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.scheduledat = convertDateTimeToServer(values.scheduledat);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...interviewsEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      project: projects.find(it => it.id.toString() === values.project.toString()),
      track: tracks.find(it => it.id.toString() === values.track.toString()),
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
          scheduledat: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...interviewsEntity,
          scheduledat: convertDateTimeFromServer(interviewsEntity.scheduledat),
          createdat: convertDateTimeFromServer(interviewsEntity.createdat),
          updatedat: convertDateTimeFromServer(interviewsEntity.updatedat),
          deletedat: convertDateTimeFromServer(interviewsEntity.deletedat),
          employee: interviewsEntity?.employee?.id,
          project: interviewsEntity?.project?.id,
          track: interviewsEntity?.track?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.interviews.home.createOrEditLabel" data-cy="InterviewsCreateUpdateHeading">
            Create or edit a Interviews
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="interviews-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Result"
                id="interviews-result"
                name="result"
                data-cy="result"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Clientcomments"
                id="interviews-clientcomments"
                name="clientcomments"
                data-cy="clientcomments"
                type="text"
                validate={{
                  maxLength: { value: 1000, message: 'This field cannot be longer than 1000 characters.' },
                }}
              />
              <ValidatedField
                label="Lmcomments"
                id="interviews-lmcomments"
                name="lmcomments"
                data-cy="lmcomments"
                type="text"
                validate={{
                  maxLength: { value: 1000, message: 'This field cannot be longer than 1000 characters.' },
                }}
              />
              <ValidatedField
                label="Scheduledat"
                id="interviews-scheduledat"
                name="scheduledat"
                data-cy="scheduledat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="interviews-createdat"
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
                id="interviews-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deletedat"
                id="interviews-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="interviews-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="interviews-project" name="project" data-cy="project" label="Project" type="select" required>
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="interviews-track" name="track" data-cy="track" label="Track" type="select" required>
                <option value="" key="0" />
                {tracks
                  ? tracks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/interviews" replace color="info">
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

export default InterviewsUpdate;
