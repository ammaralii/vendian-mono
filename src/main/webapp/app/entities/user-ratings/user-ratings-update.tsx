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
import { IHrPerformanceCycles } from 'app/shared/model/hr-performance-cycles.model';
import { getEntities as getHrPerformanceCycles } from 'app/entities/hr-performance-cycles/hr-performance-cycles.reducer';
import { IUserRatings } from 'app/shared/model/user-ratings.model';
import { getEntity, updateEntity, createEntity, reset } from './user-ratings.reducer';

export const UserRatingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const hrPerformanceCycles = useAppSelector(state => state.hrPerformanceCycles.entities);
  const userRatingsEntity = useAppSelector(state => state.userRatings.entity);
  const loading = useAppSelector(state => state.userRatings.loading);
  const updating = useAppSelector(state => state.userRatings.updating);
  const updateSuccess = useAppSelector(state => state.userRatings.updateSuccess);

  const handleClose = () => {
    navigate('/user-ratings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getHrPerformanceCycles({}));
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
      ...userRatingsEntity,
      ...values,
      user: employees.find(it => it.id.toString() === values.user.toString()),
      reviewManager: employees.find(it => it.id.toString() === values.reviewManager.toString()),
      performanceCycle: hrPerformanceCycles.find(it => it.id.toString() === values.performanceCycle.toString()),
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
          ...userRatingsEntity,
          createdAt: convertDateTimeFromServer(userRatingsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userRatingsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(userRatingsEntity.deletedAt),
          user: userRatingsEntity?.user?.id,
          reviewManager: userRatingsEntity?.reviewManager?.id,
          performanceCycle: userRatingsEntity?.performanceCycle?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userRatings.home.createOrEditLabel" data-cy="UserRatingsCreateUpdateHeading">
            Create or edit a User Ratings
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="user-ratings-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Rating"
                id="user-ratings-rating"
                name="rating"
                data-cy="rating"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 7, message: 'This field cannot be longer than 7 characters.' },
                }}
              />
              <ValidatedField
                label="Comment"
                id="user-ratings-comment"
                name="comment"
                data-cy="comment"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="user-ratings-createdAt"
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
                id="user-ratings-updatedAt"
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
                id="user-ratings-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-ratings-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="user-ratings-user" name="user" data-cy="user" label="User" type="select" required>
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
              <ValidatedField
                id="user-ratings-reviewManager"
                name="reviewManager"
                data-cy="reviewManager"
                label="Review Manager"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="user-ratings-performanceCycle"
                name="performanceCycle"
                data-cy="performanceCycle"
                label="Performance Cycle"
                type="select"
                required
              >
                <option value="" key="0" />
                {hrPerformanceCycles
                  ? hrPerformanceCycles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-ratings" replace color="info">
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

export default UserRatingsUpdate;
