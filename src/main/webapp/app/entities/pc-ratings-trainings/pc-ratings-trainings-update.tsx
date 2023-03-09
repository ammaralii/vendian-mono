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
import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { getEntities as getPcRatings } from 'app/entities/pc-ratings/pc-ratings.reducer';
import { IPcRatingsTrainings } from 'app/shared/model/pc-ratings-trainings.model';
import { getEntity, updateEntity, createEntity, reset } from './pc-ratings-trainings.reducer';

export const PcRatingsTrainingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const pcRatings = useAppSelector(state => state.pcRatings.entities);
  const pcRatingsTrainingsEntity = useAppSelector(state => state.pcRatingsTrainings.entity);
  const loading = useAppSelector(state => state.pcRatingsTrainings.loading);
  const updating = useAppSelector(state => state.pcRatingsTrainings.updating);
  const updateSuccess = useAppSelector(state => state.pcRatingsTrainings.updateSuccess);

  const handleClose = () => {
    navigate('/pc-ratings-trainings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getPcRatings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...pcRatingsTrainingsEntity,
      ...values,
      successorFor: employees.find(it => it.id.toString() === values.successorFor.toString()),
      rating: pcRatings.find(it => it.id.toString() === values.rating.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...pcRatingsTrainingsEntity,
          createdAt: convertDateTimeFromServer(pcRatingsTrainingsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(pcRatingsTrainingsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(pcRatingsTrainingsEntity.deletedAt),
          successorFor: pcRatingsTrainingsEntity?.successorFor?.id,
          rating: pcRatingsTrainingsEntity?.rating?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.pcRatingsTrainings.home.createOrEditLabel" data-cy="PcRatingsTrainingsCreateUpdateHeading">
            Create or edit a Pc Ratings Trainings
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
                <ValidatedField name="id" required readOnly id="pc-ratings-trainings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Strength"
                id="pc-ratings-trainings-strength"
                name="strength"
                data-cy="strength"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Development Area"
                id="pc-ratings-trainings-developmentArea"
                name="developmentArea"
                data-cy="developmentArea"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Career Ambition"
                id="pc-ratings-trainings-careerAmbition"
                name="careerAmbition"
                data-cy="careerAmbition"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Short Course"
                id="pc-ratings-trainings-shortCourse"
                name="shortCourse"
                data-cy="shortCourse"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Technical Training"
                id="pc-ratings-trainings-technicalTraining"
                name="technicalTraining"
                data-cy="technicalTraining"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Soft Skills Training"
                id="pc-ratings-trainings-softSkillsTraining"
                name="softSkillsTraining"
                data-cy="softSkillsTraining"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Critical Position"
                id="pc-ratings-trainings-criticalPosition"
                name="criticalPosition"
                data-cy="criticalPosition"
                check
                type="checkbox"
              />
              <ValidatedField
                label="High Potential"
                id="pc-ratings-trainings-highPotential"
                name="highPotential"
                data-cy="highPotential"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Created At"
                id="pc-ratings-trainings-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updated At"
                id="pc-ratings-trainings-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deleted At"
                id="pc-ratings-trainings-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="pc-ratings-trainings-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="pc-ratings-trainings-successorFor"
                name="successorFor"
                data-cy="successorFor"
                label="Successor For"
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
              <ValidatedField id="pc-ratings-trainings-rating" name="rating" data-cy="rating" label="Rating" type="select" required>
                <option value="" key="0" />
                {pcRatings
                  ? pcRatings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pc-ratings-trainings" replace color="info">
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

export default PcRatingsTrainingsUpdate;
