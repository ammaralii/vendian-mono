import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDealResourceStatus } from 'app/shared/model/deal-resource-status.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-resource-status.reducer';

export const DealResourceStatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealResourceStatusEntity = useAppSelector(state => state.dealResourceStatus.entity);
  const loading = useAppSelector(state => state.dealResourceStatus.loading);
  const updating = useAppSelector(state => state.dealResourceStatus.updating);
  const updateSuccess = useAppSelector(state => state.dealResourceStatus.updateSuccess);

  const handleClose = () => {
    navigate('/deal-resource-status' + location.search);
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
    values.effectivedate = convertDateTimeToServer(values.effectivedate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...dealResourceStatusEntity,
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
          effectivedate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...dealResourceStatusEntity,
          effectivedate: convertDateTimeFromServer(dealResourceStatusEntity.effectivedate),
          enddate: convertDateTimeFromServer(dealResourceStatusEntity.enddate),
          createdat: convertDateTimeFromServer(dealResourceStatusEntity.createdat),
          updatedat: convertDateTimeFromServer(dealResourceStatusEntity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.dealResourceStatus.home.createOrEditLabel" data-cy="DealResourceStatusCreateUpdateHeading">
            Create or edit a Deal Resource Status
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
                <ValidatedField name="id" required readOnly id="deal-resource-status-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Displayname"
                id="deal-resource-status-displayname"
                name="displayname"
                data-cy="displayname"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Group"
                id="deal-resource-status-group"
                name="group"
                data-cy="group"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="System Key"
                id="deal-resource-status-systemKey"
                name="systemKey"
                data-cy="systemKey"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Effectivedate"
                id="deal-resource-status-effectivedate"
                name="effectivedate"
                data-cy="effectivedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="deal-resource-status-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="deal-resource-status-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deal-resource-status-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deal-resource-status" replace color="info">
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

export default DealResourceStatusUpdate;
