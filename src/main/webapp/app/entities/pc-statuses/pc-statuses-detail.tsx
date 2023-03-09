import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pc-statuses.reducer';

export const PcStatusesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pcStatusesEntity = useAppSelector(state => state.pcStatuses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pcStatusesDetailsHeading">Pc Statuses</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pcStatusesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{pcStatusesEntity.name}</dd>
          <dt>
            <span id="group">Group</span>
          </dt>
          <dd>{pcStatusesEntity.group}</dd>
          <dt>
            <span id="systemKey">System Key</span>
          </dt>
          <dd>{pcStatusesEntity.systemKey}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {pcStatusesEntity.createdAt ? <TextFormat value={pcStatusesEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {pcStatusesEntity.updatedAt ? <TextFormat value={pcStatusesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {pcStatusesEntity.deletedAt ? <TextFormat value={pcStatusesEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{pcStatusesEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/pc-statuses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pc-statuses/${pcStatusesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PcStatusesDetail;
