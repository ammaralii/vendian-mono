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
import { ILeaveTypes } from 'app/shared/model/leave-types.model';
import { getEntities as getLeaveTypes } from 'app/entities/leave-types/leave-types.reducer';
import { ILeaveTypeApprovalRules } from 'app/shared/model/leave-type-approval-rules.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-type-approval-rules.reducer';

export const LeaveTypeApprovalRulesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveApprovalCriterias = useAppSelector(state => state.leaveApprovalCriterias.entities);
  const leaveTypes = useAppSelector(state => state.leaveTypes.entities);
  const leaveTypeApprovalRulesEntity = useAppSelector(state => state.leaveTypeApprovalRules.entity);
  const loading = useAppSelector(state => state.leaveTypeApprovalRules.loading);
  const updating = useAppSelector(state => state.leaveTypeApprovalRules.updating);
  const updateSuccess = useAppSelector(state => state.leaveTypeApprovalRules.updateSuccess);

  const handleClose = () => {
    navigate('/leave-type-approval-rules' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveApprovalCriterias({}));
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
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...leaveTypeApprovalRulesEntity,
      ...values,
      leaveApprovalCriteria: leaveApprovalCriterias.find(it => it.id.toString() === values.leaveApprovalCriteria.toString()),
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
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...leaveTypeApprovalRulesEntity,
          effDate: convertDateTimeFromServer(leaveTypeApprovalRulesEntity.effDate),
          createdAt: convertDateTimeFromServer(leaveTypeApprovalRulesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(leaveTypeApprovalRulesEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(leaveTypeApprovalRulesEntity.deletedAt),
          leaveApprovalCriteria: leaveTypeApprovalRulesEntity?.leaveApprovalCriteria?.id,
          leaveType: leaveTypeApprovalRulesEntity?.leaveType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveTypeApprovalRules.home.createOrEditLabel" data-cy="LeaveTypeApprovalRulesCreateUpdateHeading">
            Create or edit a Leave Type Approval Rules
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
                <ValidatedField name="id" required readOnly id="leave-type-approval-rules-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Eff Date"
                id="leave-type-approval-rules-effDate"
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
                id="leave-type-approval-rules-createdAt"
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
                id="leave-type-approval-rules-updatedAt"
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
                id="leave-type-approval-rules-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="leave-type-approval-rules-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="leave-type-approval-rules-leaveApprovalCriteria"
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
              <ValidatedField
                id="leave-type-approval-rules-leaveType"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-type-approval-rules" replace color="info">
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

export default LeaveTypeApprovalRulesUpdate;
