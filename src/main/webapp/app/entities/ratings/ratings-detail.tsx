import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ratings.reducer';

export const RatingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingsEntity = useAppSelector(state => state.ratings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingsDetailsHeading">Ratings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ratingsEntity.id}</dd>
          <dt>
            <span id="rateeid">Rateeid</span>
          </dt>
          <dd>{ratingsEntity.rateeid}</dd>
          <dt>
            <span id="rateetype">Rateetype</span>
          </dt>
          <dd>{ratingsEntity.rateetype}</dd>
          <dt>
            <span id="duedate">Duedate</span>
          </dt>
          <dd>{ratingsEntity.duedate ? <TextFormat value={ratingsEntity.duedate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="freeze">Freeze</span>
          </dt>
          <dd>
            {ratingsEntity.freeze ? (
              <div>
                {ratingsEntity.freezeContentType ? (
                  <a onClick={openFile(ratingsEntity.freezeContentType, ratingsEntity.freeze)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {ratingsEntity.freezeContentType}, {byteSize(ratingsEntity.freeze)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{ratingsEntity.createdat ? <TextFormat value={ratingsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{ratingsEntity.updatedat ? <TextFormat value={ratingsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>{ratingsEntity.deletedat ? <TextFormat value={ratingsEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Rater</dt>
          <dd>{ratingsEntity.rater ? ratingsEntity.rater.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ratings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ratings/${ratingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingsDetail;
