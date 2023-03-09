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
import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';
import { getEntities as getPerformanceCycles } from 'app/entities/performance-cycles/performance-cycles.reducer';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { getEntities as getProjectCycles } from 'app/entities/project-cycles/project-cycles.reducer';
import { IRatings } from 'app/shared/model/ratings.model';
import { getEntity, updateEntity, createEntity, reset } from './ratings.reducer';

export const RatingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const performanceCycles = useAppSelector(state => state.performanceCycles.entities);
  const projectCycles = useAppSelector(state => state.projectCycles.entities);
  const ratingsEntity = useAppSelector(state => state.ratings.entity);
  const loading = useAppSelector(state => state.ratings.loading);
  const updating = useAppSelector(state => state.ratings.updating);
  const updateSuccess = useAppSelector(state => state.ratings.updateSuccess);

  const handleClose = () => {
    navigate('/ratings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getPerformanceCycles({}));
    dispatch(getProjectCycles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.duedate = convertDateTimeToServer(values.duedate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...ratingsEntity,
      ...values,
      rater: employees.find(it => it.id.toString() === values.rater.toString()),
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
          duedate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...ratingsEntity,
          duedate: convertDateTimeFromServer(ratingsEntity.duedate),
          createdat: convertDateTimeFromServer(ratingsEntity.createdat),
          updatedat: convertDateTimeFromServer(ratingsEntity.updatedat),
          deletedat: convertDateTimeFromServer(ratingsEntity.deletedat),
          rater: ratingsEntity?.rater?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.ratings.home.createOrEditLabel" data-cy="RatingsCreateUpdateHeading">
            Create or edit a Ratings
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ratings-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Rateeid" id="ratings-rateeid" name="rateeid" data-cy="rateeid" type="text" />
              <ValidatedField
                label="Rateetype"
                id="ratings-rateetype"
                name="rateetype"
                data-cy="rateetype"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Duedate"
                id="ratings-duedate"
                name="duedate"
                data-cy="duedate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField label="Freeze" id="ratings-freeze" name="freeze" data-cy="freeze" openActionLabel="Open" />
              <ValidatedField
                label="Createdat"
                id="ratings-createdat"
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
                id="ratings-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deletedat"
                id="ratings-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="ratings-rater" name="rater" data-cy="rater" label="Rater" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ratings" replace color="info">
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

export default RatingsUpdate;
