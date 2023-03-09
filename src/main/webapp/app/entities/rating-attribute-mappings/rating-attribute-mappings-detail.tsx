import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating-attribute-mappings.reducer';

export const RatingAttributeMappingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingAttributeMappingsEntity = useAppSelector(state => state.ratingAttributeMappings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingAttributeMappingsDetailsHeading">Rating Attribute Mappings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingAttributeMappingsEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {ratingAttributeMappingsEntity.effDate ? (
              <TextFormat value={ratingAttributeMappingsEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {ratingAttributeMappingsEntity.createdAt ? (
              <TextFormat value={ratingAttributeMappingsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {ratingAttributeMappingsEntity.updatedAt ? (
              <TextFormat value={ratingAttributeMappingsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {ratingAttributeMappingsEntity.endDate ? (
              <TextFormat value={ratingAttributeMappingsEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{ratingAttributeMappingsEntity.version}</dd>
          <dt>Attribute</dt>
          <dd>{ratingAttributeMappingsEntity.attribute ? ratingAttributeMappingsEntity.attribute.id : ''}</dd>
          <dt>Rating Attribute</dt>
          <dd>{ratingAttributeMappingsEntity.ratingAttribute ? ratingAttributeMappingsEntity.ratingAttribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating-attribute-mappings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating-attribute-mappings/${ratingAttributeMappingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingAttributeMappingsDetail;
