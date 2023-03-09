import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotificationEvents } from 'app/shared/model/notification-events.model';
import { getEntities as getNotificationEvents } from 'app/entities/notification-events/notification-events.reducer';
import { INotificationTemplates } from 'app/shared/model/notification-templates.model';
import { getEntity, updateEntity, createEntity, reset } from './notification-templates.reducer';

export const NotificationTemplatesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notificationEvents = useAppSelector(state => state.notificationEvents.entities);
  const notificationTemplatesEntity = useAppSelector(state => state.notificationTemplates.entity);
  const loading = useAppSelector(state => state.notificationTemplates.loading);
  const updating = useAppSelector(state => state.notificationTemplates.updating);
  const updateSuccess = useAppSelector(state => state.notificationTemplates.updateSuccess);

  const handleClose = () => {
    navigate('/notification-templates' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNotificationEvents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.effDate = convertDateTimeToServer(values.effDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...notificationTemplatesEntity,
      ...values,
      notificationEvent: notificationEvents.find(it => it.id.toString() === values.notificationEvent.toString()),
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
          effDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...notificationTemplatesEntity,
          effDate: convertDateTimeFromServer(notificationTemplatesEntity.effDate),
          createdAt: convertDateTimeFromServer(notificationTemplatesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(notificationTemplatesEntity.updatedAt),
          endDate: convertDateTimeFromServer(notificationTemplatesEntity.endDate),
          notificationEvent: notificationTemplatesEntity?.notificationEvent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.notificationTemplates.home.createOrEditLabel" data-cy="NotificationTemplatesCreateUpdateHeading">
            Create or edit a Notification Templates
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
                <ValidatedField name="id" required readOnly id="notification-templates-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Name"
                id="notification-templates-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Type"
                id="notification-templates-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 5, message: 'This field cannot be longer than 5 characters.' },
                }}
              />
              <ValidatedField
                label="Subject"
                id="notification-templates-subject"
                name="subject"
                data-cy="subject"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedBlobField
                label="Template"
                id="notification-templates-template"
                name="template"
                data-cy="template"
                openActionLabel="Open"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="notification-templates-effDate"
                name="effDate"
                data-cy="effDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Created At"
                id="notification-templates-createdAt"
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
                id="notification-templates-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="End Date"
                id="notification-templates-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="notification-templates-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="notification-templates-notificationEvent"
                name="notificationEvent"
                data-cy="notificationEvent"
                label="Notification Event"
                type="select"
                required
              >
                <option value="" key="0" />
                {notificationEvents
                  ? notificationEvents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification-templates" replace color="info">
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

export default NotificationTemplatesUpdate;
