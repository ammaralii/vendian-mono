import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApproverFlows } from 'app/shared/model/approver-flows.model';
import { getEntity, updateEntity, createEntity, reset } from './approver-flows.reducer';

export const ApproverFlowsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const approverFlowsEntity = useAppSelector(state => state.approverFlows.entity);
  const loading = useAppSelector(state => state.approverFlows.loading);
  const updating = useAppSelector(state => state.approverFlows.updating);
  const updateSuccess = useAppSelector(state => state.approverFlows.updateSuccess);

  const handleClose = () => {
    navigate('/approver-flows' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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

    const entity = {
      ...approverFlowsEntity,
      ...values,
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
        }
      : {
          ...approverFlowsEntity,
          effDate: convertDateTimeFromServer(approverFlowsEntity.effDate),
          createdAt: convertDateTimeFromServer(approverFlowsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(approverFlowsEntity.updatedAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.approverFlows.home.createOrEditLabel" data-cy="ApproverFlowsCreateUpdateHeading">
            Create or edit a Approver Flows
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
                <ValidatedField name="id" required readOnly id="approver-flows-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Reference Type"
                id="approver-flows-referenceType"
                name="referenceType"
                data-cy="referenceType"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Approver Status"
                id="approver-flows-approverStatus"
                name="approverStatus"
                data-cy="approverStatus"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Approval"
                id="approver-flows-approval"
                name="approval"
                data-cy="approval"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 3, message: 'This field cannot be longer than 3 characters.' },
                }}
              />
              <ValidatedField
                label="Current Status"
                id="approver-flows-currentStatus"
                name="currentStatus"
                data-cy="currentStatus"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Next Status"
                id="approver-flows-nextStatus"
                name="nextStatus"
                data-cy="nextStatus"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="approver-flows-effDate"
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
                id="approver-flows-createdAt"
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
                id="approver-flows-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Version"
                id="approver-flows-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approver-flows" replace color="info">
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

export default ApproverFlowsUpdate;
