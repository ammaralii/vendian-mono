import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAddresses } from 'app/shared/model/addresses.model';
import { getEntity, updateEntity, createEntity, reset } from './addresses.reducer';

export const AddressesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addressesEntity = useAppSelector(state => state.addresses.entity);
  const loading = useAppSelector(state => state.addresses.loading);
  const updating = useAppSelector(state => state.addresses.updating);
  const updateSuccess = useAppSelector(state => state.addresses.updateSuccess);

  const handleClose = () => {
    navigate('/addresses' + location.search);
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
      ...addressesEntity,
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
          ...addressesEntity,
          createdat: convertDateTimeFromServer(addressesEntity.createdat),
          updatedat: convertDateTimeFromServer(addressesEntity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.addresses.home.createOrEditLabel" data-cy="AddressesCreateUpdateHeading">
            Create or edit a Addresses
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="addresses-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Address"
                id="addresses-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="City"
                id="addresses-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Province"
                id="addresses-province"
                name="province"
                data-cy="province"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Country"
                id="addresses-country"
                name="country"
                data-cy="country"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Postalcode"
                id="addresses-postalcode"
                name="postalcode"
                data-cy="postalcode"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Isdeleted" id="addresses-isdeleted" name="isdeleted" data-cy="isdeleted" check type="checkbox" />
              <ValidatedField
                label="Createdat"
                id="addresses-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="addresses-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/addresses" replace color="info">
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

export default AddressesUpdate;
