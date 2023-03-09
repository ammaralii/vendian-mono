import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-ratings-trainings.reducer';

export const PcRatingsTrainingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcRatingsTrainingsEntity = useAppSelector(state => state.pcRatingsTrainings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcRatingsTrainingsDetailsHeading">Pc Ratings Trainings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.id}</dd>
          <dt>
            <span id="strength">Strength</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.strength}</dd>
          <dt>
            <span id="developmentArea">Development Area</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.developmentArea}</dd>
          <dt>
            <span id="careerAmbition">Career Ambition</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.careerAmbition}</dd>
          <dt>
            <span id="shortCourse">Short Course</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.shortCourse}</dd>
          <dt>
            <span id="technicalTraining">Technical Training</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.technicalTraining}</dd>
          <dt>
            <span id="softSkillsTraining">Soft Skills Training</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.softSkillsTraining}</dd>
          <dt>
            <span id="criticalPosition">Critical Position</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.criticalPosition ? 'true' : 'false'}</dd>
          <dt>
            <span id="highPotential">High Potential</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.highPotential ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcRatingsTrainingsEntity.createdAt ? (
              <TextFormat value={pcRatingsTrainingsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcRatingsTrainingsEntity.updatedAt ? (
              <TextFormat value={pcRatingsTrainingsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {pcRatingsTrainingsEntity.deletedAt ? (
              <TextFormat value={pcRatingsTrainingsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcRatingsTrainingsEntity.version}</dd>
          <dt>Successor For</dt>
          <dd>{pcRatingsTrainingsEntity.successorFor ? pcRatingsTrainingsEntity.successorFor.id : ''}</dd>
          <dt>Rating</dt>
          <dd>{pcRatingsTrainingsEntity.rating ? pcRatingsTrainingsEntity.rating.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pc-ratings-trainings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-ratings-trainings/${pcRatingsTrainingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcRatingsTrainingsDetail;
