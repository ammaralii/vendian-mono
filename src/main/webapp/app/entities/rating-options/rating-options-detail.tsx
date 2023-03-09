import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating-options.reducer';

export const RatingOptionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingOptionsEntity = useAppSelector(state => state.ratingOptions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingOptionsDetailsHeading">Rating Options</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingOptionsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{ratingOptionsEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{ratingOptionsEntity.description}</dd>
          <dt>
            <span id="from">From</span>
          </dt>
          <dd>{ratingOptionsEntity.from}</dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>{ratingOptionsEntity.to}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>
            {ratingOptionsEntity.effDate ? <TextFormat value={ratingOptionsEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {ratingOptionsEntity.createdAt ? (
              <TextFormat value={ratingOptionsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {ratingOptionsEntity.updatedAt ? (
              <TextFormat value={ratingOptionsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {ratingOptionsEntity.endDate ? <TextFormat value={ratingOptionsEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{ratingOptionsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/rating-options" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating-options/${ratingOptionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingOptionsDetail;
