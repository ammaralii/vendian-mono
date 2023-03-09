import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-rater-attributes.reducer';

export const PcRaterAttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcRaterAttributesEntity = useAppSelector(state => state.pcRaterAttributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcRaterAttributesDetailsHeading">Pc Rater Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcRaterAttributesEntity.id}</dd>
          <dt>
            <span id="pcRating">Pc Rating</span>
          </dt>
          <dd>
            {pcRaterAttributesEntity.pcRating ? (
              <div>
                {pcRaterAttributesEntity.pcRatingContentType ? (
                  <a onClick={openFile(pcRaterAttributesEntity.pcRatingContentType, pcRaterAttributesEntity.pcRating)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {pcRaterAttributesEntity.pcRatingContentType}, {byteSize(pcRaterAttributesEntity.pcRating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>
            {pcRaterAttributesEntity.comment ? (
              <div>
                {pcRaterAttributesEntity.commentContentType ? (
                  <a onClick={openFile(pcRaterAttributesEntity.commentContentType, pcRaterAttributesEntity.comment)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {pcRaterAttributesEntity.commentContentType}, {byteSize(pcRaterAttributesEntity.comment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcRaterAttributesEntity.createdAt ? (
              <TextFormat value={pcRaterAttributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcRaterAttributesEntity.updatedAt ? (
              <TextFormat value={pcRaterAttributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {pcRaterAttributesEntity.deletedAt ? (
              <TextFormat value={pcRaterAttributesEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcRaterAttributesEntity.version}</dd>
          <dt>Rating Attribute Mapping</dt>
          <dd>{pcRaterAttributesEntity.ratingAttributeMapping ? pcRaterAttributesEntity.ratingAttributeMapping.id : ''}</dd>
          <dt>Rating Option</dt>
          <dd>{pcRaterAttributesEntity.ratingOption ? pcRaterAttributesEntity.ratingOption.id : ''}</dd>
          <dt>Rating</dt>
          <dd>{pcRaterAttributesEntity.rating ? pcRaterAttributesEntity.rating.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pc-rater-attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-rater-attributes/${pcRaterAttributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcRaterAttributesDetail;
