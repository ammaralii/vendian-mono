import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectCycles20190826 } from 'app/shared/model/project-cycles-20190826.model';
import { getEntity, updateEntity, createEntity, reset } from './project-cycles-20190826.reducer';

export const ProjectCycles20190826Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projectCycles20190826Entity = useAppSelector(state => state.projectCycles20190826.entity);
  const loading = useAppSelector(state => state.projectCycles20190826.loading);
  const updating = useAppSelector(state => state.projectCycles20190826.updating);
  const updateSuccess = useAppSelector(state => state.projectCycles20190826.updateSuccess);

  const handleClose = () => {
    navigate('/project-cycles-20190826' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.allowedafterduedateat = convertDateTimeToServer(values.allowedafterduedateat);
    values.duedate = convertDateTimeToServer(values.duedate);

    const entity = {
      ...projectCycles20190826Entity,
      ...values,
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
          allowedafterduedateat: displayDefaultDateTime(),
          duedate: displayDefaultDateTime(),
        }
      : {
          ...projectCycles20190826Entity,
          createdat: convertDateTimeFromServer(projectCycles20190826Entity.createdat),
          updatedat: convertDateTimeFromServer(projectCycles20190826Entity.updatedat),
          allowedafterduedateat: convertDateTimeFromServer(projectCycles20190826Entity.allowedafterduedateat),
          duedate: convertDateTimeFromServer(projectCycles20190826Entity.duedate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.projectCycles20190826.home.createOrEditLabel" data-cy="ProjectCycles20190826CreateUpdateHeading">
            Create or edit a Project Cycles 20190826
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
                <ValidatedField name="id" required readOnly id="project-cycles-20190826-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Isactive"
                id="project-cycles-20190826-isactive"
                name="isactive"
                data-cy="isactive"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Createdat"
                id="project-cycles-20190826-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="project-cycles-20190826-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Performancecycleid"
                id="project-cycles-20190826-performancecycleid"
                name="performancecycleid"
                data-cy="performancecycleid"
                type="text"
              />
              <ValidatedField label="Projectid" id="project-cycles-20190826-projectid" name="projectid" data-cy="projectid" type="text" />
              <ValidatedField
                label="Allowedafterduedateby"
                id="project-cycles-20190826-allowedafterduedateby"
                name="allowedafterduedateby"
                data-cy="allowedafterduedateby"
                type="text"
              />
              <ValidatedField
                label="Allowedafterduedateat"
                id="project-cycles-20190826-allowedafterduedateat"
                name="allowedafterduedateat"
                data-cy="allowedafterduedateat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Duedate"
                id="project-cycles-20190826-duedate"
                name="duedate"
                data-cy="duedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project-cycles-20190826" replace color="info">
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

export default ProjectCycles20190826Update;
