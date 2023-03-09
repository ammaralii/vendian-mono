import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInterviews } from 'app/shared/model/interviews.model';
import { getEntities as getInterviews } from 'app/entities/interviews/interviews.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { ITracks } from 'app/shared/model/tracks.model';
import { getEntities as getTracks } from 'app/entities/tracks/tracks.reducer';
import { IQuestions } from 'app/shared/model/questions.model';
import { getEntity, updateEntity, createEntity, reset } from './questions.reducer';

export const QuestionsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const interviews = useAppSelector(state => state.interviews.entities);
  const projects = useAppSelector(state => state.projects.entities);
  const tracks = useAppSelector(state => state.tracks.entities);
  const questionsEntity = useAppSelector(state => state.questions.entity);
  const loading = useAppSelector(state => state.questions.loading);
  const updating = useAppSelector(state => state.questions.updating);
  const updateSuccess = useAppSelector(state => state.questions.updateSuccess);

  const handleClose = () => {
    navigate('/questions' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInterviews({}));
    dispatch(getProjects({}));
    dispatch(getTracks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...questionsEntity,
      ...values,
      interview: interviews.find(it => it.id.toString() === values.interview.toString()),
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...questionsEntity,
          createdat: convertDateTimeFromServer(questionsEntity.createdat),
          updatedat: convertDateTimeFromServer(questionsEntity.updatedat),
          deletedat: convertDateTimeFromServer(questionsEntity.deletedat),
          interview: questionsEntity?.interview?.id,
          project: questionsEntity?.project?.id,
          track: questionsEntity?.track?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.questions.home.createOrEditLabel" data-cy="QuestionsCreateUpdateHeading">
            Create or edit a Questions
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="questions-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Text"
                id="questions-text"
                name="text"
                data-cy="text"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Answer"
                id="questions-answer"
                name="answer"
                data-cy="answer"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="questions-createdat"
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
                id="questions-updatedat"
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
                id="questions-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Cleaneduptext"
                id="questions-cleaneduptext"
                name="cleaneduptext"
                data-cy="cleaneduptext"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField id="questions-interview" name="interview" data-cy="interview" label="Interview" type="select">
                <option value="" key="0" />
                {interviews
                  ? interviews.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="questions-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="questions-track" name="track" data-cy="track" label="Track" type="select">
                <option value="" key="0" />
                {tracks
                  ? tracks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/questions" replace color="info">
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

export default QuestionsUpdate;
