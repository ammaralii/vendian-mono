import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './goals.reducer';

export const GoalsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const goalsEntity = useAppSelector(state => state.goals.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="goalsDetailsHeading">Goals</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{goalsEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{goalsEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{goalsEntity.description}</dd>
          <dt>
            <span id="measurement">Measurement</span>
          </dt>
          <dd>{goalsEntity.measurement}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{goalsEntity.createdAt ? <TextFormat value={goalsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{goalsEntity.updatedAt ? <TextFormat value={goalsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>{goalsEntity.deletedAt ? <TextFormat value={goalsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{goalsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/goals" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/goals/${goalsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GoalsDetail;
