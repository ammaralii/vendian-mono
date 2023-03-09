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
import { IReasons } from 'app/shared/model/reasons.model';
import { getEntities as getReasons } from 'app/entities/reasons/reasons.reducer';
import { IEmployeeCompensation } from 'app/shared/model/employee-compensation.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-compensation.reducer';

export const EmployeeCompensationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const reasons = useAppSelector(state => state.reasons.entities);
  const employeeCompensationEntity = useAppSelector(state => state.employeeCompensation.entity);
  const loading = useAppSelector(state => state.employeeCompensation.loading);
  const updating = useAppSelector(state => state.employeeCompensation.updating);
  const updateSuccess = useAppSelector(state => state.employeeCompensation.updateSuccess);

  const handleClose = () => {
    navigate('/employee-compensation' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getReasons({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);
    values.commitmentuntil = convertDateTimeToServer(values.commitmentuntil);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeCompensationEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      reason: reasons.find(it => it.id.toString() === values.reason.toString()),
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
          date: displayDefaultDateTime(),
          commitmentuntil: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeCompensationEntity,
          date: convertDateTimeFromServer(employeeCompensationEntity.date),
          commitmentuntil: convertDateTimeFromServer(employeeCompensationEntity.commitmentuntil),
          createdat: convertDateTimeFromServer(employeeCompensationEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeCompensationEntity.updatedat),
          employee: employeeCompensationEntity?.employee?.id,
          reason: employeeCompensationEntity?.reason?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeCompensation.home.createOrEditLabel" data-cy="EmployeeCompensationCreateUpdateHeading">
            Create or edit a Employee Compensation
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
                <ValidatedField name="id" required readOnly id="employee-compensation-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField
                label="Amount"
                id="employee-compensation-amount"
                name="amount"
                data-cy="amount"
                openActionLabel="Open"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Date"
                id="employee-compensation-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedBlobField
                label="Ec Reason"
                id="employee-compensation-ecReason"
                name="ecReason"
                data-cy="ecReason"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Type"
                id="employee-compensation-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 5, message: 'This field cannot be longer than 5 characters.' },
                }}
              />
              <ValidatedField
                label="Commitmentuntil"
                id="employee-compensation-commitmentuntil"
                name="commitmentuntil"
                data-cy="commitmentuntil"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Comments"
                id="employee-compensation-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employee-compensation-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-compensation-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Reasoncomments"
                id="employee-compensation-reasoncomments"
                name="reasoncomments"
                data-cy="reasoncomments"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                id="employee-compensation-employee"
                name="employee"
                data-cy="employee"
                label="Employee"
                type="select"
                required
              >
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
              <ValidatedField id="employee-compensation-reason" name="reason" data-cy="reason" label="Reason" type="select">
                <option value="" key="0" />
                {reasons
                  ? reasons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-compensation" replace color="info">
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

export default EmployeeCompensationUpdate;
