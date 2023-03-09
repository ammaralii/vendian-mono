import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './marital-statuses.reducer';

export const MaritalStatusesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const maritalStatusesEntity = useAppSelector(state => state.maritalStatuses.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="maritalStatusesDetailsHeading">Marital Statuses</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{maritalStatusesEntity.id}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{maritalStatusesEntity.status}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {maritalStatusesEntity.createdat ? (
              <TextFormat value={maritalStatusesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {maritalStatusesEntity.updatedat ? (
              <TextFormat value={maritalStatusesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/marital-statuses" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/marital-statuses/${maritalStatusesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaritalStatusesDetail;
