import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './designations.reducer';

export const DesignationsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const designationsEntity = useAppSelector(state => state.designations.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="designationsDetailsHeading">Designations</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{designationsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{designationsEntity.name}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {designationsEntity.createdat ? <TextFormat value={designationsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {designationsEntity.updatedat ? <TextFormat value={designationsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {designationsEntity.deletedat ? <TextFormat value={designationsEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/designations" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/designations/${designationsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DesignationsDetail;
