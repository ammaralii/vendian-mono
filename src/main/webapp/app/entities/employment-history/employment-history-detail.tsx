import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employment-history.reducer';

export const EmploymentHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employmentHistoryEntity = useAppSelector(state => state.employmentHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employmentHistoryDetailsHeading">Employment History</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employmentHistoryEntity.id}</dd>
          <dt>
            <span id="positiontitle">Positiontitle</span>
          </dt>
          <dd>{employmentHistoryEntity.positiontitle}</dd>
          <dt>
            <span id="companyname">Companyname</span>
          </dt>
          <dd>{employmentHistoryEntity.companyname}</dd>
          <dt>
            <span id="grade">Grade</span>
          </dt>
          <dd>{employmentHistoryEntity.grade}</dd>
          <dt>
            <span id="jobdescription">Jobdescription</span>
          </dt>
          <dd>{employmentHistoryEntity.jobdescription}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{employmentHistoryEntity.city}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{employmentHistoryEntity.country}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {employmentHistoryEntity.startdate ? (
              <TextFormat value={employmentHistoryEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {employmentHistoryEntity.enddate ? (
              <TextFormat value={employmentHistoryEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employmentHistoryEntity.createdat ? (
              <TextFormat value={employmentHistoryEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employmentHistoryEntity.updatedat ? (
              <TextFormat value={employmentHistoryEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reasonforleaving">Reasonforleaving</span>
          </dt>
          <dd>{employmentHistoryEntity.reasonforleaving}</dd>
          <dt>
            <span id="grosssalary">Grosssalary</span>
          </dt>
          <dd>
            {employmentHistoryEntity.grosssalary ? (
              <div>
                {employmentHistoryEntity.grosssalaryContentType ? (
                  <a onClick={openFile(employmentHistoryEntity.grosssalaryContentType, employmentHistoryEntity.grosssalary)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employmentHistoryEntity.grosssalaryContentType}, {byteSize(employmentHistoryEntity.grosssalary)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employmentHistoryEntity.employee ? employmentHistoryEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employment-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employment-history/${employmentHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmploymentHistoryDetail;
