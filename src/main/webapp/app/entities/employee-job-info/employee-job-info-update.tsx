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
import { IDesignations } from 'app/shared/model/designations.model';
import { getEntities as getDesignations } from 'app/entities/designations/designations.reducer';
import { IDepartments } from 'app/shared/model/departments.model';
import { getEntities as getDepartments } from 'app/entities/departments/departments.reducer';
import { IEmploymentTypes } from 'app/shared/model/employment-types.model';
import { getEntities as getEmploymentTypes } from 'app/entities/employment-types/employment-types.reducer';
import { IDesignationJobDescriptions } from 'app/shared/model/designation-job-descriptions.model';
import { getEntities as getDesignationJobDescriptions } from 'app/entities/designation-job-descriptions/designation-job-descriptions.reducer';
import { IDivisions } from 'app/shared/model/divisions.model';
import { getEntities as getDivisions } from 'app/entities/divisions/divisions.reducer';
import { IBusinessUnits } from 'app/shared/model/business-units.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-units/business-units.reducer';
import { ICompetencies } from 'app/shared/model/competencies.model';
import { getEntities as getCompetencies } from 'app/entities/competencies/competencies.reducer';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-job-info.reducer';

export const EmployeeJobInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const designations = useAppSelector(state => state.designations.entities);
  const departments = useAppSelector(state => state.departments.entities);
  const employmentTypes = useAppSelector(state => state.employmentTypes.entities);
  const designationJobDescriptions = useAppSelector(state => state.designationJobDescriptions.entities);
  const divisions = useAppSelector(state => state.divisions.entities);
  const businessUnits = useAppSelector(state => state.businessUnits.entities);
  const competencies = useAppSelector(state => state.competencies.entities);
  const employeeJobInfoEntity = useAppSelector(state => state.employeeJobInfo.entity);
  const loading = useAppSelector(state => state.employeeJobInfo.loading);
  const updating = useAppSelector(state => state.employeeJobInfo.updating);
  const updateSuccess = useAppSelector(state => state.employeeJobInfo.updateSuccess);

  const handleClose = () => {
    navigate('/employee-job-info' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getDesignations({}));
    dispatch(getDepartments({}));
    dispatch(getEmploymentTypes({}));
    dispatch(getDesignationJobDescriptions({}));
    dispatch(getDivisions({}));
    dispatch(getBusinessUnits({}));
    dispatch(getCompetencies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeJobInfoEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      reviewmanager: employees.find(it => it.id.toString() === values.reviewmanager.toString()),
      manager: employees.find(it => it.id.toString() === values.manager.toString()),
      designation: designations.find(it => it.id.toString() === values.designation.toString()),
      department: departments.find(it => it.id.toString() === values.department.toString()),
      employmenttype: employmentTypes.find(it => it.id.toString() === values.employmenttype.toString()),
      jobdescription: designationJobDescriptions.find(it => it.id.toString() === values.jobdescription.toString()),
      division: divisions.find(it => it.id.toString() === values.division.toString()),
      businessunit: businessUnits.find(it => it.id.toString() === values.businessunit.toString()),
      competency: competencies.find(it => it.id.toString() === values.competency.toString()),
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeJobInfoEntity,
          startdate: convertDateTimeFromServer(employeeJobInfoEntity.startdate),
          enddate: convertDateTimeFromServer(employeeJobInfoEntity.enddate),
          createdat: convertDateTimeFromServer(employeeJobInfoEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeJobInfoEntity.updatedat),
          employee: employeeJobInfoEntity?.employee?.id,
          designation: employeeJobInfoEntity?.designation?.id,
          reviewmanager: employeeJobInfoEntity?.reviewmanager?.id,
          manager: employeeJobInfoEntity?.manager?.id,
          department: employeeJobInfoEntity?.department?.id,
          employmenttype: employeeJobInfoEntity?.employmenttype?.id,
          jobdescription: employeeJobInfoEntity?.jobdescription?.id,
          division: employeeJobInfoEntity?.division?.id,
          businessunit: employeeJobInfoEntity?.businessunit?.id,
          competency: employeeJobInfoEntity?.competency?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeJobInfo.home.createOrEditLabel" data-cy="EmployeeJobInfoCreateUpdateHeading">
            Create or edit a Employee Job Info
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
                <ValidatedField name="id" required readOnly id="employee-job-info-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Title"
                id="employee-job-info-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Grade"
                id="employee-job-info-grade"
                name="grade"
                data-cy="grade"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Subgrade"
                id="employee-job-info-subgrade"
                name="subgrade"
                data-cy="subgrade"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Startdate"
                id="employee-job-info-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Enddate"
                id="employee-job-info-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="employee-job-info-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-job-info-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Location" id="employee-job-info-location" name="location" data-cy="location" type="text" />
              <ValidatedBlobField
                label="Grosssalary"
                id="employee-job-info-grosssalary"
                name="grosssalary"
                data-cy="grosssalary"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Fuelallowance"
                id="employee-job-info-fuelallowance"
                name="fuelallowance"
                data-cy="fuelallowance"
                openActionLabel="Open"
              />
              <ValidatedField id="employee-job-info-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
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
              <ValidatedField
                id="employee-job-info-designation"
                name="designation"
                data-cy="designation"
                label="Designation"
                type="select"
                required
              >
                <option value="" key="0" />
                {designations
                  ? designations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="employee-job-info-reviewmanager"
                name="reviewmanager"
                data-cy="reviewmanager"
                label="Reviewmanager"
                type="select"
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
              <ValidatedField id="employee-job-info-manager" name="manager" data-cy="manager" label="Manager" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-job-info-department"
                name="department"
                data-cy="department"
                label="Department"
                type="select"
                required
              >
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="employee-job-info-employmenttype"
                name="employmenttype"
                data-cy="employmenttype"
                label="Employmenttype"
                type="select"
              >
                <option value="" key="0" />
                {employmentTypes
                  ? employmentTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-job-info-jobdescription"
                name="jobdescription"
                data-cy="jobdescription"
                label="Jobdescription"
                type="select"
              >
                <option value="" key="0" />
                {designationJobDescriptions
                  ? designationJobDescriptions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-job-info-division" name="division" data-cy="division" label="Division" type="select">
                <option value="" key="0" />
                {divisions
                  ? divisions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-job-info-businessunit"
                name="businessunit"
                data-cy="businessunit"
                label="Businessunit"
                type="select"
              >
                <option value="" key="0" />
                {businessUnits
                  ? businessUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-job-info-competency" name="competency" data-cy="competency" label="Competency" type="select">
                <option value="" key="0" />
                {competencies
                  ? competencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-job-info" replace color="info">
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

export default EmployeeJobInfoUpdate;
