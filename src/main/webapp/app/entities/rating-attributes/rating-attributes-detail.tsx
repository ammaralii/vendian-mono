import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating-attributes.reducer';

export const RatingAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingAttributesEntity = useAppSelector(state => state.ratingAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingAttributesDetailsHeading">Rating Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingAttributesEntity.id}</dd>
          <dt>
            <span id="raRating">Ra Rating</span>
          </dt>
          <dd>
            {ratingAttributesEntity.raRating ? (
              <div>
                {ratingAttributesEntity.raRatingContentType ? (
                  <a onClick={openFile(ratingAttributesEntity.raRatingContentType, ratingAttributesEntity.raRating)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {ratingAttributesEntity.raRatingContentType}, {byteSize(ratingAttributesEntity.raRating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>
            {ratingAttributesEntity.comment ? (
              <div>
                {ratingAttributesEntity.commentContentType ? (
                  <a onClick={openFile(ratingAttributesEntity.commentContentType, ratingAttributesEntity.comment)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {ratingAttributesEntity.commentContentType}, {byteSize(ratingAttributesEntity.comment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {ratingAttributesEntity.createdat ? (
              <TextFormat value={ratingAttributesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {ratingAttributesEntity.updatedat ? (
              <TextFormat value={ratingAttributesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Rating</dt>
          <dd>{ratingAttributesEntity.rating ? ratingAttributesEntity.rating.id : ''}</dd>
          <dt>Raterattributemapping</dt>
          <dd>{ratingAttributesEntity.raterattributemapping ? ratingAttributesEntity.raterattributemapping.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating-attributes/${ratingAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingAttributesDetail;
