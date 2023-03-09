import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-education.reducer';

export const EmployeeEducationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEducationEntity = useAppSelector(state => state.employeeEducation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeEducationDetailsHeading">Employee Education</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeEducationEntity.id}</dd>
          <dt>
            <span id="institute">Institute</span>
          </dt>
          <dd>{employeeEducationEntity.institute}</dd>
          <dt>
            <span id="major">Major</span>
          </dt>
          <dd>{employeeEducationEntity.major}</dd>
          <dt>
            <span id="degree">Degree</span>
          </dt>
          <dd>{employeeEducationEntity.degree}</dd>
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{employeeEducationEntity.value}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{employeeEducationEntity.city}</dd>
          <dt>
            <span id="province">Province</span>
          </dt>
          <dd>{employeeEducationEntity.province}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{employeeEducationEntity.country}</dd>
          <dt>
            <span id="datefrom">Datefrom</span>
          </dt>
          <dd>
            {employeeEducationEntity.datefrom ? (
              <TextFormat value={employeeEducationEntity.datefrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateto">Dateto</span>
          </dt>
          <dd>
            {employeeEducationEntity.dateto ? (
              <TextFormat value={employeeEducationEntity.dateto} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeEducationEntity.createdat ? (
              <TextFormat value={employeeEducationEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeEducationEntity.updatedat ? (
              <TextFormat value={employeeEducationEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Qualificationtype</dt>
          <dd>{employeeEducationEntity.qualificationtype ? employeeEducationEntity.qualificationtype.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{employeeEducationEntity.employee ? employeeEducationEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-education" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-education/${employeeEducationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeEducationDetail;
