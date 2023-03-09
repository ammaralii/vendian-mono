import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-birthdays.reducer';

export const EmployeeBirthdaysDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeBirthdaysEntity = useAppSelector(state => state.employeeBirthdays.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeBirthdaysDetailsHeading">Employee Birthdays</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeBirthdaysEntity.id}</dd>
          <dt>
            <span id="birthdayDetails">Birthday Details</span>
          </dt>
          <dd>{employeeBirthdaysEntity.birthdayDetails}</dd>
          <dt>
            <span id="year">Year</span>
          </dt>
          <dd>{employeeBirthdaysEntity.year}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeBirthdaysEntity.createdat ? (
              <TextFormat value={employeeBirthdaysEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeBirthdaysEntity.updatedat ? (
              <TextFormat value={employeeBirthdaysEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeBirthdaysEntity.employee ? employeeBirthdaysEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-birthdays" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-birthdays/${employeeBirthdaysEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeBirthdaysDetail;
