import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { getEntities as getPcRatings } from 'app/entities/pc-ratings/pc-ratings.reducer';
import { IUserGoals } from 'app/shared/model/user-goals.model';
import { getEntities as getUserGoals } from 'app/entities/user-goals/user-goals.reducer';
import { IUserGoalRaterAttributes } from 'app/shared/model/user-goal-rater-attributes.model';
import { getEntity, updateEntity, createEntity, reset } from './user-goal-rater-attributes.reducer';

export const UserGoalRaterAttributesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pcRatings = useAppSelector(state => state.pcRatings.entities);
  const userGoals = useAppSelector(state => state.userGoals.entities);
  const userGoalRaterAttributesEntity = useAppSelector(state => state.userGoalRaterAttributes.entity);
  const loading = useAppSelector(state => state.userGoalRaterAttributes.loading);
  const updating = useAppSelector(state => state.userGoalRaterAttributes.updating);
  const updateSuccess = useAppSelector(state => state.userGoalRaterAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/user-goal-rater-attributes' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPcRatings({}));
    dispatch(getUserGoals({}));
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
      ...userGoalRaterAttributesEntity,
      ...values,
      rating: pcRatings.find(it => it.id.toString() === values.rating.toString()),
      usergoal: userGoals.find(it => it.id.toString() === values.usergoal.toString()),
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
          ...userGoalRaterAttributesEntity,
          createdAt: convertDateTimeFromServer(userGoalRaterAttributesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userGoalRaterAttributesEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(userGoalRaterAttributesEntity.deletedAt),
          rating: userGoalRaterAttributesEntity?.rating?.id,
          usergoal: userGoalRaterAttributesEntity?.usergoal?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userGoalRaterAttributes.home.createOrEditLabel" data-cy="UserGoalRaterAttributesCreateUpdateHeading">
            Create or edit a User Goal Rater Attributes
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
                <ValidatedField name="id" required readOnly id="user-goal-rater-attributes-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField
                label="Ugra Rating"
                id="user-goal-rater-attributes-ugraRating"
                name="ugraRating"
                data-cy="ugraRating"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Comment"
                id="user-goal-rater-attributes-comment"
                name="comment"
                data-cy="comment"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Created At"
                id="user-goal-rater-attributes-createdAt"
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
                id="user-goal-rater-attributes-updatedAt"
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
                id="user-goal-rater-attributes-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-goal-rater-attributes-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="user-goal-rater-attributes-rating" name="rating" data-cy="rating" label="Rating" type="select" required>
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
              <ValidatedField id="user-goal-rater-attributes-usergoal" name="usergoal" data-cy="usergoal" label="Usergoal" type="select">
                <option value="" key="0" />
                {userGoals
                  ? userGoals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-goal-rater-attributes" replace color="info">
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

export default UserGoalRaterAttributesUpdate;
