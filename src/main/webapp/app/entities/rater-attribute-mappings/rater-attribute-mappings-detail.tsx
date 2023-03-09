import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rater-attribute-mappings.reducer';

export const RaterAttributeMappingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const raterAttributeMappingsEntity = useAppSelector(state => state.raterAttributeMappings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="raterAttributeMappingsDetailsHeading">Rater Attribute Mappings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{raterAttributeMappingsEntity.id}</dd>
          <dt>
            <span id="effdate">Effdate</span>
          </dt>
          <dd>
            {raterAttributeMappingsEntity.effdate ? (
              <TextFormat value={raterAttributeMappingsEntity.effdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {raterAttributeMappingsEntity.enddate ? (
              <TextFormat value={raterAttributeMappingsEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {raterAttributeMappingsEntity.createdat ? (
              <TextFormat value={raterAttributeMappingsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {raterAttributeMappingsEntity.updatedat ? (
              <TextFormat value={raterAttributeMappingsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Raterattribute</dt>
          <dd>{raterAttributeMappingsEntity.raterattribute ? raterAttributeMappingsEntity.raterattribute.id : ''}</dd>
          <dt>Attributes For</dt>
          <dd>{raterAttributeMappingsEntity.attributesFor ? raterAttributeMappingsEntity.attributesFor.id : ''}</dd>
          <dt>Attributes By</dt>
          <dd>{raterAttributeMappingsEntity.attributesBy ? raterAttributeMappingsEntity.attributesBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rater-attribute-mappings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rater-attribute-mappings/${raterAttributeMappingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RaterAttributeMappingsDetail;
