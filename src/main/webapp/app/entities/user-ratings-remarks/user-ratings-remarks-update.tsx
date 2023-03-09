import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDesignations } from 'app/shared/model/designations.model';
import { getEntities as getDesignations } from 'app/entities/designations/designations.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { getEntities as getPerformanceCycleEmployeeRatings } from 'app/entities/performance-cycle-employee-rating/performance-cycle-employee-rating.reducer';
import { IUserRatingsRemarks } from 'app/shared/model/user-ratings-remarks.model';
import { getEntity, updateEntity, createEntity, reset } from './user-ratings-remarks.reducer';

export const UserRatingsRemarksUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const designations = useAppSelector(state => state.designations.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const performanceCycleEmployeeRatings = useAppSelector(state => state.performanceCycleEmployeeRating.entities);
  const userRatingsRemarksEntity = useAppSelector(state => state.userRatingsRemarks.entity);
  const loading = useAppSelector(state => state.userRatingsRemarks.loading);
  const updating = useAppSelector(state => state.userRatingsRemarks.updating);
  const updateSuccess = useAppSelector(state => state.userRatingsRemarks.updateSuccess);

  const handleClose = () => {
    navigate('/user-ratings-remarks' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDesignations({}));
    dispatch(getEmployees({}));
    dispatch(getPerformanceCycleEmployeeRatings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.deletedAt = convertDateTimeToServer(values.deletedAt);

    const entity = {
      ...userRatingsRemarksEntity,
      ...values,
      designationAfterPromotion: designations.find(it => it.id.toString() === values.designationAfterPromotion.toString()),
      designationAfterRedesignation: designations.find(it => it.id.toString() === values.designationAfterRedesignation.toString()),
      rater: employees.find(it => it.id.toString() === values.rater.toString()),
      pcEmployeeRating: performanceCycleEmployeeRatings.find(it => it.id.toString() === values.pcEmployeeRating.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          deletedAt: displayDefaultDateTime(),
        }
      : {
          ...userRatingsRemarksEntity,
          createdAt: convertDateTimeFromServer(userRatingsRemarksEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userRatingsRemarksEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(userRatingsRemarksEntity.deletedAt),
          designationAfterPromotion: userRatingsRemarksEntity?.designationAfterPromotion?.id,
          designationAfterRedesignation: userRatingsRemarksEntity?.designationAfterRedesignation?.id,
          rater: userRatingsRemarksEntity?.rater?.id,
          pcEmployeeRating: userRatingsRemarksEntity?.pcEmployeeRating?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.userRatingsRemarks.home.createOrEditLabel" data-cy="UserRatingsRemarksCreateUpdateHeading">
            Create or edit a User Ratings Remarks
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
                <ValidatedField name="id" required readOnly id="user-ratings-remarks-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Is Promotion"
                id="user-ratings-remarks-isPromotion"
                name="isPromotion"
                data-cy="isPromotion"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Strength"
                id="user-ratings-remarks-strength"
                name="strength"
                data-cy="strength"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Area Of Improvement"
                id="user-ratings-remarks-areaOfImprovement"
                name="areaOfImprovement"
                data-cy="areaOfImprovement"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Promotion Justification"
                id="user-ratings-remarks-promotionJustification"
                name="promotionJustification"
                data-cy="promotionJustification"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="New Grade"
                id="user-ratings-remarks-newGrade"
                name="newGrade"
                data-cy="newGrade"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Is Redesignation"
                id="user-ratings-remarks-isRedesignation"
                name="isRedesignation"
                data-cy="isRedesignation"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Recommended Salary"
                id="user-ratings-remarks-recommendedSalary"
                name="recommendedSalary"
                data-cy="recommendedSalary"
                type="text"
              />
              <ValidatedField
                label="Status"
                id="user-ratings-remarks-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="user-ratings-remarks-createdAt"
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
                id="user-ratings-remarks-updatedAt"
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
                id="user-ratings-remarks-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="user-ratings-remarks-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Other Comments"
                id="user-ratings-remarks-otherComments"
                name="otherComments"
                data-cy="otherComments"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                id="user-ratings-remarks-designationAfterPromotion"
                name="designationAfterPromotion"
                data-cy="designationAfterPromotion"
                label="Designation After Promotion"
                type="select"
              >
                <option value="" key="0" />
                {designations
                  ? designations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="user-ratings-remarks-designationAfterRedesignation"
                name="designationAfterRedesignation"
                data-cy="designationAfterRedesignation"
                label="Designation After Redesignation"
                type="select"
              >
                <option value="" key="0" />
                {designations
                  ? designations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="user-ratings-remarks-rater" name="rater" data-cy="rater" label="Rater" type="select" required>
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
              <ValidatedField
                id="user-ratings-remarks-pcEmployeeRating"
                name="pcEmployeeRating"
                data-cy="pcEmployeeRating"
                label="Pc Employee Rating"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-ratings-remarks" replace color="info">
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

export default UserRatingsRemarksUpdate;
