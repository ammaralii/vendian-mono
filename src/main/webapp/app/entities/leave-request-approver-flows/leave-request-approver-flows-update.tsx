import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { getEntities as getLeaveStatuses } from 'app/entities/leave-statuses/leave-statuses.reducer';
import { ILeaveRequestApproverFlows } from 'app/shared/model/leave-request-approver-flows.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-request-approver-flows.reducer';

export const LeaveRequestApproverFlowsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveRequestApproverFlowsEntity = useAppSelector(state => state.leaveRequestApproverFlows.entity);
  const loading = useAppSelector(state => state.leaveRequestApproverFlows.loading);
  const updating = useAppSelector(state => state.leaveRequestApproverFlows.updating);
  const updateSuccess = useAppSelector(state => state.leaveRequestApproverFlows.updateSuccess);

  const handleClose = () => {
    navigate('/leave-request-approver-flows' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveStatuses({}));
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
      ...leaveRequestApproverFlowsEntity,
      ...values,
      approverStatus: leaveStatuses.find(it => it.id.toString() === values.approverStatus.toString()),
      currentLeaveRequestStatus: leaveStatuses.find(it => it.id.toString() === values.currentLeaveRequestStatus.toString()),
      nextLeaveRequestStatus: leaveStatuses.find(it => it.id.toString() === values.nextLeaveRequestStatus.toString()),
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
          ...leaveRequestApproverFlowsEntity,
          effDate: convertDateTimeFromServer(leaveRequestApproverFlowsEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveRequestApproverFlowsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveRequestApproverFlowsEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveRequestApproverFlowsEntity.endDate),
          approverStatus: leaveRequestApproverFlowsEntity?.approverStatus?.id,
          currentLeaveRequestStatus: leaveRequestApproverFlowsEntity?.currentLeaveRequestStatus?.id,
          nextLeaveRequestStatus: leaveRequestApproverFlowsEntity?.nextLeaveRequestStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveRequestApproverFlows.home.createOrEditLabel" data-cy="LeaveRequestApproverFlowsCreateUpdateHeading">
            Create or edit a Leave Request Approver Flows
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
                <ValidatedField name="id" required readOnly id="leave-request-approver-flows-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Approvals"
                id="leave-request-approver-flows-approvals"
                name="approvals"
                data-cy="approvals"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 3, message: 'This field cannot be longer than 3 characters.' },
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="leave-request-approver-flows-effDate"
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
                id="leave-request-approver-flows-createdAt"
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
                id="leave-request-approver-flows-updatedAt"
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
                id="leave-request-approver-flows-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-request-approver-flows-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-request-approver-flows-approverStatus"
                name="approverStatus"
                data-cy="approverStatus"
                label="Approver Status"
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
                id="leave-request-approver-flows-currentLeaveRequestStatus"
                name="currentLeaveRequestStatus"
                data-cy="currentLeaveRequestStatus"
                label="Current Leave Request Status"
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
                id="leave-request-approver-flows-nextLeaveRequestStatus"
                name="nextLeaveRequestStatus"
                data-cy="nextLeaveRequestStatus"
                label="Next Leave Request Status"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/leave-request-approver-flows"
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

export default LeaveRequestApproverFlowsUpdate;
