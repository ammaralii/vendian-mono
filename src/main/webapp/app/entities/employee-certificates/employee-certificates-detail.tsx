import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-certificates.reducer';

export const EmployeeCertificatesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeCertificatesEntity = useAppSelector(state => state.employeeCertificates.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeCertificatesDetailsHeading">Employee Certificates</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeCertificatesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{employeeCertificatesEntity.name}</dd>
          <dt>
            <span id="certificateno">Certificateno</span>
          </dt>
          <dd>{employeeCertificatesEntity.certificateno}</dd>
          <dt>
            <span id="issuingbody">Issuingbody</span>
          </dt>
          <dd>{employeeCertificatesEntity.issuingbody}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {employeeCertificatesEntity.date ? (
              <TextFormat value={employeeCertificatesEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeCertificatesEntity.createdat ? (
              <TextFormat value={employeeCertificatesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeCertificatesEntity.updatedat ? (
              <TextFormat value={employeeCertificatesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="validtill">Validtill</span>
          </dt>
          <dd>
            {employeeCertificatesEntity.validtill ? (
              <TextFormat value={employeeCertificatesEntity.validtill} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="certificatecompetency">Certificatecompetency</span>
          </dt>
          <dd>{employeeCertificatesEntity.certificatecompetency}</dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {employeeCertificatesEntity.deletedat ? (
              <TextFormat value={employeeCertificatesEntity.deletedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeCertificatesEntity.employee ? employeeCertificatesEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-certificates" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-certificates/${employeeCertificatesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeCertificatesDetail;
