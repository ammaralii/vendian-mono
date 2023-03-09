import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveDetails } from 'app/shared/model/leave-details.model';
import { getEntities as getLeaveDetails } from 'app/entities/leave-details/leave-details.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { ILeaveDetailAdjustmentLogs } from 'app/shared/model/leave-detail-adjustment-logs.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-detail-adjustment-logs.reducer';

export const LeaveDetailAdjustmentLogsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveDetails = useAppSelector(state => state.leaveDetails.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const leaveDetailAdjustmentLogsEntity = useAppSelector(state => state.leaveDetailAdjustmentLogs.entity);
  const loading = useAppSelector(state => state.leaveDetailAdjustmentLogs.loading);
  const updating = useAppSelector(state => state.leaveDetailAdjustmentLogs.updating);
  const updateSuccess = useAppSelector(state => state.leaveDetailAdjustmentLogs.updateSuccess);

  const handleClose = () => {
    navigate('/leave-detail-adjustment-logs' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveDetails({}));
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

    const entity = {
      ...leaveDetailAdjustmentLogsEntity,
      ...values,
      leaveDetail: leaveDetails.find(it => it.id.toString() === values.leaveDetail.toString()),
      adjustedBy: employees.find(it => it.id.toString() === values.adjustedBy.toString()),
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
        }
      : {
          ...leaveDetailAdjustmentLogsEntity,
          createdAt: convertDateTimeFromServer(leaveDetailAdjustmentLogsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveDetailAdjustmentLogsEntity.updatedAt),
          leaveDetail: leaveDetailAdjustmentLogsEntity?.leaveDetail?.id,
          adjustedBy: leaveDetailAdjustmentLogsEntity?.adjustedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveDetailAdjustmentLogs.home.createOrEditLabel" data-cy="LeaveDetailAdjustmentLogsCreateUpdateHeading">
            Create or edit a Leave Detail Adjustment Logs
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
                <ValidatedField name="id" required readOnly id="leave-detail-adjustment-logs-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Action"
                id="leave-detail-adjustment-logs-action"
                name="action"
                data-cy="action"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Count" id="leave-detail-adjustment-logs-count" name="count" data-cy="count" type="text" />
              <ValidatedField
                label="Created At"
                id="leave-detail-adjustment-logs-createdAt"
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
                id="leave-detail-adjustment-logs-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Version" id="leave-detail-adjustment-logs-version" name="version" data-cy="version" type="text" />
              <ValidatedField
                label="Quota Before Adjustment"
                id="leave-detail-adjustment-logs-quotaBeforeAdjustment"
                name="quotaBeforeAdjustment"
                data-cy="quotaBeforeAdjustment"
                type="text"
              />
              <ValidatedField
                label="Quota After Adjustment"
                id="leave-detail-adjustment-logs-quotaAfterAdjustment"
                name="quotaAfterAdjustment"
                data-cy="quotaAfterAdjustment"
                type="text"
              />
              <ValidatedField
                label="Comment"
                id="leave-detail-adjustment-logs-comment"
                name="comment"
                data-cy="comment"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                id="leave-detail-adjustment-logs-leaveDetail"
                name="leaveDetail"
                data-cy="leaveDetail"
                label="Leave Detail"
                type="select"
              >
                <option value="" key="0" />
                {leaveDetails
                  ? leaveDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="leave-detail-adjustment-logs-adjustedBy"
                name="adjustedBy"
                data-cy="adjustedBy"
                label="Adjusted By"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/leave-detail-adjustment-logs"
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

export default LeaveDetailAdjustmentLogsUpdate;
