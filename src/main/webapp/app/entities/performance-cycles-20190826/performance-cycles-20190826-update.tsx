import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPerformanceCycles20190826 } from 'app/shared/model/performance-cycles-20190826.model';
import { getEntity, updateEntity, createEntity, reset } from './performance-cycles-20190826.reducer';

export const PerformanceCycles20190826Update = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const performanceCycles20190826Entity = useAppSelector(state => state.performanceCycles20190826.entity);
  const loading = useAppSelector(state => state.performanceCycles20190826.loading);
  const updating = useAppSelector(state => state.performanceCycles20190826.updating);
  const updateSuccess = useAppSelector(state => state.performanceCycles20190826.updateSuccess);

  const handleClose = () => {
    navigate('/performance-cycles-20190826' + location.search);
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
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.duedate = convertDateTimeToServer(values.duedate);
    values.initiationdate = convertDateTimeToServer(values.initiationdate);

    const entity = {
      ...performanceCycles20190826Entity,
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          duedate: displayDefaultDateTime(),
          initiationdate: displayDefaultDateTime(),
        }
      : {
          ...performanceCycles20190826Entity,
          startdate: convertDateTimeFromServer(performanceCycles20190826Entity.startdate),
          enddate: convertDateTimeFromServer(performanceCycles20190826Entity.enddate),
          createdat: convertDateTimeFromServer(performanceCycles20190826Entity.createdat),
          updatedat: convertDateTimeFromServer(performanceCycles20190826Entity.updatedat),
          duedate: convertDateTimeFromServer(performanceCycles20190826Entity.duedate),
          initiationdate: convertDateTimeFromServer(performanceCycles20190826Entity.initiationdate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.performanceCycles20190826.home.createOrEditLabel" data-cy="PerformanceCycles20190826CreateUpdateHeading">
            Create or edit a Performance Cycles 20190826
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
                <ValidatedField name="id" required readOnly id="performance-cycles-20190826-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Month" id="performance-cycles-20190826-month" name="month" data-cy="month" check type="checkbox" />
              <ValidatedField label="Year" id="performance-cycles-20190826-year" name="year" data-cy="year" check type="checkbox" />
              <ValidatedField
                label="Totalresources"
                id="performance-cycles-20190826-totalresources"
                name="totalresources"
                data-cy="totalresources"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Pmreviewed"
                id="performance-cycles-20190826-pmreviewed"
                name="pmreviewed"
                data-cy="pmreviewed"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Archreviewed"
                id="performance-cycles-20190826-archreviewed"
                name="archreviewed"
                data-cy="archreviewed"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Startdate"
                id="performance-cycles-20190826-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="performance-cycles-20190826-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Isactive"
                id="performance-cycles-20190826-isactive"
                name="isactive"
                data-cy="isactive"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Createdat"
                id="performance-cycles-20190826-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="performance-cycles-20190826-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Projectcount"
                id="performance-cycles-20190826-projectcount"
                name="projectcount"
                data-cy="projectcount"
                check
                type="checkbox"
              />
              <ValidatedField label="Criteria" id="performance-cycles-20190826-criteria" name="criteria" data-cy="criteria" type="text" />
              <ValidatedField
                label="Notificationsent"
                id="performance-cycles-20190826-notificationsent"
                name="notificationsent"
                data-cy="notificationsent"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Duedate"
                id="performance-cycles-20190826-duedate"
                name="duedate"
                data-cy="duedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Initiationdate"
                id="performance-cycles-20190826-initiationdate"
                name="initiationdate"
                data-cy="initiationdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/performance-cycles-20190826" replace color="info">
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

export default PerformanceCycles20190826Update;
