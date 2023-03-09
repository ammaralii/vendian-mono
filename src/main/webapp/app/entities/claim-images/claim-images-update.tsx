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
import { IClaimImages } from 'app/shared/model/claim-images.model';
import { getEntity, updateEntity, createEntity, reset } from './claim-images.reducer';

export const ClaimImagesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const claimRequests = useAppSelector(state => state.claimRequests.entities);
  const claimImagesEntity = useAppSelector(state => state.claimImages.entity);
  const loading = useAppSelector(state => state.claimImages.loading);
  const updating = useAppSelector(state => state.claimImages.updating);
  const updateSuccess = useAppSelector(state => state.claimImages.updateSuccess);

  const handleClose = () => {
    navigate('/claim-images' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...claimImagesEntity,
      ...values,
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
          ...claimImagesEntity,
          createdat: convertDateTimeFromServer(claimImagesEntity.createdat),
          updatedat: convertDateTimeFromServer(claimImagesEntity.updatedat),
          claimrequest: claimImagesEntity?.claimrequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.claimImages.home.createOrEditLabel" data-cy="ClaimImagesCreateUpdateHeading">
            Create or edit a Claim Images
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="claim-images-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Images"
                id="claim-images-images"
                name="images"
                data-cy="images"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="claim-images-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="claim-images-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="claim-images-claimrequest" name="claimrequest" data-cy="claimrequest" label="Claimrequest" type="select">
                <option value="" key="0" />
                {claimRequests
                  ? claimRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim-images" replace color="info">
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

export default ClaimImagesUpdate;
