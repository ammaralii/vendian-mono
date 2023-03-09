import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating-categories.reducer';

export const RatingCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingCategoriesEntity = useAppSelector(state => state.ratingCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingCategoriesDetailsHeading">Rating Categories</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingCategoriesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{ratingCategoriesEntity.name}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {ratingCategoriesEntity.effDate ? (
              <TextFormat value={ratingCategoriesEntity.effDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {ratingCategoriesEntity.createdAt ? (
              <TextFormat value={ratingCategoriesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {ratingCategoriesEntity.updatedAt ? (
              <TextFormat value={ratingCategoriesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {ratingCategoriesEntity.endDate ? (
              <TextFormat value={ratingCategoriesEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{ratingCategoriesEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/rating-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating-categories/${ratingCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingCategoriesDetail;
