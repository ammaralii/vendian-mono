import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApproverGroups } from 'app/shared/model/approver-groups.model';
import { getEntities as getApproverGroups } from 'app/entities/approver-groups/approver-groups.reducer';
import { IApprovers } from 'app/shared/model/approvers.model';
import { getEntity, updateEntity, createEntity, reset } from './approvers.reducer';

export const ApproversUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const approverGroups = useAppSelector(state => state.approverGroups.entities);
  const approversEntity = useAppSelector(state => state.approvers.entity);
  const loading = useAppSelector(state => state.approvers.loading);
  const updating = useAppSelector(state => state.approvers.updating);
  const updateSuccess = useAppSelector(state => state.approvers.updateSuccess);

  const handleClose = () => {
    navigate('/approvers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getApproverGroups({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.stausDate = convertDateTimeToServer(values.stausDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...approversEntity,
      ...values,
      approverGroup: approverGroups.find(it => it.id.toString() === values.approverGroup.toString()),
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
          stausDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...approversEntity,
          stausDate: convertDateTimeFromServer(approversEntity.stausDate),
          createdAt: convertDateTimeFromServer(approversEntity.createdAt),
          updatedAt: convertDateTimeFromServer(approversEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(approversEntity.deletedAt),
          approverGroup: approversEntity?.approverGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.approvers.home.createOrEditLabel" data-cy="ApproversCreateUpdateHeading">
            Create or edit a Approvers
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="approvers-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="User Id"
                id="approvers-userId"
                name="userId"
                data-cy="userId"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Reference"
                id="approvers-reference"
                name="reference"
                data-cy="reference"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 17, message: 'This field cannot be longer than 17 characters.' },
                }}
              />
              <ValidatedField
                label="As"
                id="approvers-as"
                name="as"
                data-cy="as"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Comment"
                id="approvers-comment"
                name="comment"
                data-cy="comment"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Status"
                id="approvers-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Staus Date"
                id="approvers-stausDate"
                name="stausDate"
                data-cy="stausDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Priority"
                id="approvers-priority"
                name="priority"
                data-cy="priority"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Created At"
                id="approvers-createdAt"
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
                id="approvers-updatedAt"
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
                id="approvers-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="approvers-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="approvers-approverGroup"
                name="approverGroup"
                data-cy="approverGroup"
                label="Approver Group"
                type="select"
                required
              >
                <option value="" key="0" />
                {approverGroups
                  ? approverGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approvers" replace color="info">
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

export default ApproversUpdate;
