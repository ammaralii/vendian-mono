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
import { IEmployeeProfileHistoryLogs } from 'app/shared/model/employee-profile-history-logs.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-profile-history-logs.reducer';

export const EmployeeProfileHistoryLogsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const employeeProfileHistoryLogsEntity = useAppSelector(state => state.employeeProfileHistoryLogs.entity);
  const loading = useAppSelector(state => state.employeeProfileHistoryLogs.loading);
  const updating = useAppSelector(state => state.employeeProfileHistoryLogs.updating);
  const updateSuccess = useAppSelector(state => state.employeeProfileHistoryLogs.updateSuccess);

  const handleClose = () => {
    navigate('/employee-profile-history-logs' + location.search);
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
      ...employeeProfileHistoryLogsEntity,
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
          ...employeeProfileHistoryLogsEntity,
          createdat: convertDateTimeFromServer(employeeProfileHistoryLogsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeProfileHistoryLogsEntity.updatedat),
          employee: employeeProfileHistoryLogsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeProfileHistoryLogs.home.createOrEditLabel" data-cy="EmployeeProfileHistoryLogsCreateUpdateHeading">
            Create or edit a Employee Profile History Logs
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employee-profile-history-logs-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Tablename"
                id="employee-profile-history-logs-tablename"
                name="tablename"
                data-cy="tablename"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Rowid"
                id="employee-profile-history-logs-rowid"
                name="rowid"
                data-cy="rowid"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Eventtype"
                id="employee-profile-history-logs-eventtype"
                name="eventtype"
                data-cy="eventtype"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedBlobField
                label="Fields"
                id="employee-profile-history-logs-fields"
                name="fields"
                data-cy="fields"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Updatedbyid"
                id="employee-profile-history-logs-updatedbyid"
                name="updatedbyid"
                data-cy="updatedbyid"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Activityid"
                id="employee-profile-history-logs-activityid"
                name="activityid"
                data-cy="activityid"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employee-profile-history-logs-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-profile-history-logs-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Category"
                id="employee-profile-history-logs-category"
                name="category"
                data-cy="category"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                id="employee-profile-history-logs-employee"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/employee-profile-history-logs"
                replace
                color="info"
              >
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

export default EmployeeProfileHistoryLogsUpdate;
