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
import { ILeaveStatusWorkFlows } from 'app/shared/model/leave-status-work-flows.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-status-work-flows.reducer';

export const LeaveStatusWorkFlowsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveStatusWorkFlowsEntity = useAppSelector(state => state.leaveStatusWorkFlows.entity);
  const loading = useAppSelector(state => state.leaveStatusWorkFlows.loading);
  const updating = useAppSelector(state => state.leaveStatusWorkFlows.updating);
  const updateSuccess = useAppSelector(state => state.leaveStatusWorkFlows.updateSuccess);

  const handleClose = () => {
    navigate('/leave-status-work-flows' + location.search);
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...leaveStatusWorkFlowsEntity,
      ...values,
      currentStatus: leaveStatuses.find(it => it.id.toString() === values.currentStatus.toString()),
      nextStatus: leaveStatuses.find(it => it.id.toString() === values.nextStatus.toString()),
      skipToStatus: leaveStatuses.find(it => it.id.toString() === values.skipToStatus.toString()),
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
          ...leaveStatusWorkFlowsEntity,
          createdAt: convertDateTimeFromServer(leaveStatusWorkFlowsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveStatusWorkFlowsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(leaveStatusWorkFlowsEntity.deletedAt),
          currentStatus: leaveStatusWorkFlowsEntity?.currentStatus?.id,
          nextStatus: leaveStatusWorkFlowsEntity?.nextStatus?.id,
          skipToStatus: leaveStatusWorkFlowsEntity?.skipToStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveStatusWorkFlows.home.createOrEditLabel" data-cy="LeaveStatusWorkFlowsCreateUpdateHeading">
            Create or edit a Leave Status Work Flows
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
                <ValidatedField name="id" required readOnly id="leave-status-work-flows-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="If Approvals"
                id="leave-status-work-flows-ifApprovals"
                name="ifApprovals"
                data-cy="ifApprovals"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Approval Required"
                id="leave-status-work-flows-approvalRequired"
                name="approvalRequired"
                data-cy="approvalRequired"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Created At"
                id="leave-status-work-flows-createdAt"
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
                id="leave-status-work-flows-updatedAt"
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
                id="leave-status-work-flows-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-status-work-flows-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-status-work-flows-currentStatus"
                name="currentStatus"
                data-cy="currentStatus"
                label="Current Status"
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
                id="leave-status-work-flows-nextStatus"
                name="nextStatus"
                data-cy="nextStatus"
                label="Next Status"
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
                id="leave-status-work-flows-skipToStatus"
                name="skipToStatus"
                data-cy="skipToStatus"
                label="Skip To Status"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-status-work-flows" replace color="info">
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

export default LeaveStatusWorkFlowsUpdate;
