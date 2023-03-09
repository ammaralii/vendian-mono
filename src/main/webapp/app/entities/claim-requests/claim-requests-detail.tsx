import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-requests.reducer';

export const ClaimRequestsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimRequestsEntity = useAppSelector(state => state.claimRequests.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimRequestsDetailsHeading">Claim Requests</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimRequestsEntity.id}</dd>
          <dt>
            <span id="projectid">Projectid</span>
          </dt>
          <dd>{claimRequestsEntity.projectid}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{claimRequestsEntity.comments}</dd>
          <dt>
            <span id="amountreleased">Amountreleased</span>
          </dt>
          <dd>{claimRequestsEntity.amountreleased}</dd>
          <dt>
            <span id="designation">Designation</span>
          </dt>
          <dd>{claimRequestsEntity.designation}</dd>
          <dt>
            <span id="department">Department</span>
          </dt>
          <dd>{claimRequestsEntity.department}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{claimRequestsEntity.location}</dd>
          <dt>
            <span id="manager">Manager</span>
          </dt>
          <dd>{claimRequestsEntity.manager}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimRequestsEntity.createdat ? (
              <TextFormat value={claimRequestsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimRequestsEntity.updatedat ? (
              <TextFormat value={claimRequestsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{claimRequestsEntity.employee ? claimRequestsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim-requests" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-requests/${claimRequestsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimRequestsDetail;
