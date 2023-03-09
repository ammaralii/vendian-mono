import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-documents.reducer';

export const EmployeeDocumentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeDocumentsEntity = useAppSelector(state => state.employeeDocuments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDocumentsDetailsHeading">Employee Documents</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeDocumentsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeDocumentsEntity.createdat ? (
              <TextFormat value={employeeDocumentsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeDocumentsEntity.updatedat ? (
              <TextFormat value={employeeDocumentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Document</dt>
          <dd>{employeeDocumentsEntity.document ? employeeDocumentsEntity.document.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{employeeDocumentsEntity.employee ? employeeDocumentsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-documents" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-documents/${employeeDocumentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDocumentsDetail;
