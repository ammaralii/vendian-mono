import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveTypes } from 'app/shared/model/leave-types.model';
import { getEntities as getLeaveTypes } from 'app/entities/leave-types/leave-types.reducer';
import { ILeaveTypeConfigurations } from 'app/shared/model/leave-type-configurations.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-type-configurations.reducer';

export const LeaveTypeConfigurationsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveTypes = useAppSelector(state => state.leaveTypes.entities);
  const leaveTypeConfigurationsEntity = useAppSelector(state => state.leaveTypeConfigurations.entity);
  const loading = useAppSelector(state => state.leaveTypeConfigurations.loading);
  const updating = useAppSelector(state => state.leaveTypeConfigurations.updating);
  const updateSuccess = useAppSelector(state => state.leaveTypeConfigurations.updateSuccess);

  const handleClose = () => {
    navigate('/leave-type-configurations' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.effDate = convertDateTimeToServer(values.effDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...leaveTypeConfigurationsEntity,
      ...values,
      leaveType: leaveTypes.find(it => it.id.toString() === values.leaveType.toString()),
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
          effDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...leaveTypeConfigurationsEntity,
          effDate: convertDateTimeFromServer(leaveTypeConfigurationsEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveTypeConfigurationsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveTypeConfigurationsEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveTypeConfigurationsEntity.endDate),
          leaveType: leaveTypeConfigurationsEntity?.leaveType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveTypeConfigurations.home.createOrEditLabel" data-cy="LeaveTypeConfigurationsCreateUpdateHeading">
            Create or edit a Leave Type Configurations
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
                <ValidatedField name="id" required readOnly id="leave-type-configurations-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="No Of Leaves"
                id="leave-type-configurations-noOfLeaves"
                name="noOfLeaves"
                data-cy="noOfLeaves"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Tenure Cycle"
                id="leave-type-configurations-tenureCycle"
                name="tenureCycle"
                data-cy="tenureCycle"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 5, message: 'This field cannot be longer than 5 characters.' },
                }}
              />
              <ValidatedField label="To" id="leave-type-configurations-to" name="to" data-cy="to" type="text" />
              <ValidatedField label="From" id="leave-type-configurations-from" name="from" data-cy="from" type="text" />
              <ValidatedField
                label="Inclusivity"
                id="leave-type-configurations-inclusivity"
                name="inclusivity"
                data-cy="inclusivity"
                type="text"
                validate={{
                  maxLength: { value: 14, message: 'This field cannot be longer than 14 characters.' },
                }}
              />
              <ValidatedField label="Max Quota" id="leave-type-configurations-maxQuota" name="maxQuota" data-cy="maxQuota" type="text" />
              <ValidatedField
                label="Is Accured"
                id="leave-type-configurations-isAccured"
                name="isAccured"
                data-cy="isAccured"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Eff Date"
                id="leave-type-configurations-effDate"
                name="effDate"
                data-cy="effDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="leave-type-configurations-createdAt"
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
                id="leave-type-configurations-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="End Date"
                id="leave-type-configurations-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-type-configurations-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-type-configurations-leaveType"
                name="leaveType"
                data-cy="leaveType"
                label="Leave Type"
                type="select"
                required
              >
                <option value="" key="0" />
                {leaveTypes
                  ? leaveTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-type-configurations" replace color="info">
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

export default LeaveTypeConfigurationsUpdate;
