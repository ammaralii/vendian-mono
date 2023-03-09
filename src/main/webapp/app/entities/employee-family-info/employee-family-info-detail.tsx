import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-family-info.reducer';

export const EmployeeFamilyInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeFamilyInfoEntity = useAppSelector(state => state.employeeFamilyInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeFamilyInfoDetailsHeading">Employee Family Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.id}</dd>
          <dt>
            <span id="fullname">Fullname</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.fullname}</dd>
          <dt>
            <span id="relationship">Relationship</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.relationship}</dd>
          <dt>
            <span id="contactno">Contactno</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.contactno}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.email}</dd>
          <dt>
            <span id="dob">Dob</span>
          </dt>
          <dd>
            {employeeFamilyInfoEntity.dob ? <TextFormat value={employeeFamilyInfoEntity.dob} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="registeredinmedical">Registeredinmedical</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.registeredinmedical ? 'true' : 'false'}</dd>
          <dt>
            <span id="cnic">Cnic</span>
          </dt>
          <dd>
            {employeeFamilyInfoEntity.cnic ? (
              <div>
                {employeeFamilyInfoEntity.cnicContentType ? (
                  <a onClick={openFile(employeeFamilyInfoEntity.cnicContentType, employeeFamilyInfoEntity.cnic)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeFamilyInfoEntity.cnicContentType}, {byteSize(employeeFamilyInfoEntity.cnic)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeFamilyInfoEntity.createdat ? (
              <TextFormat value={employeeFamilyInfoEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeFamilyInfoEntity.updatedat ? (
              <TextFormat value={employeeFamilyInfoEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="medicalpolicyno">Medicalpolicyno</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.medicalpolicyno}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{employeeFamilyInfoEntity.gender}</dd>
          <dt>Employee</dt>
          <dd>{employeeFamilyInfoEntity.employee ? employeeFamilyInfoEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-family-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-family-info/${employeeFamilyInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeFamilyInfoDetail;
