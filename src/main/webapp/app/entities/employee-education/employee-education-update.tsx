import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IQualificationTypes } from 'app/shared/model/qualification-types.model';
import { getEntities as getQualificationTypes } from 'app/entities/qualification-types/qualification-types.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IEmployeeEducation } from 'app/shared/model/employee-education.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-education.reducer';

export const EmployeeEducationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const qualificationTypes = useAppSelector(state => state.qualificationTypes.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const employeeEducationEntity = useAppSelector(state => state.employeeEducation.entity);
  const loading = useAppSelector(state => state.employeeEducation.loading);
  const updating = useAppSelector(state => state.employeeEducation.updating);
  const updateSuccess = useAppSelector(state => state.employeeEducation.updateSuccess);

  const handleClose = () => {
    navigate('/employee-education' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getQualificationTypes({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.datefrom = convertDateTimeToServer(values.datefrom);
    values.dateto = convertDateTimeToServer(values.dateto);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeEducationEntity,
      ...values,
      qualificationtype: qualificationTypes.find(it => it.id.toString() === values.qualificationtype.toString()),
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
          datefrom: displayDefaultDateTime(),
          dateto: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeEducationEntity,
          datefrom: convertDateTimeFromServer(employeeEducationEntity.datefrom),
          dateto: convertDateTimeFromServer(employeeEducationEntity.dateto),
          createdat: convertDateTimeFromServer(employeeEducationEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeEducationEntity.updatedat),
          qualificationtype: employeeEducationEntity?.qualificationtype?.id,
          employee: employeeEducationEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeEducation.home.createOrEditLabel" data-cy="EmployeeEducationCreateUpdateHeading">
            Create or edit a Employee Education
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
                <ValidatedField name="id" required readOnly id="employee-education-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Institute"
                id="employee-education-institute"
                name="institute"
                data-cy="institute"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Major"
                id="employee-education-major"
                name="major"
                data-cy="major"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Degree"
                id="employee-education-degree"
                name="degree"
                data-cy="degree"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Value"
                id="employee-education-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="City"
                id="employee-education-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Province"
                id="employee-education-province"
                name="province"
                data-cy="province"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Country"
                id="employee-education-country"
                name="country"
                data-cy="country"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Datefrom"
                id="employee-education-datefrom"
                name="datefrom"
                data-cy="datefrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Dateto"
                id="employee-education-dateto"
                name="dateto"
                data-cy="dateto"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="employee-education-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-education-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="employee-education-qualificationtype"
                name="qualificationtype"
                data-cy="qualificationtype"
                label="Qualificationtype"
                type="select"
                required
              >
                <option value="" key="0" />
                {qualificationTypes
                  ? qualificationTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="employee-education-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-education" replace color="info">
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

export default EmployeeEducationUpdate;
