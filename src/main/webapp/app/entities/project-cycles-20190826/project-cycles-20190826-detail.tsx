import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project-cycles-20190826.reducer';

export const ProjectCycles20190826Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectCycles20190826Entity = useAppSelector(state => state.projectCycles20190826.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectCycles20190826DetailsHeading">Project Cycles 20190826</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectCycles20190826Entity.id}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{projectCycles20190826Entity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {projectCycles20190826Entity.createdat ? (
              <TextFormat value={projectCycles20190826Entity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {projectCycles20190826Entity.updatedat ? (
              <TextFormat value={projectCycles20190826Entity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="performancecycleid">Performancecycleid</span>
          </dt>
          <dd>{projectCycles20190826Entity.performancecycleid}</dd>
          <dt>
            <span id="projectid">Projectid</span>
          </dt>
          <dd>{projectCycles20190826Entity.projectid}</dd>
          <dt>
            <span id="allowedafterduedateby">Allowedafterduedateby</span>
          </dt>
          <dd>{projectCycles20190826Entity.allowedafterduedateby}</dd>
          <dt>
            <span id="allowedafterduedateat">Allowedafterduedateat</span>
          </dt>
          <dd>
            {projectCycles20190826Entity.allowedafterduedateat ? (
              <TextFormat value={projectCycles20190826Entity.allowedafterduedateat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="duedate">Duedate</span>
          </dt>
          <dd>
            {projectCycles20190826Entity.duedate ? (
              <TextFormat value={projectCycles20190826Entity.duedate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/project-cycles-20190826" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-cycles-20190826/${projectCycles20190826Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectCycles20190826Detail;
