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
import { IGoals } from 'app/shared/model/goals.model';
import { getEntities as getGoals } from 'app/entities/goals/goals.reducer';
import { IUserGoals } from 'app/shared/model/user-goals.model';
import { getEntity, updateEntity, createEntity, reset } from './user-goals.reducer';

export const UserGoalsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const goals = useAppSelector(state => state.goals.entities);
  const userGoalsEntity = useAppSelector(state => state.userGoals.entity);
  const loading = useAppSelector(state => state.userGoals.loading);
  const updating = useAppSelector(state => state.userGoals.updating);
  const updateSuccess = useAppSelector(state => state.userGoals.updateSuccess);

  const handleClose = () => {
    navigate('/user-goals' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
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
      ...userGoalsEntity,
      ...values,
      user: employees.find(it => it.id.toString() === values.user.toString()),
      referenceGoal: goals.find(it => it.id.toString() === values.referenceGoal.toString()),
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
          ...userGoalsEntity,
          createdAt: convertDateTimeFromServer(userGoalsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userGoalsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(userGoalsEntity.deletedAt),
          user: userGoalsEntity?.user?.id,
          referenceGoal: userGoalsEntity?.referenceGoal?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userGoals.home.createOrEditLabel" data-cy="UserGoalsCreateUpdateHeading">
            Create or edit a User Goals
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="user-goals-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Title"
                id="user-goals-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Description"
                id="user-goals-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Measurement"
                id="user-goals-measurement"
                name="measurement"
                data-cy="measurement"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField label="Weightage" id="user-goals-weightage" name="weightage" data-cy="weightage" type="text" />
              <ValidatedField label="Progress" id="user-goals-progress" name="progress" data-cy="progress" type="text" />
              <ValidatedField
                label="Status"
                id="user-goals-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  maxLength: { value: 16, message: 'This field cannot be longer than 16 characters.' },
                }}
              />
              <ValidatedField label="Due Date" id="user-goals-dueDate" name="dueDate" data-cy="dueDate" type="date" />
              <ValidatedField
                label="Created At"
                id="user-goals-createdAt"
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
                id="user-goals-updatedAt"
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
                id="user-goals-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-goals-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="user-goals-user" name="user" data-cy="user" label="User" type="select">
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
                id="user-goals-referenceGoal"
                name="referenceGoal"
                data-cy="referenceGoal"
                label="Reference Goal"
                type="select"
              >
                <option value="" key="0" />
                {goals
                  ? goals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-goals" replace color="info">
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

export default UserGoalsUpdate;
