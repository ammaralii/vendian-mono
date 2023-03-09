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
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { getEntities as getLeaveStatuses } from 'app/entities/leave-statuses/leave-statuses.reducer';
import { ILeaveTypes } from 'app/shared/model/leave-types.model';
import { getEntities as getLeaveTypes } from 'app/entities/leave-types/leave-types.reducer';
import { ILeaveTypeEscalationRules } from 'app/shared/model/leave-type-escalation-rules.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-type-escalation-rules.reducer';

export const LeaveTypeEscalationRulesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveEscalationCriterias = useAppSelector(state => state.leaveEscalationCriterias.entities);
  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveTypes = useAppSelector(state => state.leaveTypes.entities);
  const leaveTypeEscalationRulesEntity = useAppSelector(state => state.leaveTypeEscalationRules.entity);
  const loading = useAppSelector(state => state.leaveTypeEscalationRules.loading);
  const updating = useAppSelector(state => state.leaveTypeEscalationRules.updating);
  const updateSuccess = useAppSelector(state => state.leaveTypeEscalationRules.updateSuccess);

  const handleClose = () => {
    navigate('/leave-type-escalation-rules' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveEscalationCriterias({}));
    dispatch(getLeaveStatuses({}));
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
      ...leaveTypeEscalationRulesEntity,
      ...values,
      leaveEscalationCriteria: leaveEscalationCriterias.find(it => it.id.toString() === values.leaveEscalationCriteria.toString()),
      leaveRequestStatus: leaveStatuses.find(it => it.id.toString() === values.leaveRequestStatus.toString()),
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
          ...leaveTypeEscalationRulesEntity,
          effDate: convertDateTimeFromServer(leaveTypeEscalationRulesEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveTypeEscalationRulesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveTypeEscalationRulesEntity.updatedAt),
          endDate: convertDateTimeFromServer(leaveTypeEscalationRulesEntity.endDate),
          leaveEscalationCriteria: leaveTypeEscalationRulesEntity?.leaveEscalationCriteria?.id,
          leaveRequestStatus: leaveTypeEscalationRulesEntity?.leaveRequestStatus?.id,
          leaveType: leaveTypeEscalationRulesEntity?.leaveType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveTypeEscalationRules.home.createOrEditLabel" data-cy="LeaveTypeEscalationRulesCreateUpdateHeading">
            Create or edit a Leave Type Escalation Rules
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
                <ValidatedField name="id" required readOnly id="leave-type-escalation-rules-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="No Of Days" id="leave-type-escalation-rules-noOfDays" name="noOfDays" data-cy="noOfDays" type="text" />
              <ValidatedField
                label="Eff Date"
                id="leave-type-escalation-rules-effDate"
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
                id="leave-type-escalation-rules-createdAt"
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
                id="leave-type-escalation-rules-updatedAt"
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
                id="leave-type-escalation-rules-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-type-escalation-rules-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-type-escalation-rules-leaveEscalationCriteria"
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
                id="leave-type-escalation-rules-leaveRequestStatus"
                name="leaveRequestStatus"
                data-cy="leaveRequestStatus"
                label="Leave Request Status"
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
                id="leave-type-escalation-rules-leaveType"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-type-escalation-rules" replace color="info">
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

export default LeaveTypeEscalationRulesUpdate;
