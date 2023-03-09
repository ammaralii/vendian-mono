import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './departments.reducer';

export const DepartmentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const departmentsEntity = useAppSelector(state => state.departments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="departmentsDetailsHeading">Departments</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{departmentsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{departmentsEntity.name}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {departmentsEntity.createdat ? <TextFormat value={departmentsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {departmentsEntity.updatedat ? <TextFormat value={departmentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Division</dt>
          <dd>{departmentsEntity.division ? departmentsEntity.division.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/departments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/departments/${departmentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepartmentsDetail;
