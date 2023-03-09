import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeProjectRatings20190826 } from 'app/shared/model/employee-project-ratings-20190826.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-project-ratings-20190826.reducer';

export const EmployeeProjectRatings20190826Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employeeProjectRatings20190826Entity = useAppSelector(state => state.employeeProjectRatings20190826.entity);
  const loading = useAppSelector(state => state.employeeProjectRatings20190826.loading);
  const updating = useAppSelector(state => state.employeeProjectRatings20190826.updating);
  const updateSuccess = useAppSelector(state => state.employeeProjectRatings20190826.updateSuccess);

  const handleClose = () => {
    navigate('/employee-project-ratings-20190826' + location.search);
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

    const entity = {
      ...employeeProjectRatings20190826Entity,
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
        }
      : {
          ...employeeProjectRatings20190826Entity,
          createdat: convertDateTimeFromServer(employeeProjectRatings20190826Entity.createdat),
          updatedat: convertDateTimeFromServer(employeeProjectRatings20190826Entity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="vendianMonoApp.employeeProjectRatings20190826.home.createOrEditLabel"
            data-cy="EmployeeProjectRatings20190826CreateUpdateHeading"
          >
            Create or edit a Employee Project Ratings 20190826
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employee-project-ratings-20190826-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="employee-project-ratings-20190826-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-project-ratings-20190826-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField
                label="Pmquality"
                id="employee-project-ratings-20190826-pmquality"
                name="pmquality"
                data-cy="pmquality"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmownership"
                id="employee-project-ratings-20190826-pmownership"
                name="pmownership"
                data-cy="pmownership"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmskill"
                id="employee-project-ratings-20190826-pmskill"
                name="pmskill"
                data-cy="pmskill"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmethics"
                id="employee-project-ratings-20190826-pmethics"
                name="pmethics"
                data-cy="pmethics"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmefficiency"
                id="employee-project-ratings-20190826-pmefficiency"
                name="pmefficiency"
                data-cy="pmefficiency"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmfreeze"
                id="employee-project-ratings-20190826-pmfreeze"
                name="pmfreeze"
                data-cy="pmfreeze"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archfreeze"
                id="employee-project-ratings-20190826-archfreeze"
                name="archfreeze"
                data-cy="archfreeze"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Pmcomment"
                id="employee-project-ratings-20190826-pmcomment"
                name="pmcomment"
                data-cy="pmcomment"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archquality"
                id="employee-project-ratings-20190826-archquality"
                name="archquality"
                data-cy="archquality"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archownership"
                id="employee-project-ratings-20190826-archownership"
                name="archownership"
                data-cy="archownership"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archskill"
                id="employee-project-ratings-20190826-archskill"
                name="archskill"
                data-cy="archskill"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archethics"
                id="employee-project-ratings-20190826-archethics"
                name="archethics"
                data-cy="archethics"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archefficiency"
                id="employee-project-ratings-20190826-archefficiency"
                name="archefficiency"
                data-cy="archefficiency"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Archcomment"
                id="employee-project-ratings-20190826-archcomment"
                name="archcomment"
                data-cy="archcomment"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Projectarchitectid"
                id="employee-project-ratings-20190826-projectarchitectid"
                name="projectarchitectid"
                data-cy="projectarchitectid"
                type="text"
              />
              <ValidatedField
                label="Projectmanagerid"
                id="employee-project-ratings-20190826-projectmanagerid"
                name="projectmanagerid"
                data-cy="projectmanagerid"
                type="text"
              />
              <ValidatedField
                label="Employeeid"
                id="employee-project-ratings-20190826-employeeid"
                name="employeeid"
                data-cy="employeeid"
                type="text"
              />
              <ValidatedField
                label="Projectcycleid"
                id="employee-project-ratings-20190826-projectcycleid"
                name="projectcycleid"
                data-cy="projectcycleid"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/employee-project-ratings-20190826"
                replace
                color="info"
              >
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

export default EmployeeProjectRatings20190826Update;
