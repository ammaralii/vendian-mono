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
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { getEntities as getLeaveStatuses } from 'app/entities/leave-statuses/leave-statuses.reducer';
import { getEntities as getLeaveRequests } from 'app/entities/leave-requests/leave-requests.reducer';
import { ILeaveRequests } from 'app/shared/model/leave-requests.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-requests.reducer';

export const LeaveRequestsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveDetails = useAppSelector(state => state.leaveDetails.entities);
  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveRequests = useAppSelector(state => state.leaveRequests.entities);
  const leaveRequestsEntity = useAppSelector(state => state.leaveRequests.entity);
  const loading = useAppSelector(state => state.leaveRequests.loading);
  const updating = useAppSelector(state => state.leaveRequests.updating);
  const updateSuccess = useAppSelector(state => state.leaveRequests.updateSuccess);

  const handleClose = () => {
    navigate('/leave-requests' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveDetails({}));
    dispatch(getLeaveStatuses({}));
    dispatch(getLeaveRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.statusDate = convertDateTimeToServer(values.statusDate);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...leaveRequestsEntity,
      ...values,
      leaveDetail: leaveDetails.find(it => it.id.toString() === values.leaveDetail.toString()),
      leaveStatus: leaveStatuses.find(it => it.id.toString() === values.leaveStatus.toString()),
      parentLeaveRequest: leaveRequests.find(it => it.id.toString() === values.parentLeaveRequest.toString()),
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
          statusDate: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...leaveRequestsEntity,
          createdAt: convertDateTimeFromServer(leaveRequestsEntity.createdAt),
          statusDate: convertDateTimeFromServer(leaveRequestsEntity.statusDate),
          updatedAt: convertDateTimeFromServer(leaveRequestsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(leaveRequestsEntity.deletedAt),
          leaveDetail: leaveRequestsEntity?.leaveDetail?.id,
          leaveStatus: leaveRequestsEntity?.leaveStatus?.id,
          parentLeaveRequest: leaveRequestsEntity?.parentLeaveRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveRequests.home.createOrEditLabel" data-cy="LeaveRequestsCreateUpdateHeading">
            Create or edit a Leave Requests
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
                <ValidatedField name="id" required readOnly id="leave-requests-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Created At"
                id="leave-requests-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Request Start Date"
                id="leave-requests-requestStartDate"
                name="requestStartDate"
                data-cy="requestStartDate"
                type="date"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Request End Date"
                id="leave-requests-requestEndDate"
                name="requestEndDate"
                data-cy="requestEndDate"
                type="date"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Is Half Day"
                id="leave-requests-isHalfDay"
                name="isHalfDay"
                data-cy="isHalfDay"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Status Date"
                id="leave-requests-statusDate"
                name="statusDate"
                data-cy="statusDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Comments"
                id="leave-requests-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Updated At"
                id="leave-requests-updatedAt"
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
                id="leave-requests-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-requests-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-requests-leaveDetail"
                name="leaveDetail"
                data-cy="leaveDetail"
                label="Leave Detail"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="leave-requests-leaveStatus"
                name="leaveStatus"
                data-cy="leaveStatus"
                label="Leave Status"
                type="select"
                required
              >
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
              <ValidatedField
                id="leave-requests-parentLeaveRequest"
                name="parentLeaveRequest"
                data-cy="parentLeaveRequest"
                label="Parent Leave Request"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-requests" replace color="info">
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

export default LeaveRequestsUpdate;
