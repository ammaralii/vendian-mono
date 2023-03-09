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
import { getEntity, updateEntity, createEntity, reset } from './feedback-requests.reducer';

export const FeedbackRequestsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employees.entities);
  const feedbackRequestsEntity = useAppSelector(state => state.feedbackRequests.entity);
  const loading = useAppSelector(state => state.feedbackRequests.loading);
  const updating = useAppSelector(state => state.feedbackRequests.updating);
  const updateSuccess = useAppSelector(state => state.feedbackRequests.updateSuccess);

  const handleClose = () => {
    navigate('/feedback-requests' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.approvedat = convertDateTimeToServer(values.approvedat);
    values.expiredat = convertDateTimeToServer(values.expiredat);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...feedbackRequestsEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
      linemanager: employees.find(it => it.id.toString() === values.linemanager.toString()),
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
          approvedat: displayDefaultDateTime(),
          expiredat: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...feedbackRequestsEntity,
          approvedat: convertDateTimeFromServer(feedbackRequestsEntity.approvedat),
          expiredat: convertDateTimeFromServer(feedbackRequestsEntity.expiredat),
          createdat: convertDateTimeFromServer(feedbackRequestsEntity.createdat),
          updatedat: convertDateTimeFromServer(feedbackRequestsEntity.updatedat),
          employee: feedbackRequestsEntity?.employee?.id,
          linemanager: feedbackRequestsEntity?.linemanager?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.feedbackRequests.home.createOrEditLabel" data-cy="FeedbackRequestsCreateUpdateHeading">
            Create or edit a Feedback Requests
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
                <ValidatedField name="id" required readOnly id="feedback-requests-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Status" id="feedback-requests-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Isreportavailable"
                id="feedback-requests-isreportavailable"
                name="isreportavailable"
                data-cy="isreportavailable"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Reportpath"
                id="feedback-requests-reportpath"
                name="reportpath"
                data-cy="reportpath"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Approvedat"
                id="feedback-requests-approvedat"
                name="approvedat"
                data-cy="approvedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Expiredat"
                id="feedback-requests-expiredat"
                name="expiredat"
                data-cy="expiredat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="feedback-requests-createdat"
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
                id="feedback-requests-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="feedback-requests-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="feedback-requests-linemanager" name="linemanager" data-cy="linemanager" label="Linemanager" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/feedback-requests" replace color="info">
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

export default FeedbackRequestsUpdate;
