import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './z-employee-projects.reducer';

export const ZEmployeeProjectsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zEmployeeProjectsEntity = useAppSelector(state => state.zEmployeeProjects.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zEmployeeProjectsDetailsHeading">Z Employee Projects</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.type}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.startdate ? (
              <TextFormat value={zEmployeeProjectsEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.enddate ? (
              <TextFormat value={zEmployeeProjectsEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.name}</dd>
          <dt>
            <span id="allocation">Allocation</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.allocation ? 'true' : 'false'}</dd>
          <dt>
            <span id="billed">Billed</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.billed}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.createdat ? (
              <TextFormat value={zEmployeeProjectsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.updatedat ? (
              <TextFormat value={zEmployeeProjectsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deliverymanagerid">Deliverymanagerid</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.deliverymanagerid}</dd>
          <dt>
            <span id="architectid">Architectid</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.architectid}</dd>
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.notes}</dd>
          <dt>
            <span id="previousenddate">Previousenddate</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.previousenddate ? (
              <TextFormat value={zEmployeeProjectsEntity.previousenddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="data">Data</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.data}</dd>
          <dt>
            <span id="extendedenddate">Extendedenddate</span>
          </dt>
          <dd>
            {zEmployeeProjectsEntity.extendedenddate ? (
              <TextFormat value={zEmployeeProjectsEntity.extendedenddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="probability">Probability</span>
          </dt>
          <dd>{zEmployeeProjectsEntity.probability}</dd>
          <dt>Event</dt>
          <dd>{zEmployeeProjectsEntity.event ? zEmployeeProjectsEntity.event.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{zEmployeeProjectsEntity.employee ? zEmployeeProjectsEntity.employee.id : ''}</dd>
          <dt>Project</dt>
          <dd>{zEmployeeProjectsEntity.project ? zEmployeeProjectsEntity.project.id : ''}</dd>
          <dt>Employeeproject</dt>
          <dd>{zEmployeeProjectsEntity.employeeproject ? zEmployeeProjectsEntity.employeeproject.id : ''}</dd>
          <dt>Assignor</dt>
          <dd>{zEmployeeProjectsEntity.assignor ? zEmployeeProjectsEntity.assignor.id : ''}</dd>
          <dt>Projectmanager</dt>
          <dd>{zEmployeeProjectsEntity.projectmanager ? zEmployeeProjectsEntity.projectmanager.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/z-employee-projects" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/z-employee-projects/${zEmployeeProjectsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZEmployeeProjectsDetail;
