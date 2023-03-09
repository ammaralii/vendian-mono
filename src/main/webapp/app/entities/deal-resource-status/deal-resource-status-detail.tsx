import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-resource-status.reducer';

export const DealResourceStatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealResourceStatusEntity = useAppSelector(state => state.dealResourceStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealResourceStatusDetailsHeading">Deal Resource Status</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealResourceStatusEntity.id}</dd>
          <dt>
            <span id="displayname">Displayname</span>
          </dt>
          <dd>{dealResourceStatusEntity.displayname}</dd>
          <dt>
            <span id="group">Group</span>
          </dt>
          <dd>{dealResourceStatusEntity.group}</dd>
          <dt>
            <span id="systemKey">System Key</span>
          </dt>
          <dd>{dealResourceStatusEntity.systemKey}</dd>
          <dt>
            <span id="effectivedate">Effectivedate</span>
          </dt>
          <dd>
            {dealResourceStatusEntity.effectivedate ? (
              <TextFormat value={dealResourceStatusEntity.effectivedate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {dealResourceStatusEntity.enddate ? (
              <TextFormat value={dealResourceStatusEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealResourceStatusEntity.createdat ? (
              <TextFormat value={dealResourceStatusEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {dealResourceStatusEntity.updatedat ? (
              <TextFormat value={dealResourceStatusEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/deal-resource-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-resource-status/${dealResourceStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealResourceStatusDetail;
