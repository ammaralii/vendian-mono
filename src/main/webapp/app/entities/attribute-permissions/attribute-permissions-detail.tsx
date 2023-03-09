import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attribute-permissions.reducer';

export const AttributePermissionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attributePermissionsEntity = useAppSelector(state => state.attributePermissions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attributePermissionsDetailsHeading">Attribute Permissions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attributePermissionsEntity.id}</dd>
          <dt>
            <span id="method">Method</span>
          </dt>
          <dd>{attributePermissionsEntity.method}</dd>
          <dt>
            <span id="route">Route</span>
          </dt>
          <dd>{attributePermissionsEntity.route}</dd>
          <dt>
            <span id="responsepermissions">Responsepermissions</span>
          </dt>
          <dd>{attributePermissionsEntity.responsepermissions}</dd>
          <dt>
            <span id="requestpermissions">Requestpermissions</span>
          </dt>
          <dd>{attributePermissionsEntity.requestpermissions}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {attributePermissionsEntity.createdat ? (
              <TextFormat value={attributePermissionsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {attributePermissionsEntity.updatedat ? (
              <TextFormat value={attributePermissionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/attribute-permissions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute-permissions/${attributePermissionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttributePermissionsDetail;
