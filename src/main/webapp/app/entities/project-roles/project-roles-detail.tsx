import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project-roles.reducer';

export const ProjectRolesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectRolesEntity = useAppSelector(state => state.projectRoles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectRolesDetailsHeading">Project Roles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectRolesEntity.id}</dd>
          <dt>
            <span id="role">Role</span>
          </dt>
          <dd>{projectRolesEntity.role}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {projectRolesEntity.createdat ? <TextFormat value={projectRolesEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {projectRolesEntity.updatedat ? <TextFormat value={projectRolesEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isleadership">Isleadership</span>
          </dt>
          <dd>{projectRolesEntity.isleadership ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/project-roles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-roles/${projectRolesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectRolesDetail;
