import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-details.reducer';

export const ClaimDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimDetailsEntity = useAppSelector(state => state.claimDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimDetailsDetailsHeading">Claim Details</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimDetailsEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{claimDetailsEntity.amount}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {claimDetailsEntity.startdate ? <TextFormat value={claimDetailsEntity.startdate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {claimDetailsEntity.enddate ? <TextFormat value={claimDetailsEntity.enddate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{claimDetailsEntity.description}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimDetailsEntity.createdat ? <TextFormat value={claimDetailsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimDetailsEntity.updatedat ? <TextFormat value={claimDetailsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Claimrequest</dt>
          <dd>{claimDetailsEntity.claimrequest ? claimDetailsEntity.claimrequest.id : ''}</dd>
          <dt>Claimtype</dt>
          <dd>{claimDetailsEntity.claimtype ? claimDetailsEntity.claimtype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-details/${claimDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimDetailsDetail;
