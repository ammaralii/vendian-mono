import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IEmployeeFamilyInfo } from 'app/shared/model/employee-family-info.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-family-info.reducer';

export const EmployeeFamilyInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const employeeFamilyInfoEntity = useAppSelector(state => state.employeeFamilyInfo.entity);
  const loading = useAppSelector(state => state.employeeFamilyInfo.loading);
  const updating = useAppSelector(state => state.employeeFamilyInfo.updating);
  const updateSuccess = useAppSelector(state => state.employeeFamilyInfo.updateSuccess);

  const handleClose = () => {
    navigate('/employee-family-info' + location.search);
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
    values.dob = convertDateTimeToServer(values.dob);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeFamilyInfoEntity,
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
          dob: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeFamilyInfoEntity,
          dob: convertDateTimeFromServer(employeeFamilyInfoEntity.dob),
          createdat: convertDateTimeFromServer(employeeFamilyInfoEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeFamilyInfoEntity.updatedat),
          employee: employeeFamilyInfoEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeFamilyInfo.home.createOrEditLabel" data-cy="EmployeeFamilyInfoCreateUpdateHeading">
            Create or edit a Employee Family Info
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
                <ValidatedField name="id" required readOnly id="employee-family-info-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Fullname"
                id="employee-family-info-fullname"
                name="fullname"
                data-cy="fullname"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Relationship"
                id="employee-family-info-relationship"
                name="relationship"
                data-cy="relationship"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Contactno"
                id="employee-family-info-contactno"
                name="contactno"
                data-cy="contactno"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Email"
                id="employee-family-info-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Dob"
                id="employee-family-info-dob"
                name="dob"
                data-cy="dob"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Registeredinmedical"
                id="employee-family-info-registeredinmedical"
                name="registeredinmedical"
                data-cy="registeredinmedical"
                check
                type="checkbox"
              />
              <ValidatedBlobField label="Cnic" id="employee-family-info-cnic" name="cnic" data-cy="cnic" openActionLabel="Open" />
              <ValidatedField
                label="Createdat"
                id="employee-family-info-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-family-info-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Medicalpolicyno"
                id="employee-family-info-medicalpolicyno"
                name="medicalpolicyno"
                data-cy="medicalpolicyno"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Gender"
                id="employee-family-info-gender"
                name="gender"
                data-cy="gender"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 6, message: 'This field cannot be longer than 6 characters.' },
                }}
              />
              <ValidatedField id="employee-family-info-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-family-info" replace color="info">
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

export default EmployeeFamilyInfoUpdate;
