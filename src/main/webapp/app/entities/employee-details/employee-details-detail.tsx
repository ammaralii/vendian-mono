import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-details.reducer';

export const EmployeeDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeDetailsEntity = useAppSelector(state => state.employeeDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsDetailsHeading">Employee Details</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeDetailsEntity.id}</dd>
          <dt>
            <span id="religion">Religion</span>
          </dt>
          <dd>{employeeDetailsEntity.religion}</dd>
          <dt>
            <span id="maritalstatus">Maritalstatus</span>
          </dt>
          <dd>{employeeDetailsEntity.maritalstatus}</dd>
          <dt>
            <span id="cnic">Cnic</span>
          </dt>
          <dd>
            {employeeDetailsEntity.cnic ? (
              <div>
                {employeeDetailsEntity.cnicContentType ? (
                  <a onClick={openFile(employeeDetailsEntity.cnicContentType, employeeDetailsEntity.cnic)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeDetailsEntity.cnicContentType}, {byteSize(employeeDetailsEntity.cnic)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="cnicexpiry">Cnicexpiry</span>
          </dt>
          <dd>
            {employeeDetailsEntity.cnicexpiry ? (
              <div>
                {employeeDetailsEntity.cnicexpiryContentType ? (
                  <a onClick={openFile(employeeDetailsEntity.cnicexpiryContentType, employeeDetailsEntity.cnicexpiry)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeDetailsEntity.cnicexpiryContentType}, {byteSize(employeeDetailsEntity.cnicexpiry)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="bloodgroup">Bloodgroup</span>
          </dt>
          <dd>{employeeDetailsEntity.bloodgroup}</dd>
          <dt>
            <span id="taxreturnfiler">Taxreturnfiler</span>
          </dt>
          <dd>
            {employeeDetailsEntity.taxreturnfiler ? (
              <div>
                {employeeDetailsEntity.taxreturnfilerContentType ? (
                  <a onClick={openFile(employeeDetailsEntity.taxreturnfilerContentType, employeeDetailsEntity.taxreturnfiler)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeDetailsEntity.taxreturnfilerContentType}, {byteSize(employeeDetailsEntity.taxreturnfiler)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="passportno">Passportno</span>
          </dt>
          <dd>
            {employeeDetailsEntity.passportno ? (
              <div>
                {employeeDetailsEntity.passportnoContentType ? (
                  <a onClick={openFile(employeeDetailsEntity.passportnoContentType, employeeDetailsEntity.passportno)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeDetailsEntity.passportnoContentType}, {byteSize(employeeDetailsEntity.passportno)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="passportexpiry">Passportexpiry</span>
          </dt>
          <dd>
            {employeeDetailsEntity.passportexpiry ? (
              <div>
                {employeeDetailsEntity.passportexpiryContentType ? (
                  <a onClick={openFile(employeeDetailsEntity.passportexpiryContentType, employeeDetailsEntity.passportexpiry)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeDetailsEntity.passportexpiryContentType}, {byteSize(employeeDetailsEntity.passportexpiry)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeDetailsEntity.createdat ? (
              <TextFormat value={employeeDetailsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeDetailsEntity.updatedat ? (
              <TextFormat value={employeeDetailsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="totaltenure">Totaltenure</span>
          </dt>
          <dd>{employeeDetailsEntity.totaltenure}</dd>
          <dt>Employee</dt>
          <dd>{employeeDetailsEntity.employee ? employeeDetailsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-details/${employeeDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetailsDetail;
