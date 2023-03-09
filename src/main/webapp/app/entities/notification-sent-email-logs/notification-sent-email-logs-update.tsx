import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotificationTemplates } from 'app/shared/model/notification-templates.model';
import { getEntities as getNotificationTemplates } from 'app/entities/notification-templates/notification-templates.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { INotificationSentEmailLogs } from 'app/shared/model/notification-sent-email-logs.model';
import { getEntity, updateEntity, createEntity, reset } from './notification-sent-email-logs.reducer';

export const NotificationSentEmailLogsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notificationTemplates = useAppSelector(state => state.notificationTemplates.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const notificationSentEmailLogsEntity = useAppSelector(state => state.notificationSentEmailLogs.entity);
  const loading = useAppSelector(state => state.notificationSentEmailLogs.loading);
  const updating = useAppSelector(state => state.notificationSentEmailLogs.updating);
  const updateSuccess = useAppSelector(state => state.notificationSentEmailLogs.updateSuccess);

  const handleClose = () => {
    navigate('/notification-sent-email-logs' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNotificationTemplates({}));
    dispatch(getEmployees({}));
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
      ...notificationSentEmailLogsEntity,
      ...values,
      notificationTemplate: notificationTemplates.find(it => it.id.toString() === values.notificationTemplate.toString()),
      recipient: employees.find(it => it.id.toString() === values.recipient.toString()),
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
          ...notificationSentEmailLogsEntity,
          createdAt: convertDateTimeFromServer(notificationSentEmailLogsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(notificationSentEmailLogsEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(notificationSentEmailLogsEntity.deletedAt),
          notificationTemplate: notificationSentEmailLogsEntity?.notificationTemplate?.id,
          recipient: notificationSentEmailLogsEntity?.recipient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.notificationSentEmailLogs.home.createOrEditLabel" data-cy="NotificationSentEmailLogsCreateUpdateHeading">
            Create or edit a Notification Sent Email Logs
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
                <ValidatedField name="id" required readOnly id="notification-sent-email-logs-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Email"
                id="notification-sent-email-logs-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="notification-sent-email-logs-createdAt"
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
                id="notification-sent-email-logs-updatedAt"
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
                id="notification-sent-email-logs-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="notification-sent-email-logs-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="notification-sent-email-logs-notificationTemplate"
                name="notificationTemplate"
                data-cy="notificationTemplate"
                label="Notification Template"
                type="select"
                required
              >
                <option value="" key="0" />
                {notificationTemplates
                  ? notificationTemplates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="notification-sent-email-logs-recipient"
                name="recipient"
                data-cy="recipient"
                label="Recipient"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/notification-sent-email-logs"
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

export default NotificationSentEmailLogsUpdate;
