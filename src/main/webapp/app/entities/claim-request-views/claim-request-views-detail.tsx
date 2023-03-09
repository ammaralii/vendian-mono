import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-request-views.reducer';

export const ClaimRequestViewsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimRequestViewsEntity = useAppSelector(state => state.claimRequestViews.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimRequestViewsDetailsHeading">Claim Request Views</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimRequestViewsEntity.id}</dd>
          <dt>
            <span id="costcenter">Costcenter</span>
          </dt>
          <dd>{claimRequestViewsEntity.costcenter}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{claimRequestViewsEntity.comments}</dd>
          <dt>
            <span id="amountreleased">Amountreleased</span>
          </dt>
          <dd>{claimRequestViewsEntity.amountreleased}</dd>
          <dt>
            <span id="designation">Designation</span>
          </dt>
          <dd>{claimRequestViewsEntity.designation}</dd>
          <dt>
            <span id="department">Department</span>
          </dt>
          <dd>{claimRequestViewsEntity.department}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{claimRequestViewsEntity.location}</dd>
          <dt>
            <span id="manager">Manager</span>
          </dt>
          <dd>{claimRequestViewsEntity.manager}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimRequestViewsEntity.createdat ? (
              <TextFormat value={claimRequestViewsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimRequestViewsEntity.updatedat ? (
              <TextFormat value={claimRequestViewsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{claimRequestViewsEntity.employee ? claimRequestViewsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim-request-views" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-request-views/${claimRequestViewsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimRequestViewsDetail;
