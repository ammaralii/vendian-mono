import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IEmployeeTalents } from 'app/shared/model/employee-talents.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-talents.reducer';

export const EmployeeTalentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const employeeTalentsEntity = useAppSelector(state => state.employeeTalents.entity);
  const loading = useAppSelector(state => state.employeeTalents.loading);
  const updating = useAppSelector(state => state.employeeTalents.updating);
  const updateSuccess = useAppSelector(state => state.employeeTalents.updateSuccess);

  const handleClose = () => {
    navigate('/employee-talents' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...employeeTalentsEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeTalentsEntity,
          createdat: convertDateTimeFromServer(employeeTalentsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeTalentsEntity.updatedat),
          employee: employeeTalentsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeTalents.home.createOrEditLabel" data-cy="EmployeeTalentsCreateUpdateHeading">
            Create or edit a Employee Talents
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="employee-talents-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Criticalposition"
                id="employee-talents-criticalposition"
                name="criticalposition"
                data-cy="criticalposition"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Highpotential"
                id="employee-talents-highpotential"
                name="highpotential"
                data-cy="highpotential"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Successorfor"
                id="employee-talents-successorfor"
                name="successorfor"
                data-cy="successorfor"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Criticalexperience"
                id="employee-talents-criticalexperience"
                name="criticalexperience"
                data-cy="criticalexperience"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Promotionreadiness"
                id="employee-talents-promotionreadiness"
                name="promotionreadiness"
                data-cy="promotionreadiness"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Leadershipqualities"
                id="employee-talents-leadershipqualities"
                name="leadershipqualities"
                data-cy="leadershipqualities"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Careerambitions"
                id="employee-talents-careerambitions"
                name="careerambitions"
                data-cy="careerambitions"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employee-talents-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-talents-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="employee-talents-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-talents" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EmployeeTalentsUpdate;
