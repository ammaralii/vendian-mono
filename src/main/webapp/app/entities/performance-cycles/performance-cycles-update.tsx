import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { getEntities as getProjectCycles } from 'app/entities/project-cycles/project-cycles.reducer';
import { IRatings } from 'app/shared/model/ratings.model';
import { getEntities as getRatings } from 'app/entities/ratings/ratings.reducer';
import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';
import { getEntity, updateEntity, createEntity, reset } from './performance-cycles.reducer';

export const PerformanceCyclesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projectCycles = useAppSelector(state => state.projectCycles.entities);
  const ratings = useAppSelector(state => state.ratings.entities);
  const performanceCyclesEntity = useAppSelector(state => state.performanceCycles.entity);
  const loading = useAppSelector(state => state.performanceCycles.loading);
  const updating = useAppSelector(state => state.performanceCycles.updating);
  const updateSuccess = useAppSelector(state => state.performanceCycles.updateSuccess);

  const handleClose = () => {
    navigate('/performance-cycles' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjectCycles({}));
    dispatch(getRatings({}));
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
      ...performanceCyclesEntity,
      ...values,
      projectcycles: mapIdList(values.projectcycles),
      employeeratings: mapIdList(values.employeeratings),
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
          ...performanceCyclesEntity,
          startdate: convertDateTimeFromServer(performanceCyclesEntity.startdate),
          enddate: convertDateTimeFromServer(performanceCyclesEntity.enddate),
          createdat: convertDateTimeFromServer(performanceCyclesEntity.createdat),
          updatedat: convertDateTimeFromServer(performanceCyclesEntity.updatedat),
          duedate: convertDateTimeFromServer(performanceCyclesEntity.duedate),
          initiationdate: convertDateTimeFromServer(performanceCyclesEntity.initiationdate),
          projectcycles: performanceCyclesEntity?.projectcycles?.map(e => e.id.toString()),
          employeeratings: performanceCyclesEntity?.employeeratings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.performanceCycles.home.createOrEditLabel" data-cy="PerformanceCyclesCreateUpdateHeading">
            Create or edit a Performance Cycles
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
                <ValidatedField name="id" required readOnly id="performance-cycles-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Month" id="performance-cycles-month" name="month" data-cy="month" check type="checkbox" />
              <ValidatedField label="Year" id="performance-cycles-year" name="year" data-cy="year" check type="checkbox" />
              <ValidatedField
                label="Totalresources"
                id="performance-cycles-totalresources"
                name="totalresources"
                data-cy="totalresources"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Pmreviewed"
                id="performance-cycles-pmreviewed"
                name="pmreviewed"
                data-cy="pmreviewed"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Archreviewed"
                id="performance-cycles-archreviewed"
                name="archreviewed"
                data-cy="archreviewed"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Startdate"
                id="performance-cycles-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="performance-cycles-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Isactive" id="performance-cycles-isactive" name="isactive" data-cy="isactive" check type="checkbox" />
              <ValidatedField
                label="Createdat"
                id="performance-cycles-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="performance-cycles-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Projectcount"
                id="performance-cycles-projectcount"
                name="projectcount"
                data-cy="projectcount"
                check
                type="checkbox"
              />
              <ValidatedField label="Criteria" id="performance-cycles-criteria" name="criteria" data-cy="criteria" type="text" />
              <ValidatedField
                label="Notificationsent"
                id="performance-cycles-notificationsent"
                name="notificationsent"
                data-cy="notificationsent"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Duedate"
                id="performance-cycles-duedate"
                name="duedate"
                data-cy="duedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Initiationdate"
                id="performance-cycles-initiationdate"
                name="initiationdate"
                data-cy="initiationdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Projectcycle"
                id="performance-cycles-projectcycle"
                data-cy="projectcycle"
                type="select"
                multiple
                name="projectcycles"
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
              <ValidatedField
                label="Employeerating"
                id="performance-cycles-employeerating"
                data-cy="employeerating"
                type="select"
                multiple
                name="employeeratings"
              >
                <option value="" key="0" />
                {ratings
                  ? ratings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/performance-cycles" replace color="info">
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

export default PerformanceCyclesUpdate;
