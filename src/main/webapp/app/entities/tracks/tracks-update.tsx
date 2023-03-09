import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompetencies } from 'app/shared/model/competencies.model';
import { getEntities as getCompetencies } from 'app/entities/competencies/competencies.reducer';
import { ITracks } from 'app/shared/model/tracks.model';
import { getEntity, updateEntity, createEntity, reset } from './tracks.reducer';

export const TracksUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const competencies = useAppSelector(state => state.competencies.entities);
  const tracksEntity = useAppSelector(state => state.tracks.entity);
  const loading = useAppSelector(state => state.tracks.loading);
  const updating = useAppSelector(state => state.tracks.updating);
  const updateSuccess = useAppSelector(state => state.tracks.updateSuccess);

  const handleClose = () => {
    navigate('/tracks' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCompetencies({}));
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
      ...tracksEntity,
      ...values,
      competency: competencies.find(it => it.id.toString() === values.competency.toString()),
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
          ...tracksEntity,
          createdat: convertDateTimeFromServer(tracksEntity.createdat),
          updatedat: convertDateTimeFromServer(tracksEntity.updatedat),
          deletedat: convertDateTimeFromServer(tracksEntity.deletedat),
          competency: tracksEntity?.competency?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.tracks.home.createOrEditLabel" data-cy="TracksCreateUpdateHeading">
            Create or edit a Tracks
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="tracks-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="tracks-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Description"
                id="tracks-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  maxLength: { value: 1000, message: 'This field cannot be longer than 1000 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="tracks-createdat"
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
                id="tracks-updatedat"
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
                id="tracks-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="tracks-competency" name="competency" data-cy="competency" label="Competency" type="select">
                <option value="" key="0" />
                {competencies
                  ? competencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tracks" replace color="info">
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

export default TracksUpdate;
