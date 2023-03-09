import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-images.reducer';

export const ClaimImagesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimImagesEntity = useAppSelector(state => state.claimImages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimImagesDetailsHeading">Claim Images</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimImagesEntity.id}</dd>
          <dt>
            <span id="images">Images</span>
          </dt>
          <dd>{claimImagesEntity.images}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimImagesEntity.createdat ? <TextFormat value={claimImagesEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimImagesEntity.updatedat ? <TextFormat value={claimImagesEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Claimrequest</dt>
          <dd>{claimImagesEntity.claimrequest ? claimImagesEntity.claimrequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim-images" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-images/${claimImagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimImagesDetail;
