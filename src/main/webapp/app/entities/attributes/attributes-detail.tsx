import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attributes.reducer';

export const AttributesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attributesEntity = useAppSelector(state => state.attributes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributesDetailsHeading">Attributes</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attributesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{attributesEntity.name}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {attributesEntity.createdAt ? <TextFormat value={attributesEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {attributesEntity.updatedAt ? <TextFormat value={attributesEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{attributesEntity.endDate ? <TextFormat value={attributesEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{attributesEntity.version}</dd>
          <dt>
            <span id="effDate">Eff Date</span>
          </dt>
          <dd>{attributesEntity.effDate ? <TextFormat value={attributesEntity.effDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/attributes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attributes/${attributesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttributesDetail;
