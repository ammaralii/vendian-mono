import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClaimStatus } from 'app/shared/model/claim-status.model';
import { getEntities as getClaimStatuses } from 'app/entities/claim-status/claim-status.reducer';
import { IClaimRequests } from 'app/shared/model/claim-requests.model';
import { getEntities as getClaimRequests } from 'app/entities/claim-requests/claim-requests.reducer';
import { IClaimApprovers } from 'app/shared/model/claim-approvers.model';
import { getEntity, updateEntity, createEntity, reset } from './claim-approvers.reducer';

export const ClaimApproversUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const claimStatuses = useAppSelector(state => state.claimStatus.entities);
  const claimRequests = useAppSelector(state => state.claimRequests.entities);
  const claimApproversEntity = useAppSelector(state => state.claimApprovers.entity);
  const loading = useAppSelector(state => state.claimApprovers.loading);
  const updating = useAppSelector(state => state.claimApprovers.updating);
  const updateSuccess = useAppSelector(state => state.claimApprovers.updateSuccess);

  const handleClose = () => {
    navigate('/claim-approvers' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClaimStatuses({}));
    dispatch(getClaimRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...claimApproversEntity,
      ...values,
      status: claimStatuses.find(it => it.id.toString() === values.status.toString()),
      claimrequest: claimRequests.find(it => it.id.toString() === values.claimrequest.toString()),
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...claimApproversEntity,
          createdat: convertDateTimeFromServer(claimApproversEntity.createdat),
          updatedat: convertDateTimeFromServer(claimApproversEntity.updatedat),
          status: claimApproversEntity?.status?.id,
          claimrequest: claimApproversEntity?.claimrequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.claimApprovers.home.createOrEditLabel" data-cy="ClaimApproversCreateUpdateHeading">
            Create or edit a Claim Approvers
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
                <ValidatedField name="id" required readOnly id="claim-approvers-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Referenceid" id="claim-approvers-referenceid" name="referenceid" data-cy="referenceid" type="text" />
              <ValidatedField
                label="Designation"
                id="claim-approvers-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Approveorder"
                id="claim-approvers-approveorder"
                name="approveorder"
                data-cy="approveorder"
                type="text"
              />
              <ValidatedField
                label="Reference"
                id="claim-approvers-reference"
                name="reference"
                data-cy="reference"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Comments"
                id="claim-approvers-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Approvedby"
                id="claim-approvers-approvedby"
                name="approvedby"
                data-cy="approvedby"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="claim-approvers-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="claim-approvers-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="claim-approvers-status" name="status" data-cy="status" label="Status" type="select">
                <option value="" key="0" />
                {claimStatuses
                  ? claimStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-approvers-claimrequest"
                name="claimrequest"
                data-cy="claimrequest"
                label="Claimrequest"
                type="select"
              >
                <option value="" key="0" />
                {claimRequests
                  ? claimRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim-approvers" replace color="info">
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

export default ClaimApproversUpdate;
