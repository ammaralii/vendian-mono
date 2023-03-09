import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './goal-group-mappings.reducer';

export const GoalGroupMappingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const goalGroupMappingsEntity = useAppSelector(state => state.goalGroupMappings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="goalGroupMappingsDetailsHeading">Goal Group Mappings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{goalGroupMappingsEntity.id}</dd>
          <dt>
            <span id="weightage">Weightage</span>
          </dt>
          <dd>{goalGroupMappingsEntity.weightage}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {goalGroupMappingsEntity.createdAt ? (
              <TextFormat value={goalGroupMappingsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {goalGroupMappingsEntity.updatedAt ? (
              <TextFormat value={goalGroupMappingsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {goalGroupMappingsEntity.deletedAt ? (
              <TextFormat value={goalGroupMappingsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{goalGroupMappingsEntity.version}</dd>
          <dt>Goal Group</dt>
          <dd>{goalGroupMappingsEntity.goalGroup ? goalGroupMappingsEntity.goalGroup.id : ''}</dd>
          <dt>Goal</dt>
          <dd>{goalGroupMappingsEntity.goal ? goalGroupMappingsEntity.goal.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/goal-group-mappings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/goal-group-mappings/${goalGroupMappingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GoalGroupMappingsDetail;
