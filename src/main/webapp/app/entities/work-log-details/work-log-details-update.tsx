import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkLogs } from 'app/shared/model/work-logs.model';
import { getEntities as getWorkLogs } from 'app/entities/work-logs/work-logs.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { IWorkLogDetails } from 'app/shared/model/work-log-details.model';
import { getEntity, updateEntity, createEntity, reset } from './work-log-details.reducer';

export const WorkLogDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workLogs = useAppSelector(state => state.workLogs.entities);
  const projects = useAppSelector(state => state.projects.entities);
  const workLogDetailsEntity = useAppSelector(state => state.workLogDetails.entity);
  const loading = useAppSelector(state => state.workLogDetails.loading);
  const updating = useAppSelector(state => state.workLogDetails.updating);
  const updateSuccess = useAppSelector(state => state.workLogDetails.updateSuccess);

  const handleClose = () => {
    navigate('/work-log-details' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWorkLogs({}));
    dispatch(getProjects({}));
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
      ...workLogDetailsEntity,
      ...values,
      worklog: workLogs.find(it => it.id.toString() === values.worklog.toString()),
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...workLogDetailsEntity,
          createdat: convertDateTimeFromServer(workLogDetailsEntity.createdat),
          updatedat: convertDateTimeFromServer(workLogDetailsEntity.updatedat),
          worklog: workLogDetailsEntity?.worklog?.id,
          project: workLogDetailsEntity?.project?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.workLogDetails.home.createOrEditLabel" data-cy="WorkLogDetailsCreateUpdateHeading">
            Create or edit a Work Log Details
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
                <ValidatedField name="id" required readOnly id="work-log-details-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Percentage"
                id="work-log-details-percentage"
                name="percentage"
                data-cy="percentage"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Hours" id="work-log-details-hours" name="hours" data-cy="hours" type="text" />
              <ValidatedField
                label="Createdat"
                id="work-log-details-createdat"
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
                id="work-log-details-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="work-log-details-worklog" name="worklog" data-cy="worklog" label="Worklog" type="select">
                <option value="" key="0" />
                {workLogs
                  ? workLogs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="work-log-details-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/work-log-details" replace color="info">
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

export default WorkLogDetailsUpdate;
