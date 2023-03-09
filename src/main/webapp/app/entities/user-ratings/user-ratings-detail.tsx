import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-ratings.reducer';

export const UserRatingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userRatingsEntity = useAppSelector(state => state.userRatings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRatingsDetailsHeading">User Ratings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userRatingsEntity.id}</dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>{userRatingsEntity.rating}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{userRatingsEntity.comment}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userRatingsEntity.createdAt ? <TextFormat value={userRatingsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userRatingsEntity.updatedAt ? <TextFormat value={userRatingsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {userRatingsEntity.deletedAt ? <TextFormat value={userRatingsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userRatingsEntity.version}</dd>
          <dt>User</dt>
          <dd>{userRatingsEntity.user ? userRatingsEntity.user.id : ''}</dd>
          <dt>Review Manager</dt>
          <dd>{userRatingsEntity.reviewManager ? userRatingsEntity.reviewManager.id : ''}</dd>
          <dt>Performance Cycle</dt>
          <dd>{userRatingsEntity.performanceCycle ? userRatingsEntity.performanceCycle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-ratings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-ratings/${userRatingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRatingsDetail;
