import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employees.reducer';

export const EmployeesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeesEntity = useAppSelector(state => state.employees.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeesDetailsHeading">Employees</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeesEntity.id}</dd>
          <dt>
            <span id="firstname">Firstname</span>
          </dt>
          <dd>{employeesEntity.firstname}</dd>
          <dt>
            <span id="lastname">Lastname</span>
          </dt>
          <dd>{employeesEntity.lastname}</dd>
          <dt>
            <span id="phonenumber">Phonenumber</span>
          </dt>
          <dd>{employeesEntity.phonenumber}</dd>
          <dt>
            <span id="dateofbirth">Dateofbirth</span>
          </dt>
          <dd>
            {employeesEntity.dateofbirth ? <TextFormat value={employeesEntity.dateofbirth} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{employeesEntity.email}</dd>
          <dt>
            <span id="skype">Skype</span>
          </dt>
          <dd>{employeesEntity.skype}</dd>
          <dt>
            <span id="employeeDesignation">Employee Designation</span>
          </dt>
          <dd>{employeesEntity.employeeDesignation}</dd>
          <dt>
            <span id="joiningdate">Joiningdate</span>
          </dt>
          <dd>
            {employeesEntity.joiningdate ? <TextFormat value={employeesEntity.joiningdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="area">Area</span>
          </dt>
          <dd>{employeesEntity.area}</dd>
          <dt>
            <span id="leavingdate">Leavingdate</span>
          </dt>
          <dd>
            {employeesEntity.leavingdate ? <TextFormat value={employeesEntity.leavingdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>{employeesEntity.notes}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{employeesEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="googleid">Googleid</span>
          </dt>
          <dd>{employeesEntity.googleid}</dd>
          <dt>
            <span id="oracleid">Oracleid</span>
          </dt>
          <dd>{employeesEntity.oracleid}</dd>
          <dt>
            <span id="deptt">Deptt</span>
          </dt>
          <dd>{employeesEntity.deptt}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeesEntity.createdat ? <TextFormat value={employeesEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeesEntity.updatedat ? <TextFormat value={employeesEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="genderid">Genderid</span>
          </dt>
          <dd>{employeesEntity.genderid}</dd>
          <dt>
            <span id="onprobation">Onprobation</span>
          </dt>
          <dd>{employeesEntity.onprobation ? 'true' : 'false'}</dd>
          <dt>
            <span id="employeeCompetency">Employee Competency</span>
          </dt>
          <dd>{employeesEntity.employeeCompetency}</dd>
          <dt>
            <span id="resourcetype">Resourcetype</span>
          </dt>
          <dd>{employeesEntity.resourcetype}</dd>
          <dt>
            <span id="grade">Grade</span>
          </dt>
          <dd>{employeesEntity.grade}</dd>
          <dt>
            <span id="subgrade">Subgrade</span>
          </dt>
          <dd>{employeesEntity.subgrade}</dd>
          <dt>
            <span id="imageurl">Imageurl</span>
          </dt>
          <dd>{employeesEntity.imageurl}</dd>
          <dt>
            <span id="resignationdate">Resignationdate</span>
          </dt>
          <dd>
            {employeesEntity.resignationdate ? (
              <TextFormat value={employeesEntity.resignationdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="graduationdate">Graduationdate</span>
          </dt>
          <dd>
            {employeesEntity.graduationdate ? (
              <TextFormat value={employeesEntity.graduationdate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="careerstartdate">Careerstartdate</span>
          </dt>
          <dd>
            {employeesEntity.careerstartdate ? (
              <TextFormat value={employeesEntity.careerstartdate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="externalexpyears">Externalexpyears</span>
          </dt>
          <dd>{employeesEntity.externalexpyears}</dd>
          <dt>
            <span id="externalexpmonths">Externalexpmonths</span>
          </dt>
          <dd>{employeesEntity.externalexpmonths}</dd>
          <dt>
            <span id="placeofbirth">Placeofbirth</span>
          </dt>
          <dd>{employeesEntity.placeofbirth}</dd>
          <dt>
            <span id="hireddate">Hireddate</span>
          </dt>
          <dd>
            {employeesEntity.hireddate ? <TextFormat value={employeesEntity.hireddate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lasttrackingupdate">Lasttrackingupdate</span>
          </dt>
          <dd>
            {employeesEntity.lasttrackingupdate ? (
              <TextFormat value={employeesEntity.lasttrackingupdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="middlename">Middlename</span>
          </dt>
          <dd>{employeesEntity.middlename}</dd>
          <dt>
            <span id="grosssalary">Grosssalary</span>
          </dt>
          <dd>
            {employeesEntity.grosssalary ? (
              <div>
                {employeesEntity.grosssalaryContentType ? (
                  <a onClick={openFile(employeesEntity.grosssalaryContentType, employeesEntity.grosssalary)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeesEntity.grosssalaryContentType}, {byteSize(employeesEntity.grosssalary)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="fuelallowance">Fuelallowance</span>
          </dt>
          <dd>
            {employeesEntity.fuelallowance ? (
              <div>
                {employeesEntity.fuelallowanceContentType ? (
                  <a onClick={openFile(employeesEntity.fuelallowanceContentType, employeesEntity.fuelallowance)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeesEntity.fuelallowanceContentType}, {byteSize(employeesEntity.fuelallowance)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="mobilenumber">Mobilenumber</span>
          </dt>
          <dd>{employeesEntity.mobilenumber}</dd>
          <dt>
            <span id="resignationtype">Resignationtype</span>
          </dt>
          <dd>{employeesEntity.resignationtype}</dd>
          <dt>
            <span id="primaryreasonforleaving">Primaryreasonforleaving</span>
          </dt>
          <dd>{employeesEntity.primaryreasonforleaving}</dd>
          <dt>
            <span id="secondaryreasonforleaving">Secondaryreasonforleaving</span>
          </dt>
          <dd>{employeesEntity.secondaryreasonforleaving}</dd>
          <dt>
            <span id="noticeperiodduration">Noticeperiodduration</span>
          </dt>
          <dd>{employeesEntity.noticeperiodduration}</dd>
          <dt>
            <span id="noticeperiodserved">Noticeperiodserved</span>
          </dt>
          <dd>{employeesEntity.noticeperiodserved}</dd>
          <dt>
            <span id="probationperiodduration">Probationperiodduration</span>
          </dt>
          <dd>{employeesEntity.probationperiodduration}</dd>
          <dt>Location</dt>
          <dd>{employeesEntity.location ? employeesEntity.location.id : ''}</dd>
          <dt>Role</dt>
          <dd>{employeesEntity.role ? employeesEntity.role.id : ''}</dd>
          <dt>Manager</dt>
          <dd>{employeesEntity.manager ? employeesEntity.manager.id : ''}</dd>
          <dt>Leave</dt>
          <dd>{employeesEntity.leave ? employeesEntity.leave.id : ''}</dd>
          <dt>Department</dt>
          <dd>{employeesEntity.department ? employeesEntity.department.id : ''}</dd>
          <dt>Businessunit</dt>
          <dd>{employeesEntity.businessunit ? employeesEntity.businessunit.id : ''}</dd>
          <dt>Division</dt>
          <dd>{employeesEntity.division ? employeesEntity.division.id : ''}</dd>
          <dt>Competency</dt>
          <dd>{employeesEntity.competency ? employeesEntity.competency.id : ''}</dd>
          <dt>Employmentstatus</dt>
          <dd>{employeesEntity.employmentstatus ? employeesEntity.employmentstatus.name : ''}</dd>
          <dt>Employmenttype</dt>
          <dd>{employeesEntity.employmenttype ? employeesEntity.employmenttype.name : ''}</dd>
          <dt>Designation</dt>
          <dd>{employeesEntity.designation ? employeesEntity.designation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employees" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employees/${employeesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeesDetail;
