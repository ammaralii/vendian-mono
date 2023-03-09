import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-rating-attributes.reducer';

export const PcRatingAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcRatingAttributesEntity = useAppSelector(state => state.pcRatingAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcRatingAttributesDetailsHeading">Pc Rating Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcRatingAttributesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{pcRatingAttributesEntity.name}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {pcRatingAttributesEntity.effDate ? (
              <TextFormat value={pcRatingAttributesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcRatingAttributesEntity.createdAt ? (
              <TextFormat value={pcRatingAttributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcRatingAttributesEntity.updatedAt ? (
              <TextFormat value={pcRatingAttributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {pcRatingAttributesEntity.endDate ? (
              <TextFormat value={pcRatingAttributesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcRatingAttributesEntity.version}</dd>
          <dt>
            <span id="subCategory">Sub Category</span>
          </dt>
          <dd>{pcRatingAttributesEntity.subCategory}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{pcRatingAttributesEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/pc-rating-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-rating-attributes/${pcRatingAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcRatingAttributesDetail;
