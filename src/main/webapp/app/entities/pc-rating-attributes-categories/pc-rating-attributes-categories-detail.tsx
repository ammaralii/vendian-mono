import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-rating-attributes-categories.reducer';

export const PcRatingAttributesCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcRatingAttributesCategoriesEntity = useAppSelector(state => state.pcRatingAttributesCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcRatingAttributesCategoriesDetailsHeading">Pc Rating Attributes Categories</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcRatingAttributesCategoriesEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {pcRatingAttributesCategoriesEntity.effDate ? (
              <TextFormat value={pcRatingAttributesCategoriesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcRatingAttributesCategoriesEntity.createdAt ? (
              <TextFormat value={pcRatingAttributesCategoriesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcRatingAttributesCategoriesEntity.updatedAt ? (
              <TextFormat value={pcRatingAttributesCategoriesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {pcRatingAttributesCategoriesEntity.endDate ? (
              <TextFormat value={pcRatingAttributesCategoriesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcRatingAttributesCategoriesEntity.version}</dd>
          <dt>Category</dt>
          <dd>{pcRatingAttributesCategoriesEntity.category ? pcRatingAttributesCategoriesEntity.category.id : ''}</dd>
          <dt>Rating Attribute</dt>
          <dd>{pcRatingAttributesCategoriesEntity.ratingAttribute ? pcRatingAttributesCategoriesEntity.ratingAttribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pc-rating-attributes-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-rating-attributes-categories/${pcRatingAttributesCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcRatingAttributesCategoriesDetail;
