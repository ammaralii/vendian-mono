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
import { ILeaveApprovalCriterias } from 'app/shared/model/leave-approval-criterias.model';
import { getEntities as getLeaveApprovalCriterias } from 'app/entities/leave-approval-criterias/leave-approval-criterias.reducer';
import { IUserRelationApprovalRules } from 'app/shared/model/user-relation-approval-rules.model';
import { getEntity, updateEntity, createEntity, reset } from './user-relation-approval-rules.reducer';

export const UserRelationApprovalRulesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributes = useAppSelector(state => state.attributes.entities);
  const leaveApprovalCriterias = useAppSelector(state => state.leaveApprovalCriterias.entities);
  const userRelationApprovalRulesEntity = useAppSelector(state => state.userRelationApprovalRules.entity);
  const loading = useAppSelector(state => state.userRelationApprovalRules.loading);
  const updating = useAppSelector(state => state.userRelationApprovalRules.updating);
  const updateSuccess = useAppSelector(state => state.userRelationApprovalRules.updateSuccess);

  const handleClose = () => {
    navigate('/user-relation-approval-rules' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAttributes({}));
    dispatch(getLeaveApprovalCriterias({}));
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
      ...userRelationApprovalRulesEntity,
      ...values,
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
      leaveApprovalCriteria: leaveApprovalCriterias.find(it => it.id.toString() === values.leaveApprovalCriteria.toString()),
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
          ...userRelationApprovalRulesEntity,
          effDate: convertDateTimeFromServer(userRelationApprovalRulesEntity.effDate),
          createdAt: convertDateTimeFromServer(userRelationApprovalRulesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userRelationApprovalRulesEntity.updatedAt),
          endDate: convertDateTimeFromServer(userRelationApprovalRulesEntity.endDate),
          attribute: userRelationApprovalRulesEntity?.attribute?.id,
          leaveApprovalCriteria: userRelationApprovalRulesEntity?.leaveApprovalCriteria?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userRelationApprovalRules.home.createOrEditLabel" data-cy="UserRelationApprovalRulesCreateUpdateHeading">
            Create or edit a User Relation Approval Rules
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
                <ValidatedField name="id" required readOnly id="user-relation-approval-rules-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Eff Date"
                id="user-relation-approval-rules-effDate"
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
                id="user-relation-approval-rules-createdAt"
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
                id="user-relation-approval-rules-updatedAt"
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
                id="user-relation-approval-rules-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-relation-approval-rules-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="user-relation-approval-rules-attribute"
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
                id="user-relation-approval-rules-leaveApprovalCriteria"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/user-relation-approval-rules"
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

export default UserRelationApprovalRulesUpdate;
