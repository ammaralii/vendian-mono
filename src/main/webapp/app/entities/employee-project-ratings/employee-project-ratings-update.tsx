import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { getEntities as getProjectCycles } from 'app/entities/project-cycles/project-cycles.reducer';
import { IEmployeeProjectRatings } from 'app/shared/model/employee-project-ratings.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-project-ratings.reducer';

export const EmployeeProjectRatingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const projectCycles = useAppSelector(state => state.projectCycles.entities);
  const employeeProjectRatingsEntity = useAppSelector(state => state.employeeProjectRatings.entity);
  const loading = useAppSelector(state => state.employeeProjectRatings.loading);
  const updating = useAppSelector(state => state.employeeProjectRatings.updating);
  const updateSuccess = useAppSelector(state => state.employeeProjectRatings.updateSuccess);

  const handleClose = () => {
    navigate('/employee-project-ratings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getProjectCycles({}));
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
      ...employeeProjectRatingsEntity,
      ...values,
      projectarchitect: employees.find(it => it.id.toString() === values.projectarchitect.toString()),
      projectmanager: employees.find(it => it.id.toString() === values.projectmanager.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      projectcycle: projectCycles.find(it => it.id.toString() === values.projectcycle.toString()),
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
          ...employeeProjectRatingsEntity,
          createdat: convertDateTimeFromServer(employeeProjectRatingsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeProjectRatingsEntity.updatedat),
          projectarchitect: employeeProjectRatingsEntity?.projectarchitect?.id,
          projectmanager: employeeProjectRatingsEntity?.projectmanager?.id,
          employee: employeeProjectRatingsEntity?.employee?.id,
          projectcycle: employeeProjectRatingsEntity?.projectcycle?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeProjectRatings.home.createOrEditLabel" data-cy="EmployeeProjectRatingsCreateUpdateHeading">
            Create or edit a Employee Project Ratings
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
                <ValidatedField name="id" required readOnly id="employee-project-ratings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="employee-project-ratings-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-project-ratings-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField
                label="Pmquality"
                id="employee-project-ratings-pmquality"
                name="pmquality"
                data-cy="pmquality"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmownership"
                id="employee-project-ratings-pmownership"
                name="pmownership"
                data-cy="pmownership"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmskill"
                id="employee-project-ratings-pmskill"
                name="pmskill"
                data-cy="pmskill"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmethics"
                id="employee-project-ratings-pmethics"
                name="pmethics"
                data-cy="pmethics"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmefficiency"
                id="employee-project-ratings-pmefficiency"
                name="pmefficiency"
                data-cy="pmefficiency"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmfreeze"
                id="employee-project-ratings-pmfreeze"
                name="pmfreeze"
                data-cy="pmfreeze"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archfreeze"
                id="employee-project-ratings-archfreeze"
                name="archfreeze"
                data-cy="archfreeze"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmcomment"
                id="employee-project-ratings-pmcomment"
                name="pmcomment"
                data-cy="pmcomment"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archquality"
                id="employee-project-ratings-archquality"
                name="archquality"
                data-cy="archquality"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archownership"
                id="employee-project-ratings-archownership"
                name="archownership"
                data-cy="archownership"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archskill"
                id="employee-project-ratings-archskill"
                name="archskill"
                data-cy="archskill"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archethics"
                id="employee-project-ratings-archethics"
                name="archethics"
                data-cy="archethics"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archefficiency"
                id="employee-project-ratings-archefficiency"
                name="archefficiency"
                data-cy="archefficiency"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archcomment"
                id="employee-project-ratings-archcomment"
                name="archcomment"
                data-cy="archcomment"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archcodequality"
                id="employee-project-ratings-archcodequality"
                name="archcodequality"
                data-cy="archcodequality"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archdocumentation"
                id="employee-project-ratings-archdocumentation"
                name="archdocumentation"
                data-cy="archdocumentation"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archcollaboration"
                id="employee-project-ratings-archcollaboration"
                name="archcollaboration"
                data-cy="archcollaboration"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmdocumentation"
                id="employee-project-ratings-pmdocumentation"
                name="pmdocumentation"
                data-cy="pmdocumentation"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmcollaboration"
                id="employee-project-ratings-pmcollaboration"
                name="pmcollaboration"
                data-cy="pmcollaboration"
                openActionLabel="Open"
              />
              <ValidatedField
                id="employee-project-ratings-projectarchitect"
                name="projectarchitect"
                data-cy="projectarchitect"
                label="Projectarchitect"
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-project-ratings-projectmanager"
                name="projectmanager"
                data-cy="projectmanager"
                label="Projectmanager"
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="employee-project-ratings-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-project-ratings-projectcycle"
                name="projectcycle"
                data-cy="projectcycle"
                label="Projectcycle"
                type="select"
              >
                <option value="" key="0" />
                {projectCycles
                  ? projectCycles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-project-ratings" replace color="info">
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

export default EmployeeProjectRatingsUpdate;
