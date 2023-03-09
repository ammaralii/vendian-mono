import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './goal-groups.reducer';

export const GoalGroupsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const goalGroupsEntity = useAppSelector(state => state.goalGroups.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="goalGroupsDetailsHeading">Goal Groups</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{goalGroupsEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{goalGroupsEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{goalGroupsEntity.description}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {goalGroupsEntity.createdAt ? <TextFormat value={goalGroupsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {goalGroupsEntity.updatedAt ? <TextFormat value={goalGroupsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {goalGroupsEntity.deletedAt ? <TextFormat value={goalGroupsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{goalGroupsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/goal-groups" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/goal-groups/${goalGroupsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GoalGroupsDetail;
