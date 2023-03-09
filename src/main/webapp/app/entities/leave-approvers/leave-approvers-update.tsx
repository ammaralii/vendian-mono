import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveApprovalCriterias } from 'app/shared/model/leave-approval-criterias.model';
import { getEntities as getLeaveApprovalCriterias } from 'app/entities/leave-approval-criterias/leave-approval-criterias.reducer';
import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { ILeaveApprovers } from 'app/shared/model/leave-approvers.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-approvers.reducer';

export const LeaveApproversUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveApprovalCriterias = useAppSelector(state => state.leaveApprovalCriterias.entities);
  const attributes = useAppSelector(state => state.attributes.entities);
  const leaveApproversEntity = useAppSelector(state => state.leaveApprovers.entity);
  const loading = useAppSelector(state => state.leaveApprovers.loading);
  const updating = useAppSelector(state => state.leaveApprovers.updating);
  const updateSuccess = useAppSelector(state => state.leaveApprovers.updateSuccess);

  const handleClose = () => {
    navigate('/leave-approvers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveApprovalCriterias({}));
    dispatch(getAttributes({}));
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
      ...leaveApproversEntity,
      ...values,
      leaveApprovalCriteria: leaveApprovalCriterias.find(it => it.id.toString() === values.leaveApprovalCriteria.toString()),
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
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
          ...leaveApproversEntity,
          effDate: convertDateTimeFromServer(leaveApproversEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveApproversEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveApproversEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveApproversEntity.endDate),
          leaveApprovalCriteria: leaveApproversEntity?.leaveApprovalCriteria?.id,
          attribute: leaveApproversEntity?.attribute?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveApprovers.home.createOrEditLabel" data-cy="LeaveApproversCreateUpdateHeading">
            Create or edit a Leave Approvers
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
                <ValidatedField name="id" required readOnly id="leave-approvers-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Source"
                id="leave-approvers-source"
                name="source"
                data-cy="source"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 17, message: 'This field cannot be longer than 17 characters.' },
                }}
              />
              <ValidatedField
                label="Min Approvals"
                id="leave-approvers-minApprovals"
                name="minApprovals"
                data-cy="minApprovals"
                type="text"
              />
              <ValidatedField
                label="Priority"
                id="leave-approvers-priority"
                name="priority"
                data-cy="priority"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="leave-approvers-effDate"
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
                id="leave-approvers-createdAt"
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
                id="leave-approvers-updatedAt"
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
                id="leave-approvers-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-approvers-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-approvers-leaveApprovalCriteria"
                name="leaveApprovalCriteria"
                data-cy="leaveApprovalCriteria"
                label="Leave Approval Criteria"
                type="select"
                required
              >
                <option value="" key="0" />
                {leaveApprovalCriterias
                  ? leaveApprovalCriterias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="leave-approvers-attribute" name="attribute" data-cy="attribute" label="Attribute" type="select" required>
                <option value="" key="0" />
                {attributes
                  ? attributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-approvers" replace color="info">
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

export default LeaveApproversUpdate;
