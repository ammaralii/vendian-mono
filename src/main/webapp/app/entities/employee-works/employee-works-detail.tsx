import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-works.reducer';

export const EmployeeWorksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeWorksEntity = useAppSelector(state => state.employeeWorks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeWorksDetailsHeading">Employee Works</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeWorksEntity.id}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {employeeWorksEntity.startdate ? (
              <TextFormat value={employeeWorksEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {employeeWorksEntity.enddate ? <TextFormat value={employeeWorksEntity.enddate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="designation">Designation</span>
          </dt>
          <dd>{employeeWorksEntity.designation}</dd>
          <dt>
            <span id="leavingreason">Leavingreason</span>
          </dt>
          <dd>{employeeWorksEntity.leavingreason}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeWorksEntity.createdat ? (
              <TextFormat value={employeeWorksEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeWorksEntity.updatedat ? (
              <TextFormat value={employeeWorksEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeWorksEntity.employee ? employeeWorksEntity.employee.id : ''}</dd>
          <dt>Company</dt>
          <dd>{employeeWorksEntity.company ? employeeWorksEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-works" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-works/${employeeWorksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeWorksDetail;
