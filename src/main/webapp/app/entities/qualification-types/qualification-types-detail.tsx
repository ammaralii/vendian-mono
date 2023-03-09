import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './qualification-types.reducer';

export const QualificationTypesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const qualificationTypesEntity = useAppSelector(state => state.qualificationTypes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="qualificationTypesDetailsHeading">Qualification Types</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{qualificationTypesEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{qualificationTypesEntity.name}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {qualificationTypesEntity.createdat ? (
              <TextFormat value={qualificationTypesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {qualificationTypesEntity.updatedat ? (
              <TextFormat value={qualificationTypesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/qualification-types" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qualification-types/${qualificationTypesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default QualificationTypesDetail;
