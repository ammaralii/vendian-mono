import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotificationEvents } from 'app/shared/model/notification-events.model';
import { getEntities as getNotificationEvents } from 'app/entities/notification-events/notification-events.reducer';
import { INotificationMergeFields } from 'app/shared/model/notification-merge-fields.model';
import { getEntity, updateEntity, createEntity, reset } from './notification-merge-fields.reducer';

export const NotificationMergeFieldsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notificationEvents = useAppSelector(state => state.notificationEvents.entities);
  const notificationMergeFieldsEntity = useAppSelector(state => state.notificationMergeFields.entity);
  const loading = useAppSelector(state => state.notificationMergeFields.loading);
  const updating = useAppSelector(state => state.notificationMergeFields.updating);
  const updateSuccess = useAppSelector(state => state.notificationMergeFields.updateSuccess);

  const handleClose = () => {
    navigate('/notification-merge-fields' + location.search);
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
      ...notificationMergeFieldsEntity,
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
          ...notificationMergeFieldsEntity,
          effDate: convertDateTimeFromServer(notificationMergeFieldsEntity.effDate),
          createdAt: convertDateTimeFromServer(notificationMergeFieldsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(notificationMergeFieldsEntity.updatedAt),
          endDate: convertDateTimeFromServer(notificationMergeFieldsEntity.endDate),
          notificationEvent: notificationMergeFieldsEntity?.notificationEvent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.notificationMergeFields.home.createOrEditLabel" data-cy="NotificationMergeFieldsCreateUpdateHeading">
            Create or edit a Notification Merge Fields
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
                <ValidatedField name="id" required readOnly id="notification-merge-fields-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Field"
                id="notification-merge-fields-field"
                name="field"
                data-cy="field"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Eff Date"
                id="notification-merge-fields-effDate"
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
                id="notification-merge-fields-createdAt"
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
                id="notification-merge-fields-updatedAt"
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
                id="notification-merge-fields-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="notification-merge-fields-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="notification-merge-fields-notificationEvent"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification-merge-fields" replace color="info">
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

export default NotificationMergeFieldsUpdate;
