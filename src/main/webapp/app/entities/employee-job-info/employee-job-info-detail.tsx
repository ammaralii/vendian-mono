import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-job-info.reducer';

export const EmployeeJobInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeJobInfoEntity = useAppSelector(state => state.employeeJobInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeJobInfoDetailsHeading">Employee Job Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeJobInfoEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{employeeJobInfoEntity.title}</dd>
          <dt>
            <span id="grade">Grade</span>
          </dt>
          <dd>{employeeJobInfoEntity.grade}</dd>
          <dt>
            <span id="subgrade">Subgrade</span>
          </dt>
          <dd>{employeeJobInfoEntity.subgrade}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.startdate ? (
              <TextFormat value={employeeJobInfoEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.enddate ? (
              <TextFormat value={employeeJobInfoEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.createdat ? (
              <TextFormat value={employeeJobInfoEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.updatedat ? (
              <TextFormat value={employeeJobInfoEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{employeeJobInfoEntity.location}</dd>
          <dt>
            <span id="grosssalary">Grosssalary</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.grosssalary ? (
              <div>
                {employeeJobInfoEntity.grosssalaryContentType ? (
                  <a onClick={openFile(employeeJobInfoEntity.grosssalaryContentType, employeeJobInfoEntity.grosssalary)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeJobInfoEntity.grosssalaryContentType}, {byteSize(employeeJobInfoEntity.grosssalary)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="fuelallowance">Fuelallowance</span>
          </dt>
          <dd>
            {employeeJobInfoEntity.fuelallowance ? (
              <div>
                {employeeJobInfoEntity.fuelallowanceContentType ? (
                  <a onClick={openFile(employeeJobInfoEntity.fuelallowanceContentType, employeeJobInfoEntity.fuelallowance)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeJobInfoEntity.fuelallowanceContentType}, {byteSize(employeeJobInfoEntity.fuelallowance)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeJobInfoEntity.employee ? employeeJobInfoEntity.employee.id : ''}</dd>
          <dt>Designation</dt>
          <dd>{employeeJobInfoEntity.designation ? employeeJobInfoEntity.designation.id : ''}</dd>
          <dt>Reviewmanager</dt>
          <dd>{employeeJobInfoEntity.reviewmanager ? employeeJobInfoEntity.reviewmanager.id : ''}</dd>
          <dt>Manager</dt>
          <dd>{employeeJobInfoEntity.manager ? employeeJobInfoEntity.manager.id : ''}</dd>
          <dt>Department</dt>
          <dd>{employeeJobInfoEntity.department ? employeeJobInfoEntity.department.id : ''}</dd>
          <dt>Employmenttype</dt>
          <dd>{employeeJobInfoEntity.employmenttype ? employeeJobInfoEntity.employmenttype.name : ''}</dd>
          <dt>Jobdescription</dt>
          <dd>{employeeJobInfoEntity.jobdescription ? employeeJobInfoEntity.jobdescription.id : ''}</dd>
          <dt>Division</dt>
          <dd>{employeeJobInfoEntity.division ? employeeJobInfoEntity.division.id : ''}</dd>
          <dt>Businessunit</dt>
          <dd>{employeeJobInfoEntity.businessunit ? employeeJobInfoEntity.businessunit.id : ''}</dd>
          <dt>Competency</dt>
          <dd>{employeeJobInfoEntity.competency ? employeeJobInfoEntity.competency.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-job-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-job-info/${employeeJobInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeJobInfoDetail;
