import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-approvers.reducer';

export const ClaimApproversDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimApproversEntity = useAppSelector(state => state.claimApprovers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimApproversDetailsHeading">Claim Approvers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimApproversEntity.id}</dd>
          <dt>
            <span id="referenceid">Referenceid</span>
          </dt>
          <dd>{claimApproversEntity.referenceid}</dd>
          <dt>
            <span id="designation">Designation</span>
          </dt>
          <dd>{claimApproversEntity.designation}</dd>
          <dt>
            <span id="approveorder">Approveorder</span>
          </dt>
          <dd>{claimApproversEntity.approveorder}</dd>
          <dt>
            <span id="reference">Reference</span>
          </dt>
          <dd>{claimApproversEntity.reference}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{claimApproversEntity.comments}</dd>
          <dt>
            <span id="approvedby">Approvedby</span>
          </dt>
          <dd>{claimApproversEntity.approvedby}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimApproversEntity.createdat ? (
              <TextFormat value={claimApproversEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimApproversEntity.updatedat ? (
              <TextFormat value={claimApproversEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Status</dt>
          <dd>{claimApproversEntity.status ? claimApproversEntity.status.id : ''}</dd>
          <dt>Claimrequest</dt>
          <dd>{claimApproversEntity.claimrequest ? claimApproversEntity.claimrequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim-approvers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-approvers/${claimApproversEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimApproversDetail;
