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
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { getEntity, updateEntity, createEntity, reset } from './user-attributes.reducer';

export const UserAttributesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributes = useAppSelector(state => state.attributes.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const userAttributesEntity = useAppSelector(state => state.userAttributes.entity);
  const loading = useAppSelector(state => state.userAttributes.loading);
  const updating = useAppSelector(state => state.userAttributes.updating);
  const updateSuccess = useAppSelector(state => state.userAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/user-attributes' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAttributes({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.effDate = convertDateTimeToServer(values.effDate);

    const entity = {
      ...userAttributesEntity,
      ...values,
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
      user: employees.find(it => it.id.toString() === values.user.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
          effDate: displayDefaultDateTime(),
        }
      : {
          ...userAttributesEntity,
          createdAt: convertDateTimeFromServer(userAttributesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userAttributesEntity.updatedAt),
          endDate: convertDateTimeFromServer(userAttributesEntity.endDate),
          effDate: convertDateTimeFromServer(userAttributesEntity.effDate),
          attribute: userAttributesEntity?.attribute?.id,
          user: userAttributesEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userAttributes.home.createOrEditLabel" data-cy="UserAttributesCreateUpdateHeading">
            Create or edit a User Attributes
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
                <ValidatedField name="id" required readOnly id="user-attributes-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Created At"
                id="user-attributes-createdAt"
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
                id="user-attributes-updatedAt"
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
                id="user-attributes-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="End Date"
                id="user-attributes-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Eff Date"
                id="user-attributes-effDate"
                name="effDate"
                data-cy="effDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="user-attributes-attribute" name="attribute" data-cy="attribute" label="Attribute" type="select" required>
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
              <ValidatedField id="user-attributes-user" name="user" data-cy="user" label="User" type="select" required>
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-attributes" replace color="info">
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

export default UserAttributesUpdate;
