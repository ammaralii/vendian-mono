import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projects.reducer';

export const ProjectsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectsEntity = useAppSelector(state => state.projects.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectsDetailsHeading">Projects</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{projectsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{projectsEntity.name}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{projectsEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="isdelete">Isdelete</span>
          </dt>
          <dd>{projectsEntity.isdelete ? 'true' : 'false'}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>{projectsEntity.startdate ? <TextFormat value={projectsEntity.startdate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>{projectsEntity.enddate ? <TextFormat value={projectsEntity.enddate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="colorcode">Colorcode</span>
          </dt>
          <dd>{projectsEntity.colorcode}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{projectsEntity.createdat ? <TextFormat value={projectsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{projectsEntity.updatedat ? <TextFormat value={projectsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deliverymanagerid">Deliverymanagerid</span>
          </dt>
          <dd>{projectsEntity.deliverymanagerid}</dd>
          <dt>
            <span id="architectid">Architectid</span>
          </dt>
          <dd>{projectsEntity.architectid}</dd>
          <dt>
            <span id="isdeleted">Isdeleted</span>
          </dt>
          <dd>{projectsEntity.isdeleted}</dd>
          <dt>
            <span id="approvedresources">Approvedresources</span>
          </dt>
          <dd>{projectsEntity.approvedresources}</dd>
          <dt>
            <span id="releaseat">Releaseat</span>
          </dt>
          <dd>{projectsEntity.releaseat ? <TextFormat value={projectsEntity.releaseat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Projectmanager</dt>
          <dd>{projectsEntity.projectmanager ? projectsEntity.projectmanager.id : ''}</dd>
          <dt>Businessunit</dt>
          <dd>{projectsEntity.businessunit ? projectsEntity.businessunit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/projects" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projects/${projectsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectsDetail;
