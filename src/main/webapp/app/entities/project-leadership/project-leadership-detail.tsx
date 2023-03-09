import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project-leadership.reducer';

export const ProjectLeadershipDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectLeadershipEntity = useAppSelector(state => state.projectLeadership.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectLeadershipDetailsHeading">Project Leadership</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectLeadershipEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {projectLeadershipEntity.createdat ? (
              <TextFormat value={projectLeadershipEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {projectLeadershipEntity.updatedat ? (
              <TextFormat value={projectLeadershipEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Projectrole</dt>
          <dd>{projectLeadershipEntity.projectrole ? projectLeadershipEntity.projectrole.id : ''}</dd>
          <dt>Project</dt>
          <dd>{projectLeadershipEntity.project ? projectLeadershipEntity.project.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{projectLeadershipEntity.employee ? projectLeadershipEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project-leadership" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-leadership/${projectLeadershipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectLeadershipDetail;
