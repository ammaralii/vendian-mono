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
import { IEmployeeDetails } from 'app/shared/model/employee-details.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-details.reducer';

export const EmployeeDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const employeeDetailsEntity = useAppSelector(state => state.employeeDetails.entity);
  const loading = useAppSelector(state => state.employeeDetails.loading);
  const updating = useAppSelector(state => state.employeeDetails.updating);
  const updateSuccess = useAppSelector(state => state.employeeDetails.updateSuccess);

  const handleClose = () => {
    navigate('/employee-details' + location.search);
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
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeDetailsEntity,
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeDetailsEntity,
          createdat: convertDateTimeFromServer(employeeDetailsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeDetailsEntity.updatedat),
          employee: employeeDetailsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeDetails.home.createOrEditLabel" data-cy="EmployeeDetailsCreateUpdateHeading">
            Create or edit a Employee Details
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
                <ValidatedField name="id" required readOnly id="employee-details-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Religion"
                id="employee-details-religion"
                name="religion"
                data-cy="religion"
                type="text"
                validate={{
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Maritalstatus"
                id="employee-details-maritalstatus"
                name="maritalstatus"
                data-cy="maritalstatus"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedBlobField label="Cnic" id="employee-details-cnic" name="cnic" data-cy="cnic" openActionLabel="Open" />
              <ValidatedBlobField
                label="Cnicexpiry"
                id="employee-details-cnicexpiry"
                name="cnicexpiry"
                data-cy="cnicexpiry"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Bloodgroup"
                id="employee-details-bloodgroup"
                name="bloodgroup"
                data-cy="bloodgroup"
                type="text"
                validate={{
                  maxLength: { value: 3, message: 'This field cannot be longer than 3 characters.' },
                }}
              />
              <ValidatedBlobField
                label="Taxreturnfiler"
                id="employee-details-taxreturnfiler"
                name="taxreturnfiler"
                data-cy="taxreturnfiler"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Passportno"
                id="employee-details-passportno"
                name="passportno"
                data-cy="passportno"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Passportexpiry"
                id="employee-details-passportexpiry"
                name="passportexpiry"
                data-cy="passportexpiry"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Createdat"
                id="employee-details-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-details-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Totaltenure"
                id="employee-details-totaltenure"
                name="totaltenure"
                data-cy="totaltenure"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField id="employee-details-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-details" replace color="info">
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

export default EmployeeDetailsUpdate;
