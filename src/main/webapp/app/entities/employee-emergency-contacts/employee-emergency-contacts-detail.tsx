import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-emergency-contacts.reducer';

export const EmployeeEmergencyContactsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEmergencyContactsEntity = useAppSelector(state => state.employeeEmergencyContacts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeEmergencyContactsDetailsHeading">Employee Emergency Contacts</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeEmergencyContactsEntity.id}</dd>
          <dt>
            <span id="fullname">Fullname</span>
          </dt>
          <dd>{employeeEmergencyContactsEntity.fullname}</dd>
          <dt>
            <span id="relationship">Relationship</span>
          </dt>
          <dd>{employeeEmergencyContactsEntity.relationship}</dd>
          <dt>
            <span id="contactno">Contactno</span>
          </dt>
          <dd>{employeeEmergencyContactsEntity.contactno}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeEmergencyContactsEntity.createdat ? (
              <TextFormat value={employeeEmergencyContactsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeEmergencyContactsEntity.updatedat ? (
              <TextFormat value={employeeEmergencyContactsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeEmergencyContactsEntity.employee ? employeeEmergencyContactsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-emergency-contacts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-emergency-contacts/${employeeEmergencyContactsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeEmergencyContactsDetail;
