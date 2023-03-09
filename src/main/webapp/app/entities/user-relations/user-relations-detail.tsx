import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-relations.reducer';

export const UserRelationsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userRelationsEntity = useAppSelector(state => state.userRelations.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRelationsDetailsHeading">User Relations</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userRelationsEntity.id}</dd>
          <dt>
            <span id="referenceType">Reference Type</span>
          </dt>
          <dd>{userRelationsEntity.referenceType}</dd>
          <dt>
            <span id="referenceId">Reference Id</span>
          </dt>
          <dd>{userRelationsEntity.referenceId}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {userRelationsEntity.effDate ? <TextFormat value={userRelationsEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userRelationsEntity.createdAt ? (
              <TextFormat value={userRelationsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userRelationsEntity.updatedAt ? (
              <TextFormat value={userRelationsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userRelationsEntity.endDate ? <TextFormat value={userRelationsEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userRelationsEntity.version}</dd>
          <dt>User</dt>
          <dd>{userRelationsEntity.user ? userRelationsEntity.user.id : ''}</dd>
          <dt>Attribute</dt>
          <dd>{userRelationsEntity.attribute ? userRelationsEntity.attribute.id : ''}</dd>
          <dt>Related User</dt>
          <dd>{userRelationsEntity.relatedUser ? userRelationsEntity.relatedUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-relations" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-relations/${userRelationsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRelationsDetail;
