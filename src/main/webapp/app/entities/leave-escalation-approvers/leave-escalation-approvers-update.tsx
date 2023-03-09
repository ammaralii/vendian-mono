import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveEscalationCriterias } from 'app/shared/model/leave-escalation-criterias.model';
import { getEntities as getLeaveEscalationCriterias } from 'app/entities/leave-escalation-criterias/leave-escalation-criterias.reducer';
import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { ILeaveEscalationApprovers } from 'app/shared/model/leave-escalation-approvers.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-escalation-approvers.reducer';

export const LeaveEscalationApproversUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveEscalationCriterias = useAppSelector(state => state.leaveEscalationCriterias.entities);
  const attributes = useAppSelector(state => state.attributes.entities);
  const leaveEscalationApproversEntity = useAppSelector(state => state.leaveEscalationApprovers.entity);
  const loading = useAppSelector(state => state.leaveEscalationApprovers.loading);
  const updating = useAppSelector(state => state.leaveEscalationApprovers.updating);
  const updateSuccess = useAppSelector(state => state.leaveEscalationApprovers.updateSuccess);

  const handleClose = () => {
    navigate('/leave-escalation-approvers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveEscalationCriterias({}));
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
      ...leaveEscalationApproversEntity,
      ...values,
      leaveEscalationCriteria: leaveEscalationCriterias.find(it => it.id.toString() === values.leaveEscalationCriteria.toString()),
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
          ...leaveEscalationApproversEntity,
          effDate: convertDateTimeFromServer(leaveEscalationApproversEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveEscalationApproversEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveEscalationApproversEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveEscalationApproversEntity.endDate),
          leaveEscalationCriteria: leaveEscalationApproversEntity?.leaveEscalationCriteria?.id,
          attribute: leaveEscalationApproversEntity?.attribute?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveEscalationApprovers.home.createOrEditLabel" data-cy="LeaveEscalationApproversCreateUpdateHeading">
            Create or edit a Leave Escalation Approvers
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
                <ValidatedField name="id" required readOnly id="leave-escalation-approvers-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Source"
                id="leave-escalation-approvers-source"
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
                id="leave-escalation-approvers-minApprovals"
                name="minApprovals"
                data-cy="minApprovals"
                type="text"
              />
              <ValidatedField
                label="Priority"
                id="leave-escalation-approvers-priority"
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
                id="leave-escalation-approvers-effDate"
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
                id="leave-escalation-approvers-createdAt"
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
                id="leave-escalation-approvers-updatedAt"
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
                id="leave-escalation-approvers-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-escalation-approvers-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-escalation-approvers-leaveEscalationCriteria"
                name="leaveEscalationCriteria"
                data-cy="leaveEscalationCriteria"
                label="Leave Escalation Criteria"
                type="select"
                required
              >
                <option value="" key="0" />
                {leaveEscalationCriterias
                  ? leaveEscalationCriterias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="leave-escalation-approvers-attribute"
                name="attribute"
                data-cy="attribute"
                label="Attribute"
                type="select"
                required
              >
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-escalation-approvers" replace color="info">
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

export default LeaveEscalationApproversUpdate;
