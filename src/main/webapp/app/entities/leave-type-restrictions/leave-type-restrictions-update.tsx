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
import { ILeaveTypeRestrictions } from 'app/shared/model/leave-type-restrictions.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-type-restrictions.reducer';

export const LeaveTypeRestrictionsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveTypes = useAppSelector(state => state.leaveTypes.entities);
  const leaveTypeRestrictionsEntity = useAppSelector(state => state.leaveTypeRestrictions.entity);
  const loading = useAppSelector(state => state.leaveTypeRestrictions.loading);
  const updating = useAppSelector(state => state.leaveTypeRestrictions.updating);
  const updateSuccess = useAppSelector(state => state.leaveTypeRestrictions.updateSuccess);

  const handleClose = () => {
    navigate('/leave-type-restrictions' + location.search);
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
      ...leaveTypeRestrictionsEntity,
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
          ...leaveTypeRestrictionsEntity,
          effDate: convertDateTimeFromServer(leaveTypeRestrictionsEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveTypeRestrictionsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveTypeRestrictionsEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveTypeRestrictionsEntity.endDate),
          leaveType: leaveTypeRestrictionsEntity?.leaveType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveTypeRestrictions.home.createOrEditLabel" data-cy="LeaveTypeRestrictionsCreateUpdateHeading">
            Create or edit a Leave Type Restrictions
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
                <ValidatedField name="id" required readOnly id="leave-type-restrictions-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Restriction Json"
                id="leave-type-restrictions-restrictionJson"
                name="restrictionJson"
                data-cy="restrictionJson"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="leave-type-restrictions-effDate"
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
                id="leave-type-restrictions-createdAt"
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
                id="leave-type-restrictions-updatedAt"
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
                id="leave-type-restrictions-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-type-restrictions-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-type-restrictions-leaveType"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-type-restrictions" replace color="info">
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

export default LeaveTypeRestrictionsUpdate;
