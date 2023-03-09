import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './skills.reducer';

export const SkillsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const skillsEntity = useAppSelector(state => state.skills.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillsDetailsHeading">Skills</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{skillsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{skillsEntity.name}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{skillsEntity.createdat ? <TextFormat value={skillsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{skillsEntity.updatedat ? <TextFormat value={skillsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/skills" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skills/${skillsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SkillsDetail;
