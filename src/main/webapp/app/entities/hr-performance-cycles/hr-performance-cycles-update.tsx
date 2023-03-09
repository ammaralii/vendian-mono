import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHrPerformanceCycles } from 'app/shared/model/hr-performance-cycles.model';
import { getEntity, updateEntity, createEntity, reset } from './hr-performance-cycles.reducer';

export const HrPerformanceCyclesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hrPerformanceCyclesEntity = useAppSelector(state => state.hrPerformanceCycles.entity);
  const loading = useAppSelector(state => state.hrPerformanceCycles.loading);
  const updating = useAppSelector(state => state.hrPerformanceCycles.updating);
  const updateSuccess = useAppSelector(state => state.hrPerformanceCycles.updateSuccess);

  const handleClose = () => {
    navigate('/hr-performance-cycles' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...hrPerformanceCyclesEntity,
      ...values,
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
          ...hrPerformanceCyclesEntity,
          createdAt: convertDateTimeFromServer(hrPerformanceCyclesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(hrPerformanceCyclesEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(hrPerformanceCyclesEntity.deletedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.hrPerformanceCycles.home.createOrEditLabel" data-cy="HrPerformanceCyclesCreateUpdateHeading">
            Create or edit a Hr Performance Cycles
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
                <ValidatedField name="id" required readOnly id="hr-performance-cycles-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Title"
                id="hr-performance-cycles-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Freeze" id="hr-performance-cycles-freeze" name="freeze" data-cy="freeze" check type="checkbox" />
              <ValidatedField label="Due Date" id="hr-performance-cycles-dueDate" name="dueDate" data-cy="dueDate" type="date" />
              <ValidatedField label="Start Date" id="hr-performance-cycles-startDate" name="startDate" data-cy="startDate" type="date" />
              <ValidatedField label="End Date" id="hr-performance-cycles-endDate" name="endDate" data-cy="endDate" type="date" />
              <ValidatedField
                label="Created At"
                id="hr-performance-cycles-createdAt"
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
                id="hr-performance-cycles-updatedAt"
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
                id="hr-performance-cycles-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="hr-performance-cycles-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hr-performance-cycles" replace color="info">
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

export default HrPerformanceCyclesUpdate;
