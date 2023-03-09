import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './configs.reducer';

export const ConfigsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const configsEntity = useAppSelector(state => state.configs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="configsDetailsHeading">Configs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{configsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{configsEntity.name}</dd>
          <dt>
            <span id="group">Group</span>
          </dt>
          <dd>{configsEntity.group}</dd>
          <dt>
            <span id="intvalue">Intvalue</span>
          </dt>
          <dd>{configsEntity.intvalue}</dd>
          <dt>
            <span id="stringvalue">Stringvalue</span>
          </dt>
          <dd>{configsEntity.stringvalue}</dd>
          <dt>
            <span id="decimalvalue">Decimalvalue</span>
          </dt>
          <dd>{configsEntity.decimalvalue}</dd>
          <dt>
            <span id="jsonvalue">Jsonvalue</span>
          </dt>
          <dd>{configsEntity.jsonvalue}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{configsEntity.createdat ? <TextFormat value={configsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{configsEntity.updatedat ? <TextFormat value={configsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/configs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/configs/${configsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConfigsDetail;
