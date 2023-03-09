import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-skills.reducer';

export const EmployeeSkillsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeSkillsEntity = useAppSelector(state => state.employeeSkills.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeSkillsDetailsHeading">Employee Skills</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeSkillsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeSkillsEntity.createdat ? (
              <TextFormat value={employeeSkillsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeSkillsEntity.updatedat ? (
              <TextFormat value={employeeSkillsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expertise">Expertise</span>
          </dt>
          <dd>{employeeSkillsEntity.expertise}</dd>
          <dt>Employee</dt>
          <dd>{employeeSkillsEntity.employee ? employeeSkillsEntity.employee.id : ''}</dd>
          <dt>Skill</dt>
          <dd>{employeeSkillsEntity.skill ? employeeSkillsEntity.skill.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-skills" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-skills/${employeeSkillsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeSkillsDetail;
