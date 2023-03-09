import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-talents.reducer';

export const EmployeeTalentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeTalentsEntity = useAppSelector(state => state.employeeTalents.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeTalentsDetailsHeading">Employee Talents</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeTalentsEntity.id}</dd>
          <dt>
            <span id="criticalposition">Criticalposition</span>
          </dt>
          <dd>{employeeTalentsEntity.criticalposition ? 'true' : 'false'}</dd>
          <dt>
            <span id="highpotential">Highpotential</span>
          </dt>
          <dd>{employeeTalentsEntity.highpotential ? 'true' : 'false'}</dd>
          <dt>
            <span id="successorfor">Successorfor</span>
          </dt>
          <dd>{employeeTalentsEntity.successorfor}</dd>
          <dt>
            <span id="criticalexperience">Criticalexperience</span>
          </dt>
          <dd>{employeeTalentsEntity.criticalexperience ? 'true' : 'false'}</dd>
          <dt>
            <span id="promotionreadiness">Promotionreadiness</span>
          </dt>
          <dd>{employeeTalentsEntity.promotionreadiness}</dd>
          <dt>
            <span id="leadershipqualities">Leadershipqualities</span>
          </dt>
          <dd>{employeeTalentsEntity.leadershipqualities}</dd>
          <dt>
            <span id="careerambitions">Careerambitions</span>
          </dt>
          <dd>{employeeTalentsEntity.careerambitions}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeTalentsEntity.createdat ? (
              <TextFormat value={employeeTalentsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeTalentsEntity.updatedat ? (
              <TextFormat value={employeeTalentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Employee</dt>
          <dd>{employeeTalentsEntity.employee ? employeeTalentsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-talents" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-talents/${employeeTalentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeTalentsDetail;
