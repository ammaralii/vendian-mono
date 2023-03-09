import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPcStatuses } from 'app/shared/model/pc-statuses.model';
import { getEntities as getPcStatuses } from 'app/entities/pc-statuses/pc-statuses.reducer';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { getEntities as getPerformanceCycleEmployeeRatings } from 'app/entities/performance-cycle-employee-rating/performance-cycle-employee-rating.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { getEntity, updateEntity, createEntity, reset } from './pc-ratings.reducer';

export const PcRatingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pcStatuses = useAppSelector(state => state.pcStatuses.entities);
  const performanceCycleEmployeeRatings = useAppSelector(state => state.performanceCycleEmployeeRating.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const pcRatingsEntity = useAppSelector(state => state.pcRatings.entity);
  const loading = useAppSelector(state => state.pcRatings.loading);
  const updating = useAppSelector(state => state.pcRatings.updating);
  const updateSuccess = useAppSelector(state => state.pcRatings.updateSuccess);

  const handleClose = () => {
    navigate('/pc-ratings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPcStatuses({}));
    dispatch(getPerformanceCycleEmployeeRatings({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.stausDate = convertDateTimeToServer(values.stausDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...pcRatingsEntity,
      ...values,
      status: pcStatuses.find(it => it.id.toString() === values.status.toString()),
      pcemployeerating: performanceCycleEmployeeRatings.find(it => it.id.toString() === values.pcemployeerating.toString()),
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
          stausDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...pcRatingsEntity,
          stausDate: convertDateTimeFromServer(pcRatingsEntity.stausDate),
          createdAt: convertDateTimeFromServer(pcRatingsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(pcRatingsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(pcRatingsEntity.deletedAt),
          status: pcRatingsEntity?.status?.id,
          pcemployeerating: pcRatingsEntity?.pcemployeerating?.id,
          employee: pcRatingsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.pcRatings.home.createOrEditLabel" data-cy="PcRatingsCreateUpdateHeading">
            Create or edit a Pc Ratings
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="pc-ratings-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedBlobField label="Rating" id="pc-ratings-rating" name="rating" data-cy="rating" openActionLabel="Open" />
              <ValidatedBlobField label="Comment" id="pc-ratings-comment" name="comment" data-cy="comment" openActionLabel="Open" />
              <ValidatedField
                label="Staus Date"
                id="pc-ratings-stausDate"
                name="stausDate"
                data-cy="stausDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Due Date" id="pc-ratings-dueDate" name="dueDate" data-cy="dueDate" type="date" />
              <ValidatedField label="Freeze" id="pc-ratings-freeze" name="freeze" data-cy="freeze" check type="checkbox" />
              <ValidatedField
                label="Include In Final Rating"
                id="pc-ratings-includeInFinalRating"
                name="includeInFinalRating"
                data-cy="includeInFinalRating"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Created At"
                id="pc-ratings-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updated At"
                id="pc-ratings-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Deleted At"
                id="pc-ratings-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="pc-ratings-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="pc-ratings-status" name="status" data-cy="status" label="Status" type="select">
                <option value="" key="0" />
                {pcStatuses
                  ? pcStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="pc-ratings-pcemployeerating"
                name="pcemployeerating"
                data-cy="pcemployeerating"
                label="Pcemployeerating"
                type="select"
                required
              >
                <option value="" key="0" />
                {performanceCycleEmployeeRatings
                  ? performanceCycleEmployeeRatings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="pc-ratings-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pc-ratings" replace color="info">
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

export default PcRatingsUpdate;
