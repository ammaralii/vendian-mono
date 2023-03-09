import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClaimTypes } from 'app/shared/model/claim-types.model';
import { getEntity, updateEntity, createEntity, reset } from './claim-types.reducer';

export const ClaimTypesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const claimTypesEntity = useAppSelector(state => state.claimTypes.entity);
  const loading = useAppSelector(state => state.claimTypes.loading);
  const updating = useAppSelector(state => state.claimTypes.updating);
  const updateSuccess = useAppSelector(state => state.claimTypes.updateSuccess);

  const handleClose = () => {
    navigate('/claim-types' + location.search);
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
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...claimTypesEntity,
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...claimTypesEntity,
          createdat: convertDateTimeFromServer(claimTypesEntity.createdat),
          updatedat: convertDateTimeFromServer(claimTypesEntity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.claimTypes.home.createOrEditLabel" data-cy="ClaimTypesCreateUpdateHeading">
            Create or edit a Claim Types
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="claim-types-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Claimtype"
                id="claim-types-claimtype"
                name="claimtype"
                data-cy="claimtype"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Daterangerequired"
                id="claim-types-daterangerequired"
                name="daterangerequired"
                data-cy="daterangerequired"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Descriptionrequired"
                id="claim-types-descriptionrequired"
                name="descriptionrequired"
                data-cy="descriptionrequired"
                check
                type="checkbox"
              />
              <ValidatedField label="Parentid" id="claim-types-parentid" name="parentid" data-cy="parentid" type="text" />
              <ValidatedField
                label="Createdat"
                id="claim-types-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="claim-types-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim-types" replace color="info">
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

export default ClaimTypesUpdate;
