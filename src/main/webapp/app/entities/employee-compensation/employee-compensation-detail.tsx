import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-compensation.reducer';

export const EmployeeCompensationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeCompensationEntity = useAppSelector(state => state.employeeCompensation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeCompensationDetailsHeading">Employee Compensation</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeCompensationEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>
            {employeeCompensationEntity.amount ? (
              <div>
                {employeeCompensationEntity.amountContentType ? (
                  <a onClick={openFile(employeeCompensationEntity.amountContentType, employeeCompensationEntity.amount)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeCompensationEntity.amountContentType}, {byteSize(employeeCompensationEntity.amount)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {employeeCompensationEntity.date ? (
              <TextFormat value={employeeCompensationEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ecReason">Ec Reason</span>
          </dt>
          <dd>
            {employeeCompensationEntity.ecReason ? (
              <div>
                {employeeCompensationEntity.ecReasonContentType ? (
                  <a onClick={openFile(employeeCompensationEntity.ecReasonContentType, employeeCompensationEntity.ecReason)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeCompensationEntity.ecReasonContentType}, {byteSize(employeeCompensationEntity.ecReason)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{employeeCompensationEntity.type}</dd>
          <dt>
            <span id="commitmentuntil">Commitmentuntil</span>
          </dt>
          <dd>
            {employeeCompensationEntity.commitmentuntil ? (
              <TextFormat value={employeeCompensationEntity.commitmentuntil} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{employeeCompensationEntity.comments}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeCompensationEntity.createdat ? (
              <TextFormat value={employeeCompensationEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeCompensationEntity.updatedat ? (
              <TextFormat value={employeeCompensationEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reasoncomments">Reasoncomments</span>
          </dt>
          <dd>{employeeCompensationEntity.reasoncomments}</dd>
          <dt>Employee</dt>
          <dd>{employeeCompensationEntity.employee ? employeeCompensationEntity.employee.id : ''}</dd>
          <dt>Reason</dt>
          <dd>{employeeCompensationEntity.reason ? employeeCompensationEntity.reason.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-compensation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-compensation/${employeeCompensationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeCompensationDetail;
