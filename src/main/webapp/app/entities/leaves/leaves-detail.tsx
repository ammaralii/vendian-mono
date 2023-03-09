import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leaves.reducer';

export const LeavesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leavesEntity = useAppSelector(state => state.leaves.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leavesDetailsHeading">Leaves</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leavesEntity.id}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>{leavesEntity.effDate ? <TextFormat value={leavesEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{leavesEntity.createdAt ? <TextFormat value={leavesEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{leavesEntity.updatedAt ? <TextFormat value={leavesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{leavesEntity.endDate ? <TextFormat value={leavesEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{leavesEntity.version}</dd>
          <dt>User</dt>
          <dd>{leavesEntity.user ? leavesEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leaves" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leaves/${leavesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeavesDetail;
