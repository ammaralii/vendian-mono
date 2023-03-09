import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-types-olds.reducer';

export const LeaveTypesOldsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveTypesOldsEntity = useAppSelector(state => state.leaveTypesOlds.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveTypesOldsDetailsHeading">Leave Types Olds</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveTypesOldsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{leaveTypesOldsEntity.name}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{leaveTypesOldsEntity.isactive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {leaveTypesOldsEntity.createdat ? (
              <TextFormat value={leaveTypesOldsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {leaveTypesOldsEntity.updatedat ? (
              <TextFormat value={leaveTypesOldsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/leave-types-olds" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-types-olds/${leaveTypesOldsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveTypesOldsDetail;
