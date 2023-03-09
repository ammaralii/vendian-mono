import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHrPerformanceCycles } from 'app/shared/model/hr-performance-cycles.model';
import { getEntities as getHrPerformanceCycles } from 'app/entities/hr-performance-cycles/hr-performance-cycles.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { getEntity, updateEntity, createEntity, reset } from './performance-cycle-employee-rating.reducer';

export const PerformanceCycleEmployeeRatingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hrPerformanceCycles = useAppSelector(state => state.hrPerformanceCycles.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const performanceCycleEmployeeRatingEntity = useAppSelector(state => state.performanceCycleEmployeeRating.entity);
  const loading = useAppSelector(state => state.performanceCycleEmployeeRating.loading);
  const updating = useAppSelector(state => state.performanceCycleEmployeeRating.updating);
  const updateSuccess = useAppSelector(state => state.performanceCycleEmployeeRating.updateSuccess);

  const handleClose = () => {
    navigate('/performance-cycle-employee-rating' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHrPerformanceCycles({}));
    dispatch(getEmployees({}));
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
      ...performanceCycleEmployeeRatingEntity,
      ...values,
      performancecycle: hrPerformanceCycles.find(it => it.id.toString() === values.performancecycle.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      manager: employees.find(it => it.id.toString() === values.manager.toString()),
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
          ...performanceCycleEmployeeRatingEntity,
          createdAt: convertDateTimeFromServer(performanceCycleEmployeeRatingEntity.createdAt),
          updatedAt: convertDateTimeFromServer(performanceCycleEmployeeRatingEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(performanceCycleEmployeeRatingEntity.deletedAt),
          performancecycle: performanceCycleEmployeeRatingEntity?.performancecycle?.id,
          employee: performanceCycleEmployeeRatingEntity?.employee?.id,
          manager: performanceCycleEmployeeRatingEntity?.manager?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="vendianMonoApp.performanceCycleEmployeeRating.home.createOrEditLabel"
            data-cy="PerformanceCycleEmployeeRatingCreateUpdateHeading"
          >
            Create or edit a Performance Cycle Employee Rating
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
                  id="performance-cycle-employee-rating-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedBlobField
                label="Rating"
                id="performance-cycle-employee-rating-rating"
                name="rating"
                data-cy="rating"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Comment"
                id="performance-cycle-employee-rating-comment"
                name="comment"
                data-cy="comment"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Created At"
                id="performance-cycle-employee-rating-createdAt"
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
                id="performance-cycle-employee-rating-updatedAt"
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
                id="performance-cycle-employee-rating-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="performance-cycle-employee-rating-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="performance-cycle-employee-rating-performancecycle"
                name="performancecycle"
                data-cy="performancecycle"
                label="Performancecycle"
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
              <ValidatedField
                id="performance-cycle-employee-rating-employee"
                name="employee"
                data-cy="employee"
                label="Employee"
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
                id="performance-cycle-employee-rating-manager"
                name="manager"
                data-cy="manager"
                label="Manager"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/performance-cycle-employee-rating"
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

export default PerformanceCycleEmployeeRatingUpdate;
