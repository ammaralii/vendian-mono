import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClaimRequests } from 'app/shared/model/claim-requests.model';
import { getEntities as getClaimRequests } from 'app/entities/claim-requests/claim-requests.reducer';
import { IClaimTypes } from 'app/shared/model/claim-types.model';
import { getEntities as getClaimTypes } from 'app/entities/claim-types/claim-types.reducer';
import { IClaimDetails } from 'app/shared/model/claim-details.model';
import { getEntity, updateEntity, createEntity, reset } from './claim-details.reducer';

export const ClaimDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const claimRequests = useAppSelector(state => state.claimRequests.entities);
  const claimTypes = useAppSelector(state => state.claimTypes.entities);
  const claimDetailsEntity = useAppSelector(state => state.claimDetails.entity);
  const loading = useAppSelector(state => state.claimDetails.loading);
  const updating = useAppSelector(state => state.claimDetails.updating);
  const updateSuccess = useAppSelector(state => state.claimDetails.updateSuccess);

  const handleClose = () => {
    navigate('/claim-details' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClaimRequests({}));
    dispatch(getClaimTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...claimDetailsEntity,
      ...values,
      claimrequest: claimRequests.find(it => it.id.toString() === values.claimrequest.toString()),
      claimtype: claimTypes.find(it => it.id.toString() === values.claimtype.toString()),
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...claimDetailsEntity,
          startdate: convertDateTimeFromServer(claimDetailsEntity.startdate),
          enddate: convertDateTimeFromServer(claimDetailsEntity.enddate),
          createdat: convertDateTimeFromServer(claimDetailsEntity.createdat),
          updatedat: convertDateTimeFromServer(claimDetailsEntity.updatedat),
          claimrequest: claimDetailsEntity?.claimrequest?.id,
          claimtype: claimDetailsEntity?.claimtype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.claimDetails.home.createOrEditLabel" data-cy="ClaimDetailsCreateUpdateHeading">
            Create or edit a Claim Details
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
                <ValidatedField name="id" required readOnly id="claim-details-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Amount" id="claim-details-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField
                label="Startdate"
                id="claim-details-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="claim-details-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Description"
                id="claim-details-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="claim-details-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="claim-details-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="claim-details-claimrequest" name="claimrequest" data-cy="claimrequest" label="Claimrequest" type="select">
                <option value="" key="0" />
                {claimRequests
                  ? claimRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="claim-details-claimtype" name="claimtype" data-cy="claimtype" label="Claimtype" type="select">
                <option value="" key="0" />
                {claimTypes
                  ? claimTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim-details" replace color="info">
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

export default ClaimDetailsUpdate;
