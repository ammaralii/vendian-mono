import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITracks } from 'app/shared/model/tracks.model';
import { getEntities as getTracks } from 'app/entities/tracks/tracks.reducer';
import { IQuestionsFrequencyPerTrack } from 'app/shared/model/questions-frequency-per-track.model';
import { getEntity, updateEntity, createEntity, reset } from './questions-frequency-per-track.reducer';

export const QuestionsFrequencyPerTrackUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tracks = useAppSelector(state => state.tracks.entities);
  const questionsFrequencyPerTrackEntity = useAppSelector(state => state.questionsFrequencyPerTrack.entity);
  const loading = useAppSelector(state => state.questionsFrequencyPerTrack.loading);
  const updating = useAppSelector(state => state.questionsFrequencyPerTrack.updating);
  const updateSuccess = useAppSelector(state => state.questionsFrequencyPerTrack.updateSuccess);

  const handleClose = () => {
    navigate('/questions-frequency-per-track' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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

    const entity = {
      ...questionsFrequencyPerTrackEntity,
      ...values,
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
        }
      : {
          ...questionsFrequencyPerTrackEntity,
          createdat: convertDateTimeFromServer(questionsFrequencyPerTrackEntity.createdat),
          updatedat: convertDateTimeFromServer(questionsFrequencyPerTrackEntity.updatedat),
          track: questionsFrequencyPerTrackEntity?.track?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.questionsFrequencyPerTrack.home.createOrEditLabel" data-cy="QuestionsFrequencyPerTrackCreateUpdateHeading">
            Create or edit a Questions Frequency Per Track
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="questions-frequency-per-track-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Question"
                id="questions-frequency-per-track-question"
                name="question"
                data-cy="question"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Frequency"
                id="questions-frequency-per-track-frequency"
                name="frequency"
                data-cy="frequency"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Createdat"
                id="questions-frequency-per-track-createdat"
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
                id="questions-frequency-per-track-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="questions-frequency-per-track-track" name="track" data-cy="track" label="Track" type="select" required>
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/questions-frequency-per-track"
                replace
                color="info"
              >
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

export default QuestionsFrequencyPerTrackUpdate;
