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
import { IFeedbackRequests } from 'app/shared/model/feedback-requests.model';
import { getEntities as getFeedbackRequests } from 'app/entities/feedback-requests/feedback-requests.reducer';
import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';
import { getEntity, updateEntity, createEntity, reset } from './feedback-respondents.reducer';

export const FeedbackRespondentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const feedbackRequests = useAppSelector(state => state.feedbackRequests.entities);
  const feedbackRespondentsEntity = useAppSelector(state => state.feedbackRespondents.entity);
  const loading = useAppSelector(state => state.feedbackRespondents.loading);
  const updating = useAppSelector(state => state.feedbackRespondents.updating);
  const updateSuccess = useAppSelector(state => state.feedbackRespondents.updateSuccess);

  const handleClose = () => {
    navigate('/feedback-respondents' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getFeedbackRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.respondedat = convertDateTimeToServer(values.respondedat);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...feedbackRespondentsEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      request: feedbackRequests.find(it => it.id.toString() === values.request.toString()),
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
          respondedat: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...feedbackRespondentsEntity,
          respondedat: convertDateTimeFromServer(feedbackRespondentsEntity.respondedat),
          createdat: convertDateTimeFromServer(feedbackRespondentsEntity.createdat),
          updatedat: convertDateTimeFromServer(feedbackRespondentsEntity.updatedat),
          employee: feedbackRespondentsEntity?.employee?.id,
          request: feedbackRespondentsEntity?.request?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.feedbackRespondents.home.createOrEditLabel" data-cy="FeedbackRespondentsCreateUpdateHeading">
            Create or edit a Feedback Respondents
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
                <ValidatedField name="id" required readOnly id="feedback-respondents-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Category"
                id="feedback-respondents-category"
                name="category"
                data-cy="category"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Hasresponded"
                id="feedback-respondents-hasresponded"
                name="hasresponded"
                data-cy="hasresponded"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Respondedat"
                id="feedback-respondents-respondedat"
                name="respondedat"
                data-cy="respondedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="feedback-respondents-createdat"
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
                id="feedback-respondents-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="feedback-respondents-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="feedback-respondents-request" name="request" data-cy="request" label="Request" type="select">
                <option value="" key="0" />
                {feedbackRequests
                  ? feedbackRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/feedback-respondents" replace color="info">
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

export default FeedbackRespondentsUpdate;
