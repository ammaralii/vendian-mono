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
import { IBusinessUnits } from 'app/shared/model/business-units.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-units/business-units.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntity, updateEntity, createEntity, reset } from './projects.reducer';

export const ProjectsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const businessUnits = useAppSelector(state => state.businessUnits.entities);
  const projectsEntity = useAppSelector(state => state.projects.entity);
  const loading = useAppSelector(state => state.projects.loading);
  const updating = useAppSelector(state => state.projects.updating);
  const updateSuccess = useAppSelector(state => state.projects.updateSuccess);

  const handleClose = () => {
    navigate('/projects' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getBusinessUnits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.releaseat = convertDateTimeToServer(values.releaseat);

    const entity = {
      ...projectsEntity,
      ...values,
      projectmanager: employees.find(it => it.id.toString() === values.projectmanager.toString()),
      businessunit: businessUnits.find(it => it.id.toString() === values.businessunit.toString()),
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          releaseat: displayDefaultDateTime(),
        }
      : {
          ...projectsEntity,
          startdate: convertDateTimeFromServer(projectsEntity.startdate),
          enddate: convertDateTimeFromServer(projectsEntity.enddate),
          createdat: convertDateTimeFromServer(projectsEntity.createdat),
          updatedat: convertDateTimeFromServer(projectsEntity.updatedat),
          releaseat: convertDateTimeFromServer(projectsEntity.releaseat),
          projectmanager: projectsEntity?.projectmanager?.id,
          businessunit: projectsEntity?.businessunit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.projects.home.createOrEditLabel" data-cy="ProjectsCreateUpdateHeading">
            Create or edit a Projects
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="projects-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="projects-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Isactive" id="projects-isactive" name="isactive" data-cy="isactive" check type="checkbox" />
              <ValidatedField label="Isdelete" id="projects-isdelete" name="isdelete" data-cy="isdelete" check type="checkbox" />
              <ValidatedField
                label="Startdate"
                id="projects-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="projects-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Colorcode"
                id="projects-colorcode"
                name="colorcode"
                data-cy="colorcode"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="projects-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updatedat"
                id="projects-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deliverymanagerid"
                id="projects-deliverymanagerid"
                name="deliverymanagerid"
                data-cy="deliverymanagerid"
                type="text"
              />
              <ValidatedField label="Architectid" id="projects-architectid" name="architectid" data-cy="architectid" type="text" />
              <ValidatedField label="Isdeleted" id="projects-isdeleted" name="isdeleted" data-cy="isdeleted" type="text" />
              <ValidatedField
                label="Approvedresources"
                id="projects-approvedresources"
                name="approvedresources"
                data-cy="approvedresources"
                type="text"
              />
              <ValidatedField
                label="Releaseat"
                id="projects-releaseat"
                name="releaseat"
                data-cy="releaseat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="projects-projectmanager"
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
              <ValidatedField id="projects-businessunit" name="businessunit" data-cy="businessunit" label="Businessunit" type="select">
                <option value="" key="0" />
                {businessUnits
                  ? businessUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projects" replace color="info">
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

export default ProjectsUpdate;
