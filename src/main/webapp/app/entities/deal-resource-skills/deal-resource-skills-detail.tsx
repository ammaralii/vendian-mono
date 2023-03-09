import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-resource-skills.reducer';

export const DealResourceSkillsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealResourceSkillsEntity = useAppSelector(state => state.dealResourceSkills.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealResourceSkillsDetailsHeading">Deal Resource Skills</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealResourceSkillsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealResourceSkillsEntity.createdat ? (
              <TextFormat value={dealResourceSkillsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {dealResourceSkillsEntity.updatedat ? (
              <TextFormat value={dealResourceSkillsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {dealResourceSkillsEntity.deletedat ? (
              <TextFormat value={dealResourceSkillsEntity.deletedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Resource</dt>
          <dd>{dealResourceSkillsEntity.resource ? dealResourceSkillsEntity.resource.id : ''}</dd>
          <dt>Skill</dt>
          <dd>{dealResourceSkillsEntity.skill ? dealResourceSkillsEntity.skill.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deal-resource-skills" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-resource-skills/${dealResourceSkillsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealResourceSkillsDetail;
