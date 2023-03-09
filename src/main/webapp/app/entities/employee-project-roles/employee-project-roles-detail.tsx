import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-project-roles.reducer';

export const EmployeeProjectRolesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeProjectRolesEntity = useAppSelector(state => state.employeeProjectRoles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeProjectRolesDetailsHeading">Employee Project Roles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeProjectRolesEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{employeeProjectRolesEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeProjectRolesEntity.createdat ? (
              <TextFormat value={employeeProjectRolesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeProjectRolesEntity.updatedat ? (
              <TextFormat value={employeeProjectRolesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employeeproject</dt>
          <dd>{employeeProjectRolesEntity.employeeproject ? employeeProjectRolesEntity.employeeproject.id : ''}</dd>
          <dt>Projectrole</dt>
          <dd>{employeeProjectRolesEntity.projectrole ? employeeProjectRolesEntity.projectrole.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-project-roles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-project-roles/${employeeProjectRolesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeProjectRolesDetail;
