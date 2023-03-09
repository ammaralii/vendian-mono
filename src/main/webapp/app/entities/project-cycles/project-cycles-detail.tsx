import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project-cycles.reducer';

export const ProjectCyclesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectCyclesEntity = useAppSelector(state => state.projectCycles.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectCyclesDetailsHeading">Project Cycles</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectCyclesEntity.id}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{projectCyclesEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {projectCyclesEntity.createdat ? (
              <TextFormat value={projectCyclesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {projectCyclesEntity.updatedat ? (
              <TextFormat value={projectCyclesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="allowedafterduedateat">Allowedafterduedateat</span>
          </dt>
          <dd>
            {projectCyclesEntity.allowedafterduedateat ? (
              <TextFormat value={projectCyclesEntity.allowedafterduedateat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="duedate">Duedate</span>
          </dt>
          <dd>
            {projectCyclesEntity.duedate ? <TextFormat value={projectCyclesEntity.duedate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="auditlogs">Auditlogs</span>
          </dt>
          <dd>{projectCyclesEntity.auditlogs}</dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {projectCyclesEntity.deletedat ? (
              <TextFormat value={projectCyclesEntity.deletedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Project</dt>
          <dd>{projectCyclesEntity.project ? projectCyclesEntity.project.id : ''}</dd>
          <dt>Allowedafterduedateby</dt>
          <dd>{projectCyclesEntity.allowedafterduedateby ? projectCyclesEntity.allowedafterduedateby.id : ''}</dd>
          <dt>Architect</dt>
          <dd>{projectCyclesEntity.architect ? projectCyclesEntity.architect.id : ''}</dd>
          <dt>Projectmanager</dt>
          <dd>{projectCyclesEntity.projectmanager ? projectCyclesEntity.projectmanager.id : ''}</dd>
          <dt>Rating</dt>
          <dd>
            {projectCyclesEntity.ratings
              ? projectCyclesEntity.ratings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {projectCyclesEntity.ratings && i === projectCyclesEntity.ratings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/project-cycles" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-cycles/${projectCyclesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectCyclesDetail;
