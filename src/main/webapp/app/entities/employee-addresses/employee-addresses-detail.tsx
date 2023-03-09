import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-addresses.reducer';

export const EmployeeAddressesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeAddressesEntity = useAppSelector(state => state.employeeAddresses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeAddressesDetailsHeading">Employee Addresses</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeAddressesEntity.id}</dd>
          <dt>
            <span id="isdeleted">Isdeleted</span>
          </dt>
          <dd>{employeeAddressesEntity.isdeleted ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeAddressesEntity.createdat ? (
              <TextFormat value={employeeAddressesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeAddressesEntity.updatedat ? (
              <TextFormat value={employeeAddressesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{employeeAddressesEntity.type}</dd>
          <dt>Address</dt>
          <dd>{employeeAddressesEntity.address ? employeeAddressesEntity.address.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{employeeAddressesEntity.employee ? employeeAddressesEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-addresses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-addresses/${employeeAddressesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeAddressesDetail;
