import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reasons.reducer';

export const ReasonsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reasonsEntity = useAppSelector(state => state.reasons.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reasonsDetailsHeading">Reasons</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reasonsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{reasonsEntity.name}</dd>
          <dt>
            <span id="isdefault">Isdefault</span>
          </dt>
          <dd>{reasonsEntity.isdefault ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{reasonsEntity.createdat ? <TextFormat value={reasonsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{reasonsEntity.updatedat ? <TextFormat value={reasonsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/reasons" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reasons/${reasonsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReasonsDetail;
