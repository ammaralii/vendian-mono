import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-contacts.reducer';

export const EmployeeContactsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeContactsEntity = useAppSelector(state => state.employeeContacts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeContactsDetailsHeading">Employee Contacts</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeContactsEntity.id}</dd>
          <dt>
            <span id="number">Number</span>
          </dt>
          <dd>
            {employeeContactsEntity.number ? (
              <div>
                {employeeContactsEntity.numberContentType ? (
                  <a onClick={openFile(employeeContactsEntity.numberContentType, employeeContactsEntity.number)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeContactsEntity.numberContentType}, {byteSize(employeeContactsEntity.number)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{employeeContactsEntity.type}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeContactsEntity.createdat ? (
              <TextFormat value={employeeContactsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeContactsEntity.updatedat ? (
              <TextFormat value={employeeContactsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeContactsEntity.employee ? employeeContactsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-contacts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-contacts/${employeeContactsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeContactsDetail;
