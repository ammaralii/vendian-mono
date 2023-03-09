import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tracks.reducer';

export const TracksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tracksEntity = useAppSelector(state => state.tracks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tracksDetailsHeading">Tracks</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tracksEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{tracksEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{tracksEntity.description}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{tracksEntity.createdat ? <TextFormat value={tracksEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{tracksEntity.updatedat ? <TextFormat value={tracksEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>{tracksEntity.deletedat ? <TextFormat value={tracksEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Competency</dt>
          <dd>{tracksEntity.competency ? tracksEntity.competency.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tracks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tracks/${tracksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TracksDetail;
