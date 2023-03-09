import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rater-attributes.reducer';

export const RaterAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const raterAttributesEntity = useAppSelector(state => state.raterAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="raterAttributesDetailsHeading">Rater Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{raterAttributesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{raterAttributesEntity.name}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{raterAttributesEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{raterAttributesEntity.description}</dd>
          <dt>
            <span id="effdate">Effdate</span>
          </dt>
          <dd>
            {raterAttributesEntity.effdate ? (
              <TextFormat value={raterAttributesEntity.effdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {raterAttributesEntity.enddate ? (
              <TextFormat value={raterAttributesEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {raterAttributesEntity.createdat ? (
              <TextFormat value={raterAttributesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {raterAttributesEntity.updatedat ? (
              <TextFormat value={raterAttributesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/rater-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rater-attributes/${raterAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RaterAttributesDetail;
