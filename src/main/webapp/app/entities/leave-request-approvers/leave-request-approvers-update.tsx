import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveRequests } from 'app/shared/model/leave-requests.model';
import { getEntities as getLeaveRequests } from 'app/entities/leave-requests/leave-requests.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { getEntities as getLeaveStatuses } from 'app/entities/leave-statuses/leave-statuses.reducer';
import { ILeaveRequestApprovers } from 'app/shared/model/leave-request-approvers.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-request-approvers.reducer';

export const LeaveRequestApproversUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveRequests = useAppSelector(state => state.leaveRequests.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveRequestApproversEntity = useAppSelector(state => state.leaveRequestApprovers.entity);
  const loading = useAppSelector(state => state.leaveRequestApprovers.loading);
  const updating = useAppSelector(state => state.leaveRequestApprovers.updating);
  const updateSuccess = useAppSelector(state => state.leaveRequestApprovers.updateSuccess);

  const handleClose = () => {
    navigate('/leave-request-approvers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveRequests({}));
    dispatch(getEmployees({}));
    dispatch(getLeaveStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.statusDate = convertDateTimeToServer(values.statusDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...leaveRequestApproversEntity,
      ...values,
      leaveRequest: leaveRequests.find(it => it.id.toString() === values.leaveRequest.toString()),
      user: employees.find(it => it.id.toString() === values.user.toString()),
      status: leaveStatuses.find(it => it.id.toString() === values.status.toString()),
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
          statusDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...leaveRequestApproversEntity,
          statusDate: convertDateTimeFromServer(leaveRequestApproversEntity.statusDate),
          createdAt: convertDateTimeFromServer(leaveRequestApproversEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveRequestApproversEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(leaveRequestApproversEntity.deletedAt),
          leaveRequest: leaveRequestApproversEntity?.leaveRequest?.id,
          user: leaveRequestApproversEntity?.user?.id,
          status: leaveRequestApproversEntity?.status?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveRequestApprovers.home.createOrEditLabel" data-cy="LeaveRequestApproversCreateUpdateHeading">
            Create or edit a Leave Request Approvers
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
                <ValidatedField name="id" required readOnly id="leave-request-approvers-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Reference"
                id="leave-request-approvers-reference"
                name="reference"
                data-cy="reference"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 17, message: 'This field cannot be longer than 17 characters.' },
                }}
              />
              <ValidatedField
                label="As"
                id="leave-request-approvers-as"
                name="as"
                data-cy="as"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Comments"
                id="leave-request-approvers-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Approver Group"
                id="leave-request-approvers-approverGroup"
                name="approverGroup"
                data-cy="approverGroup"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Priority"
                id="leave-request-approvers-priority"
                name="priority"
                data-cy="priority"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Status Date"
                id="leave-request-approvers-statusDate"
                name="statusDate"
                data-cy="statusDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="leave-request-approvers-createdAt"
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
                id="leave-request-approvers-updatedAt"
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
                id="leave-request-approvers-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-request-approvers-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-request-approvers-leaveRequest"
                name="leaveRequest"
                data-cy="leaveRequest"
                label="Leave Request"
                type="select"
                required
              >
                <option value="" key="0" />
                {leaveRequests
                  ? leaveRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="leave-request-approvers-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="leave-request-approvers-status" name="status" data-cy="status" label="Status" type="select" required>
                <option value="" key="0" />
                {leaveStatuses
                  ? leaveStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-request-approvers" replace color="info">
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

export default LeaveRequestApproversUpdate;
