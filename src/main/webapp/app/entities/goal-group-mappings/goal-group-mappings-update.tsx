import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGoalGroups } from 'app/shared/model/goal-groups.model';
import { getEntities as getGoalGroups } from 'app/entities/goal-groups/goal-groups.reducer';
import { IGoals } from 'app/shared/model/goals.model';
import { getEntities as getGoals } from 'app/entities/goals/goals.reducer';
import { IGoalGroupMappings } from 'app/shared/model/goal-group-mappings.model';
import { getEntity, updateEntity, createEntity, reset } from './goal-group-mappings.reducer';

export const GoalGroupMappingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const goalGroups = useAppSelector(state => state.goalGroups.entities);
  const goals = useAppSelector(state => state.goals.entities);
  const goalGroupMappingsEntity = useAppSelector(state => state.goalGroupMappings.entity);
  const loading = useAppSelector(state => state.goalGroupMappings.loading);
  const updating = useAppSelector(state => state.goalGroupMappings.updating);
  const updateSuccess = useAppSelector(state => state.goalGroupMappings.updateSuccess);

  const handleClose = () => {
    navigate('/goal-group-mappings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGoalGroups({}));
    dispatch(getGoals({}));
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
      ...goalGroupMappingsEntity,
      ...values,
      goalGroup: goalGroups.find(it => it.id.toString() === values.goalGroup.toString()),
      goal: goals.find(it => it.id.toString() === values.goal.toString()),
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
          ...goalGroupMappingsEntity,
          createdAt: convertDateTimeFromServer(goalGroupMappingsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(goalGroupMappingsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(goalGroupMappingsEntity.deletedAt),
          goalGroup: goalGroupMappingsEntity?.goalGroup?.id,
          goal: goalGroupMappingsEntity?.goal?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.goalGroupMappings.home.createOrEditLabel" data-cy="GoalGroupMappingsCreateUpdateHeading">
            Create or edit a Goal Group Mappings
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
                <ValidatedField name="id" required readOnly id="goal-group-mappings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Weightage"
                id="goal-group-mappings-weightage"
                name="weightage"
                data-cy="weightage"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Created At"
                id="goal-group-mappings-createdAt"
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
                id="goal-group-mappings-updatedAt"
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
                id="goal-group-mappings-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="goal-group-mappings-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="goal-group-mappings-goalGroup"
                name="goalGroup"
                data-cy="goalGroup"
                label="Goal Group"
                type="select"
                required
              >
                <option value="" key="0" />
                {goalGroups
                  ? goalGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="goal-group-mappings-goal" name="goal" data-cy="goal" label="Goal" type="select" required>
                <option value="" key="0" />
                {goals
                  ? goals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/goal-group-mappings" replace color="info">
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

export default GoalGroupMappingsUpdate;
