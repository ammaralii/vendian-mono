import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-projects.reducer';

export const EmployeeProjectsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeProjectsEntity = useAppSelector(state => state.employeeProjects.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeProjectsDetailsHeading">Employee Projects</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeProjectsEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{employeeProjectsEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{employeeProjectsEntity.type}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {employeeProjectsEntity.startdate ? (
              <TextFormat value={employeeProjectsEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {employeeProjectsEntity.enddate ? (
              <TextFormat value={employeeProjectsEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="allocation">Allocation</span>
          </dt>
          <dd>{employeeProjectsEntity.allocation ? 'true' : 'false'}</dd>
          <dt>
            <span id="billed">Billed</span>
          </dt>
          <dd>{employeeProjectsEntity.billed}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeProjectsEntity.createdat ? (
              <TextFormat value={employeeProjectsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeProjectsEntity.updatedat ? (
              <TextFormat value={employeeProjectsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="roleid">Roleid</span>
          </dt>
          <dd>{employeeProjectsEntity.roleid}</dd>
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>{employeeProjectsEntity.notes}</dd>
          <dt>
            <span id="extendedenddate">Extendedenddate</span>
          </dt>
          <dd>
            {employeeProjectsEntity.extendedenddate ? (
              <TextFormat value={employeeProjectsEntity.extendedenddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="probability">Probability</span>
          </dt>
          <dd>{employeeProjectsEntity.probability}</dd>
          <dt>Employee</dt>
          <dd>{employeeProjectsEntity.employee ? employeeProjectsEntity.employee.id : ''}</dd>
          <dt>Project</dt>
          <dd>{employeeProjectsEntity.project ? employeeProjectsEntity.project.id : ''}</dd>
          <dt>Assignor</dt>
          <dd>{employeeProjectsEntity.assignor ? employeeProjectsEntity.assignor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-projects" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-projects/${employeeProjectsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeProjectsDetail;
