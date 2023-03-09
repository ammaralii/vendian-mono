import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { ILeaveStatuses } from 'app/shared/model/leave-statuses.model';
import { getEntities as getLeaveStatuses } from 'app/entities/leave-statuses/leave-statuses.reducer';
import { ILeaveEscalationCriterias } from 'app/shared/model/leave-escalation-criterias.model';
import { getEntities as getLeaveEscalationCriterias } from 'app/entities/leave-escalation-criterias/leave-escalation-criterias.reducer';
import { IUserAttributeEscalationRules } from 'app/shared/model/user-attribute-escalation-rules.model';
import { getEntity, updateEntity, createEntity, reset } from './user-attribute-escalation-rules.reducer';

export const UserAttributeEscalationRulesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributes = useAppSelector(state => state.attributes.entities);
  const leaveStatuses = useAppSelector(state => state.leaveStatuses.entities);
  const leaveEscalationCriterias = useAppSelector(state => state.leaveEscalationCriterias.entities);
  const userAttributeEscalationRulesEntity = useAppSelector(state => state.userAttributeEscalationRules.entity);
  const loading = useAppSelector(state => state.userAttributeEscalationRules.loading);
  const updating = useAppSelector(state => state.userAttributeEscalationRules.updating);
  const updateSuccess = useAppSelector(state => state.userAttributeEscalationRules.updateSuccess);

  const handleClose = () => {
    navigate('/user-attribute-escalation-rules' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAttributes({}));
    dispatch(getLeaveStatuses({}));
    dispatch(getLeaveEscalationCriterias({}));
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
      ...userAttributeEscalationRulesEntity,
      ...values,
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
      approverStatus: leaveStatuses.find(it => it.id.toString() === values.approverStatus.toString()),
      leaveescalationcriteria: leaveEscalationCriterias.find(it => it.id.toString() === values.leaveescalationcriteria.toString()),
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
          ...userAttributeEscalationRulesEntity,
          effDate: convertDateTimeFromServer(userAttributeEscalationRulesEntity.effDate),
          createdAt: convertDateTimeFromServer(userAttributeEscalationRulesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userAttributeEscalationRulesEntity.updatedAt),
          endDate: convertDateTimeFromServer(userAttributeEscalationRulesEntity.endDate),
          attribute: userAttributeEscalationRulesEntity?.attribute?.id,
          approverStatus: userAttributeEscalationRulesEntity?.approverStatus?.id,
          leaveescalationcriteria: userAttributeEscalationRulesEntity?.leaveescalationcriteria?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="vendianMonoApp.userAttributeEscalationRules.home.createOrEditLabel"
            data-cy="UserAttributeEscalationRulesCreateUpdateHeading"
          >
            Create or edit a User Attribute Escalation Rules
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="user-attribute-escalation-rules-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Leave Escalation Criteria Id"
                id="user-attribute-escalation-rules-leaveEscalationCriteriaId"
                name="leaveEscalationCriteriaId"
                data-cy="leaveEscalationCriteriaId"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="No Of Days"
                id="user-attribute-escalation-rules-noOfDays"
                name="noOfDays"
                data-cy="noOfDays"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="user-attribute-escalation-rules-effDate"
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
                id="user-attribute-escalation-rules-createdAt"
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
                id="user-attribute-escalation-rules-updatedAt"
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
                id="user-attribute-escalation-rules-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-attribute-escalation-rules-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="user-attribute-escalation-rules-attribute"
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
              <ValidatedField
                id="user-attribute-escalation-rules-approverStatus"
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
                id="user-attribute-escalation-rules-leaveescalationcriteria"
                name="leaveescalationcriteria"
                data-cy="leaveescalationcriteria"
                label="Leaveescalationcriteria"
                type="select"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/user-attribute-escalation-rules"
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

export default UserAttributeEscalationRulesUpdate;
