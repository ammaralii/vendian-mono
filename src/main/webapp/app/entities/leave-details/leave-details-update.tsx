import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaves } from 'app/shared/model/leaves.model';
import { getEntities as getLeaves } from 'app/entities/leaves/leaves.reducer';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';
import { getEntities as getLeaveTypes } from 'app/entities/leave-types/leave-types.reducer';
import { ILeaveDetails } from 'app/shared/model/leave-details.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-details.reducer';

export const LeaveDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaves = useAppSelector(state => state.leaves.entities);
  const leaveTypes = useAppSelector(state => state.leaveTypes.entities);
  const leaveDetailsEntity = useAppSelector(state => state.leaveDetails.entity);
  const loading = useAppSelector(state => state.leaveDetails.loading);
  const updating = useAppSelector(state => state.leaveDetails.updating);
  const updateSuccess = useAppSelector(state => state.leaveDetails.updateSuccess);

  const handleClose = () => {
    navigate('/leave-details' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaves({}));
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
      ...leaveDetailsEntity,
      ...values,
      leave: leaves.find(it => it.id.toString() === values.leave.toString()),
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
          ...leaveDetailsEntity,
          effDate: convertDateTimeFromServer(leaveDetailsEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveDetailsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveDetailsEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveDetailsEntity.endDate),
          leave: leaveDetailsEntity?.leave?.id,
          leaveType: leaveDetailsEntity?.leaveType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveDetails.home.createOrEditLabel" data-cy="LeaveDetailsCreateUpdateHeading">
            Create or edit a Leave Details
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
                <ValidatedField name="id" required readOnly id="leave-details-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Total" id="leave-details-total" name="total" data-cy="total" type="text" />
              <ValidatedField
                label="Used"
                id="leave-details-used"
                name="used"
                data-cy="used"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="leave-details-effDate"
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
                id="leave-details-createdAt"
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
                id="leave-details-updatedAt"
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
                id="leave-details-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-details-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="leave-details-leave" name="leave" data-cy="leave" label="Leave" type="select" required>
                <option value="" key="0" />
                {leaves
                  ? leaves.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="leave-details-leaveType" name="leaveType" data-cy="leaveType" label="Leave Type" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-details" replace color="info">
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

export default LeaveDetailsUpdate;
