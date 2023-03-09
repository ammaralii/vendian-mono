import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-goals.reducer';

export const UserGoalsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userGoalsEntity = useAppSelector(state => state.userGoals.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userGoalsDetailsHeading">User Goals</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userGoalsEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{userGoalsEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{userGoalsEntity.description}</dd>
          <dt>
            <span id="measurement">Measurement</span>
          </dt>
          <dd>{userGoalsEntity.measurement}</dd>
          <dt>
            <span id="weightage">Weightage</span>
          </dt>
          <dd>{userGoalsEntity.weightage}</dd>
          <dt>
            <span id="progress">Progress</span>
          </dt>
          <dd>{userGoalsEntity.progress}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{userGoalsEntity.status}</dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>
            {userGoalsEntity.dueDate ? <TextFormat value={userGoalsEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userGoalsEntity.createdAt ? <TextFormat value={userGoalsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userGoalsEntity.updatedAt ? <TextFormat value={userGoalsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {userGoalsEntity.deletedAt ? <TextFormat value={userGoalsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userGoalsEntity.version}</dd>
          <dt>User</dt>
          <dd>{userGoalsEntity.user ? userGoalsEntity.user.id : ''}</dd>
          <dt>Reference Goal</dt>
          <dd>{userGoalsEntity.referenceGoal ? userGoalsEntity.referenceGoal.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-goals" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-goals/${userGoalsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserGoalsDetail;
