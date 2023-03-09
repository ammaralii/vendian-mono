import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './z-leave-requests.reducer';

export const ZLeaveRequestsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zLeaveRequestsEntity = useAppSelector(state => state.zLeaveRequests.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zLeaveRequestsDetailsHeading">Z Leave Requests</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zLeaveRequestsEntity.id}</dd>
          <dt>
            <span id="action">Action</span>
          </dt>
          <dd>{zLeaveRequestsEntity.action}</dd>
          <dt>
            <span id="userid">Userid</span>
          </dt>
          <dd>{zLeaveRequestsEntity.userid}</dd>
          <dt>
            <span id="managerid">Managerid</span>
          </dt>
          <dd>{zLeaveRequestsEntity.managerid}</dd>
          <dt>
            <span id="requestparams">Requestparams</span>
          </dt>
          <dd>{zLeaveRequestsEntity.requestparams}</dd>
          <dt>
            <span id="responseparams">Responseparams</span>
          </dt>
          <dd>{zLeaveRequestsEntity.responseparams}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {zLeaveRequestsEntity.createdat ? (
              <TextFormat value={zLeaveRequestsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {zLeaveRequestsEntity.updatedat ? (
              <TextFormat value={zLeaveRequestsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/z-leave-requests" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/z-leave-requests/${zLeaveRequestsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZLeaveRequestsDetail;
