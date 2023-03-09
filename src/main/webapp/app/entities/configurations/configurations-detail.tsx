import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './configurations.reducer';

export const ConfigurationsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const configurationsEntity = useAppSelector(state => state.configurations.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="configurationsDetailsHeading">Configurations</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{configurationsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{configurationsEntity.name}</dd>
          <dt>
            <span id="group">Group</span>
          </dt>
          <dd>{configurationsEntity.group}</dd>
          <dt>
            <span id="intValue">Int Value</span>
          </dt>
          <dd>{configurationsEntity.intValue}</dd>
          <dt>
            <span id="stringValue">String Value</span>
          </dt>
          <dd>{configurationsEntity.stringValue}</dd>
          <dt>
            <span id="doubleValue">Double Value</span>
          </dt>
          <dd>{configurationsEntity.doubleValue}</dd>
          <dt>
            <span id="dateValue">Date Value</span>
          </dt>
          <dd>
            {configurationsEntity.dateValue ? (
              <TextFormat value={configurationsEntity.dateValue} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="jsonValue">Json Value</span>
          </dt>
          <dd>{configurationsEntity.jsonValue}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {configurationsEntity.createdAt ? (
              <TextFormat value={configurationsEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {configurationsEntity.updatedAt ? (
              <TextFormat value={configurationsEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {configurationsEntity.deletedAt ? (
              <TextFormat value={configurationsEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{configurationsEntity.version}</dd>
        </dl>
        <Button tag={Link} to="/configurations" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/configurations/${configurationsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConfigurationsDetail;
