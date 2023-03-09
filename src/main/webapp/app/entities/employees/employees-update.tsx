import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocations } from 'app/shared/model/locations.model';
import { getEntities as getLocations } from 'app/entities/locations/locations.reducer';
import { IRoles } from 'app/shared/model/roles.model';
import { getEntities as getRoles } from 'app/entities/roles/roles.reducer';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { ILeavesOlds } from 'app/shared/model/leaves-olds.model';
import { getEntities as getLeavesOlds } from 'app/entities/leaves-olds/leaves-olds.reducer';
import { IDepartments } from 'app/shared/model/departments.model';
import { getEntities as getDepartments } from 'app/entities/departments/departments.reducer';
import { IBusinessUnits } from 'app/shared/model/business-units.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-units/business-units.reducer';
import { IDivisions } from 'app/shared/model/divisions.model';
import { getEntities as getDivisions } from 'app/entities/divisions/divisions.reducer';
import { ICompetencies } from 'app/shared/model/competencies.model';
import { getEntities as getCompetencies } from 'app/entities/competencies/competencies.reducer';
import { IEmploymentStatuses } from 'app/shared/model/employment-statuses.model';
import { getEntities as getEmploymentStatuses } from 'app/entities/employment-statuses/employment-statuses.reducer';
import { IEmploymentTypes } from 'app/shared/model/employment-types.model';
import { getEntities as getEmploymentTypes } from 'app/entities/employment-types/employment-types.reducer';
import { IDesignations } from 'app/shared/model/designations.model';
import { getEntities as getDesignations } from 'app/entities/designations/designations.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntity, updateEntity, createEntity, reset } from './employees.reducer';

export const EmployeesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.locations.entities);
  const roles = useAppSelector(state => state.roles.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const leavesOlds = useAppSelector(state => state.leavesOlds.entities);
  const departments = useAppSelector(state => state.departments.entities);
  const businessUnits = useAppSelector(state => state.businessUnits.entities);
  const divisions = useAppSelector(state => state.divisions.entities);
  const competencies = useAppSelector(state => state.competencies.entities);
  const employmentStatuses = useAppSelector(state => state.employmentStatuses.entities);
  const employmentTypes = useAppSelector(state => state.employmentTypes.entities);
  const designations = useAppSelector(state => state.designations.entities);
  const employeesEntity = useAppSelector(state => state.employees.entity);
  const loading = useAppSelector(state => state.employees.loading);
  const updating = useAppSelector(state => state.employees.updating);
  const updateSuccess = useAppSelector(state => state.employees.updateSuccess);

  const handleClose = () => {
    navigate('/employees' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
    dispatch(getRoles({}));
    dispatch(getEmployees({}));
    dispatch(getLeavesOlds({}));
    dispatch(getDepartments({}));
    dispatch(getBusinessUnits({}));
    dispatch(getDivisions({}));
    dispatch(getCompetencies({}));
    dispatch(getEmploymentStatuses({}));
    dispatch(getEmploymentTypes({}));
    dispatch(getDesignations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateofbirth = convertDateTimeToServer(values.dateofbirth);
    values.joiningdate = convertDateTimeToServer(values.joiningdate);
    values.leavingdate = convertDateTimeToServer(values.leavingdate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.resignationdate = convertDateTimeToServer(values.resignationdate);
    values.hireddate = convertDateTimeToServer(values.hireddate);
    values.lasttrackingupdate = convertDateTimeToServer(values.lasttrackingupdate);

    const entity = {
      ...employeesEntity,
      ...values,
      location: locations.find(it => it.id.toString() === values.location.toString()),
      role: roles.find(it => it.id.toString() === values.role.toString()),
      manager: employees.find(it => it.id.toString() === values.manager.toString()),
      leave: leavesOlds.find(it => it.id.toString() === values.leave.toString()),
      department: departments.find(it => it.id.toString() === values.department.toString()),
      businessunit: businessUnits.find(it => it.id.toString() === values.businessunit.toString()),
      division: divisions.find(it => it.id.toString() === values.division.toString()),
      competency: competencies.find(it => it.id.toString() === values.competency.toString()),
      employmentstatus: employmentStatuses.find(it => it.id.toString() === values.employmentstatus.toString()),
      employmenttype: employmentTypes.find(it => it.id.toString() === values.employmenttype.toString()),
      designation: designations.find(it => it.id.toString() === values.designation.toString()),
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
          dateofbirth: displayDefaultDateTime(),
          joiningdate: displayDefaultDateTime(),
          leavingdate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          resignationdate: displayDefaultDateTime(),
          hireddate: displayDefaultDateTime(),
          lasttrackingupdate: displayDefaultDateTime(),
        }
      : {
          ...employeesEntity,
          dateofbirth: convertDateTimeFromServer(employeesEntity.dateofbirth),
          joiningdate: convertDateTimeFromServer(employeesEntity.joiningdate),
          leavingdate: convertDateTimeFromServer(employeesEntity.leavingdate),
          createdat: convertDateTimeFromServer(employeesEntity.createdat),
          updatedat: convertDateTimeFromServer(employeesEntity.updatedat),
          resignationdate: convertDateTimeFromServer(employeesEntity.resignationdate),
          hireddate: convertDateTimeFromServer(employeesEntity.hireddate),
          lasttrackingupdate: convertDateTimeFromServer(employeesEntity.lasttrackingupdate),
          location: employeesEntity?.location?.id,
          role: employeesEntity?.role?.id,
          manager: employeesEntity?.manager?.id,
          leave: employeesEntity?.leave?.id,
          department: employeesEntity?.department?.id,
          businessunit: employeesEntity?.businessunit?.id,
          division: employeesEntity?.division?.id,
          competency: employeesEntity?.competency?.id,
          employmentstatus: employeesEntity?.employmentstatus?.id,
          employmenttype: employeesEntity?.employmenttype?.id,
          designation: employeesEntity?.designation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employees.home.createOrEditLabel" data-cy="EmployeesCreateUpdateHeading">
            Create or edit a Employees
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="employees-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Firstname"
                id="employees-firstname"
                name="firstname"
                data-cy="firstname"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Lastname"
                id="employees-lastname"
                name="lastname"
                data-cy="lastname"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Phonenumber"
                id="employees-phonenumber"
                name="phonenumber"
                data-cy="phonenumber"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Dateofbirth"
                id="employees-dateofbirth"
                name="dateofbirth"
                data-cy="dateofbirth"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Email"
                id="employees-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Skype"
                id="employees-skype"
                name="skype"
                data-cy="skype"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Employee Designation"
                id="employees-employeeDesignation"
                name="employeeDesignation"
                data-cy="employeeDesignation"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Joiningdate"
                id="employees-joiningdate"
                name="joiningdate"
                data-cy="joiningdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Area"
                id="employees-area"
                name="area"
                data-cy="area"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Leavingdate"
                id="employees-leavingdate"
                name="leavingdate"
                data-cy="leavingdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Notes"
                id="employees-notes"
                name="notes"
                data-cy="notes"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField label="Isactive" id="employees-isactive" name="isactive" data-cy="isactive" check type="checkbox" />
              <ValidatedField
                label="Googleid"
                id="employees-googleid"
                name="googleid"
                data-cy="googleid"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Oracleid"
                id="employees-oracleid"
                name="oracleid"
                data-cy="oracleid"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Deptt"
                id="employees-deptt"
                name="deptt"
                data-cy="deptt"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employees-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updatedat"
                id="employees-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Genderid"
                id="employees-genderid"
                name="genderid"
                data-cy="genderid"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 6, message: 'This field cannot be longer than 6 characters.' },
                }}
              />
              <ValidatedField
                label="Onprobation"
                id="employees-onprobation"
                name="onprobation"
                data-cy="onprobation"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Employee Competency"
                id="employees-employeeCompetency"
                name="employeeCompetency"
                data-cy="employeeCompetency"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Resourcetype"
                id="employees-resourcetype"
                name="resourcetype"
                data-cy="resourcetype"
                type="text"
                validate={{
                  maxLength: { value: 12, message: 'This field cannot be longer than 12 characters.' },
                }}
              />
              <ValidatedField
                label="Grade"
                id="employees-grade"
                name="grade"
                data-cy="grade"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Subgrade"
                id="employees-subgrade"
                name="subgrade"
                data-cy="subgrade"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Imageurl"
                id="employees-imageurl"
                name="imageurl"
                data-cy="imageurl"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Resignationdate"
                id="employees-resignationdate"
                name="resignationdate"
                data-cy="resignationdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Graduationdate"
                id="employees-graduationdate"
                name="graduationdate"
                data-cy="graduationdate"
                type="date"
              />
              <ValidatedField
                label="Careerstartdate"
                id="employees-careerstartdate"
                name="careerstartdate"
                data-cy="careerstartdate"
                type="date"
              />
              <ValidatedField
                label="Externalexpyears"
                id="employees-externalexpyears"
                name="externalexpyears"
                data-cy="externalexpyears"
                type="text"
              />
              <ValidatedField
                label="Externalexpmonths"
                id="employees-externalexpmonths"
                name="externalexpmonths"
                data-cy="externalexpmonths"
                type="text"
              />
              <ValidatedField
                label="Placeofbirth"
                id="employees-placeofbirth"
                name="placeofbirth"
                data-cy="placeofbirth"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Hireddate"
                id="employees-hireddate"
                name="hireddate"
                data-cy="hireddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Lasttrackingupdate"
                id="employees-lasttrackingupdate"
                name="lasttrackingupdate"
                data-cy="lasttrackingupdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Middlename"
                id="employees-middlename"
                name="middlename"
                data-cy="middlename"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedBlobField
                label="Grosssalary"
                id="employees-grosssalary"
                name="grosssalary"
                data-cy="grosssalary"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Fuelallowance"
                id="employees-fuelallowance"
                name="fuelallowance"
                data-cy="fuelallowance"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Mobilenumber"
                id="employees-mobilenumber"
                name="mobilenumber"
                data-cy="mobilenumber"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Resignationtype"
                id="employees-resignationtype"
                name="resignationtype"
                data-cy="resignationtype"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Primaryreasonforleaving"
                id="employees-primaryreasonforleaving"
                name="primaryreasonforleaving"
                data-cy="primaryreasonforleaving"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Secondaryreasonforleaving"
                id="employees-secondaryreasonforleaving"
                name="secondaryreasonforleaving"
                data-cy="secondaryreasonforleaving"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Noticeperiodduration"
                id="employees-noticeperiodduration"
                name="noticeperiodduration"
                data-cy="noticeperiodduration"
                type="text"
              />
              <ValidatedField
                label="Noticeperiodserved"
                id="employees-noticeperiodserved"
                name="noticeperiodserved"
                data-cy="noticeperiodserved"
                type="text"
              />
              <ValidatedField
                label="Probationperiodduration"
                id="employees-probationperiodduration"
                name="probationperiodduration"
                data-cy="probationperiodduration"
                type="text"
              />
              <ValidatedField id="employees-location" name="location" data-cy="location" label="Location" type="select">
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-role" name="role" data-cy="role" label="Role" type="select">
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-manager" name="manager" data-cy="manager" label="Manager" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-leave" name="leave" data-cy="leave" label="Leave" type="select">
                <option value="" key="0" />
                {leavesOlds
                  ? leavesOlds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-department" name="department" data-cy="department" label="Department" type="select">
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-businessunit" name="businessunit" data-cy="businessunit" label="Businessunit" type="select">
                <option value="" key="0" />
                {businessUnits
                  ? businessUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-division" name="division" data-cy="division" label="Division" type="select">
                <option value="" key="0" />
                {divisions
                  ? divisions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employees-competency" name="competency" data-cy="competency" label="Competency" type="select">
                <option value="" key="0" />
                {competencies
                  ? competencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employees-employmentstatus"
                name="employmentstatus"
                data-cy="employmentstatus"
                label="Employmentstatus"
                type="select"
              >
                <option value="" key="0" />
                {employmentStatuses
                  ? employmentStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employees-employmenttype"
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
              <ValidatedField id="employees-designation" name="designation" data-cy="designation" label="Designation" type="select">
                <option value="" key="0" />
                {designations
                  ? designations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employees" replace color="info">
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

export default EmployeesUpdate;
