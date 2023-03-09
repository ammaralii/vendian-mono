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
import { IDealResources } from 'app/shared/model/deal-resources.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-resources.reducer';

export const DealResourcesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const dealResourcesEntity = useAppSelector(state => state.dealResources.entity);
  const loading = useAppSelector(state => state.dealResources.loading);
  const updating = useAppSelector(state => state.dealResources.updating);
  const updateSuccess = useAppSelector(state => state.dealResources.updateSuccess);

  const handleClose = () => {
    navigate('/deal-resources' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.joiningdate = convertDateTimeToServer(values.joiningdate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...dealResourcesEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
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
          joiningdate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...dealResourcesEntity,
          joiningdate: convertDateTimeFromServer(dealResourcesEntity.joiningdate),
          createdat: convertDateTimeFromServer(dealResourcesEntity.createdat),
          updatedat: convertDateTimeFromServer(dealResourcesEntity.updatedat),
          employee: dealResourcesEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.dealResources.home.createOrEditLabel" data-cy="DealResourcesCreateUpdateHeading">
            Create or edit a Deal Resources
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
                <ValidatedField name="id" required readOnly id="deal-resources-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Firstname"
                id="deal-resources-firstname"
                name="firstname"
                data-cy="firstname"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Lastname"
                id="deal-resources-lastname"
                name="lastname"
                data-cy="lastname"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Joiningdate"
                id="deal-resources-joiningdate"
                name="joiningdate"
                data-cy="joiningdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Externalexpyears"
                id="deal-resources-externalexpyears"
                name="externalexpyears"
                data-cy="externalexpyears"
                type="text"
              />
              <ValidatedField
                label="Externalexpmonths"
                id="deal-resources-externalexpmonths"
                name="externalexpmonths"
                data-cy="externalexpmonths"
                type="text"
              />
              <ValidatedField
                label="Createdat"
                id="deal-resources-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deal-resources-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Type"
                id="deal-resources-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 14, message: 'This field cannot be longer than 14 characters.' },
                }}
              />
              <ValidatedField label="Isactive" id="deal-resources-isactive" name="isactive" data-cy="isactive" check type="checkbox" />
              <ValidatedField id="deal-resources-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deal-resources" replace color="info">
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

export default DealResourcesUpdate;
