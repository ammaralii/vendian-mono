import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './performance-cycle-employee-rating.reducer';

export const PerformanceCycleEmployeeRatingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const performanceCycleEmployeeRatingEntity = useAppSelector(state => state.performanceCycleEmployeeRating.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="performanceCycleEmployeeRatingDetailsHeading">Performance Cycle Employee Rating</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{performanceCycleEmployeeRatingEntity.id}</dd>
          <dt>
            <span id="rating">Rating</span>
          </dt>
          <dd>
            {performanceCycleEmployeeRatingEntity.rating ? (
              <div>
                {performanceCycleEmployeeRatingEntity.ratingContentType ? (
                  <a
                    onClick={openFile(performanceCycleEmployeeRatingEntity.ratingContentType, performanceCycleEmployeeRatingEntity.rating)}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {performanceCycleEmployeeRatingEntity.ratingContentType}, {byteSize(performanceCycleEmployeeRatingEntity.rating)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>
            {performanceCycleEmployeeRatingEntity.comment ? (
              <div>
                {performanceCycleEmployeeRatingEntity.commentContentType ? (
                  <a
                    onClick={openFile(
                      performanceCycleEmployeeRatingEntity.commentContentType,
                      performanceCycleEmployeeRatingEntity.comment
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {performanceCycleEmployeeRatingEntity.commentContentType}, {byteSize(performanceCycleEmployeeRatingEntity.comment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {performanceCycleEmployeeRatingEntity.createdAt ? (
              <TextFormat value={performanceCycleEmployeeRatingEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {performanceCycleEmployeeRatingEntity.updatedAt ? (
              <TextFormat value={performanceCycleEmployeeRatingEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {performanceCycleEmployeeRatingEntity.deletedAt ? (
              <TextFormat value={performanceCycleEmployeeRatingEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{performanceCycleEmployeeRatingEntity.version}</dd>
          <dt>Performancecycle</dt>
          <dd>{performanceCycleEmployeeRatingEntity.performancecycle ? performanceCycleEmployeeRatingEntity.performancecycle.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{performanceCycleEmployeeRatingEntity.employee ? performanceCycleEmployeeRatingEntity.employee.id : ''}</dd>
          <dt>Manager</dt>
          <dd>{performanceCycleEmployeeRatingEntity.manager ? performanceCycleEmployeeRatingEntity.manager.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/performance-cycle-employee-rating" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/performance-cycle-employee-rating/${performanceCycleEmployeeRatingEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PerformanceCycleEmployeeRatingDetail;
