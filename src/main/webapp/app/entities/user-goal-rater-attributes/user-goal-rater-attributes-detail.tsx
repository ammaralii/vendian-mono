import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-goal-rater-attributes.reducer';

export const UserGoalRaterAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userGoalRaterAttributesEntity = useAppSelector(state => state.userGoalRaterAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userGoalRaterAttributesDetailsHeading">User Goal Rater Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userGoalRaterAttributesEntity.id}</dd>
          <dt>
            <span id="ugraRating">Ugra Rating</span>
          </dt>
          <dd>
            {userGoalRaterAttributesEntity.ugraRating ? (
              <div>
                {userGoalRaterAttributesEntity.ugraRatingContentType ? (
                  <a onClick={openFile(userGoalRaterAttributesEntity.ugraRatingContentType, userGoalRaterAttributesEntity.ugraRating)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {userGoalRaterAttributesEntity.ugraRatingContentType}, {byteSize(userGoalRaterAttributesEntity.ugraRating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>
            {userGoalRaterAttributesEntity.comment ? (
              <div>
                {userGoalRaterAttributesEntity.commentContentType ? (
                  <a onClick={openFile(userGoalRaterAttributesEntity.commentContentType, userGoalRaterAttributesEntity.comment)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {userGoalRaterAttributesEntity.commentContentType}, {byteSize(userGoalRaterAttributesEntity.comment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userGoalRaterAttributesEntity.createdAt ? (
              <TextFormat value={userGoalRaterAttributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userGoalRaterAttributesEntity.updatedAt ? (
              <TextFormat value={userGoalRaterAttributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {userGoalRaterAttributesEntity.deletedAt ? (
              <TextFormat value={userGoalRaterAttributesEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userGoalRaterAttributesEntity.version}</dd>
          <dt>Rating</dt>
          <dd>{userGoalRaterAttributesEntity.rating ? userGoalRaterAttributesEntity.rating.id : ''}</dd>
          <dt>Usergoal</dt>
          <dd>{userGoalRaterAttributesEntity.usergoal ? userGoalRaterAttributesEntity.usergoal.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-goal-rater-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-goal-rater-attributes/${userGoalRaterAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserGoalRaterAttributesDetail;
