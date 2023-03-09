import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { IUserRelations } from 'app/shared/model/user-relations.model';
import { getEntity, updateEntity, createEntity, reset } from './user-relations.reducer';

export const UserRelationsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const attributes = useAppSelector(state => state.attributes.entities);
  const userRelationsEntity = useAppSelector(state => state.userRelations.entity);
  const loading = useAppSelector(state => state.userRelations.loading);
  const updating = useAppSelector(state => state.userRelations.updating);
  const updateSuccess = useAppSelector(state => state.userRelations.updateSuccess);

  const handleClose = () => {
    navigate('/user-relations' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getAttributes({}));
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
      ...userRelationsEntity,
      ...values,
      user: employees.find(it => it.id.toString() === values.user.toString()),
      relatedUser: employees.find(it => it.id.toString() === values.relatedUser.toString()),
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
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
          ...userRelationsEntity,
          effDate: convertDateTimeFromServer(userRelationsEntity.effDate),
          createdAt: convertDateTimeFromServer(userRelationsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userRelationsEntity.updatedAt),
          endDate: convertDateTimeFromServer(userRelationsEntity.endDate),
          user: userRelationsEntity?.user?.id,
          attribute: userRelationsEntity?.attribute?.id,
          relatedUser: userRelationsEntity?.relatedUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userRelations.home.createOrEditLabel" data-cy="UserRelationsCreateUpdateHeading">
            Create or edit a User Relations
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
                <ValidatedField name="id" required readOnly id="user-relations-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Reference Type"
                id="user-relations-referenceType"
                name="referenceType"
                data-cy="referenceType"
                type="text"
                validate={{
                  maxLength: { value: 7, message: 'This field cannot be longer than 7 characters.' },
                }}
              />
              <ValidatedField label="Reference Id" id="user-relations-referenceId" name="referenceId" data-cy="referenceId" type="text" />
              <ValidatedField
                label="Eff Date"
                id="user-relations-effDate"
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
                id="user-relations-createdAt"
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
                id="user-relations-updatedAt"
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
                id="user-relations-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-relations-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="user-relations-user" name="user" data-cy="user" label="User" type="select" required>
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
              <ValidatedField id="user-relations-attribute" name="attribute" data-cy="attribute" label="Attribute" type="select" required>
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
              <ValidatedField id="user-relations-relatedUser" name="relatedUser" data-cy="relatedUser" label="Related User" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-relations" replace color="info">
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

export default UserRelationsUpdate;
