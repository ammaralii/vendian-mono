import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-attributes.reducer';

export const UserAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userAttributesEntity = useAppSelector(state => state.userAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAttributesDetailsHeading">User Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userAttributesEntity.id}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userAttributesEntity.createdAt ? (
              <TextFormat value={userAttributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userAttributesEntity.updatedAt ? (
              <TextFormat value={userAttributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userAttributesEntity.version}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userAttributesEntity.endDate ? <TextFormat value={userAttributesEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {userAttributesEntity.effDate ? <TextFormat value={userAttributesEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Attribute</dt>
          <dd>{userAttributesEntity.attribute ? userAttributesEntity.attribute.id : ''}</dd>
          <dt>User</dt>
          <dd>{userAttributesEntity.user ? userAttributesEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-attributes/${userAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAttributesDetail;
