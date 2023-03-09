import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-ratings.reducer';

export const PcRatingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcRatingsEntity = useAppSelector(state => state.pcRatings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcRatingsDetailsHeading">Pc Ratings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcRatingsEntity.id}</dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>
            {pcRatingsEntity.rating ? (
              <div>
                {pcRatingsEntity.ratingContentType ? (
                  <a onClick={openFile(pcRatingsEntity.ratingContentType, pcRatingsEntity.rating)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {pcRatingsEntity.ratingContentType}, {byteSize(pcRatingsEntity.rating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>
            {pcRatingsEntity.comment ? (
              <div>
                {pcRatingsEntity.commentContentType ? (
                  <a onClick={openFile(pcRatingsEntity.commentContentType, pcRatingsEntity.comment)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {pcRatingsEntity.commentContentType}, {byteSize(pcRatingsEntity.comment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="stausDate">Staus Date</span>
          </dt>
          <dd>
            {pcRatingsEntity.stausDate ? <TextFormat value={pcRatingsEntity.stausDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dueDate">Due Date</span>
          </dt>
          <dd>
            {pcRatingsEntity.dueDate ? <TextFormat value={pcRatingsEntity.dueDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="freeze">Freeze</span>
          </dt>
          <dd>{pcRatingsEntity.freeze ? 'true' : 'false'}</dd>
          <dt>
            <span id="includeInFinalRating">Include In Final Rating</span>
          </dt>
          <dd>{pcRatingsEntity.includeInFinalRating ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcRatingsEntity.createdAt ? <TextFormat value={pcRatingsEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcRatingsEntity.updatedAt ? <TextFormat value={pcRatingsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {pcRatingsEntity.deletedAt ? <TextFormat value={pcRatingsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcRatingsEntity.version}</dd>
          <dt>Status</dt>
          <dd>{pcRatingsEntity.status ? pcRatingsEntity.status.id : ''}</dd>
          <dt>Pcemployeerating</dt>
          <dd>{pcRatingsEntity.pcemployeerating ? pcRatingsEntity.pcemployeerating.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{pcRatingsEntity.employee ? pcRatingsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pc-ratings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-ratings/${pcRatingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcRatingsDetail;
