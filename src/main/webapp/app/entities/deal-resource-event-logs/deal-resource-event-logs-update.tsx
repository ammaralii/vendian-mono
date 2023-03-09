import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { getEntities as getDealRequirementMatchingResources } from 'app/entities/deal-requirement-matching-resources/deal-requirement-matching-resources.reducer';
import { IDealResourceStatus } from 'app/shared/model/deal-resource-status.model';
import { getEntities as getDealResourceStatuses } from 'app/entities/deal-resource-status/deal-resource-status.reducer';
import { IDealResourceEventLogs } from 'app/shared/model/deal-resource-event-logs.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-resource-event-logs.reducer';

export const DealResourceEventLogsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealRequirementMatchingResources = useAppSelector(state => state.dealRequirementMatchingResources.entities);
  const dealResourceStatuses = useAppSelector(state => state.dealResourceStatus.entities);
  const dealResourceEventLogsEntity = useAppSelector(state => state.dealResourceEventLogs.entity);
  const loading = useAppSelector(state => state.dealResourceEventLogs.loading);
  const updating = useAppSelector(state => state.dealResourceEventLogs.updating);
  const updateSuccess = useAppSelector(state => state.dealResourceEventLogs.updateSuccess);

  const handleClose = () => {
    navigate('/deal-resource-event-logs' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDealRequirementMatchingResources({}));
    dispatch(getDealResourceStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);

    const entity = {
      ...dealResourceEventLogsEntity,
      ...values,
      matchingresource: dealRequirementMatchingResources.find(it => it.id.toString() === values.matchingresource.toString()),
      resourcestatus: dealResourceStatuses.find(it => it.id.toString() === values.resourcestatus.toString()),
      systemstatus: dealResourceStatuses.find(it => it.id.toString() === values.systemstatus.toString()),
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
          createdat: displayDefaultDateTime(),
        }
      : {
          ...dealResourceEventLogsEntity,
          createdat: convertDateTimeFromServer(dealResourceEventLogsEntity.createdat),
          matchingresource: dealResourceEventLogsEntity?.matchingresource?.id,
          resourcestatus: dealResourceEventLogsEntity?.resourcestatus?.id,
          systemstatus: dealResourceEventLogsEntity?.systemstatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.dealResourceEventLogs.home.createOrEditLabel" data-cy="DealResourceEventLogsCreateUpdateHeading">
            Create or edit a Deal Resource Event Logs
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
                <ValidatedField name="id" required readOnly id="deal-resource-event-logs-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Comments"
                id="deal-resource-event-logs-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 500, message: 'This field cannot be longer than 500 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="deal-resource-event-logs-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="deal-resource-event-logs-matchingresource"
                name="matchingresource"
                data-cy="matchingresource"
                label="Matchingresource"
                type="select"
              >
                <option value="" key="0" />
                {dealRequirementMatchingResources
                  ? dealRequirementMatchingResources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="deal-resource-event-logs-resourcestatus"
                name="resourcestatus"
                data-cy="resourcestatus"
                label="Resourcestatus"
                type="select"
              >
                <option value="" key="0" />
                {dealResourceStatuses
                  ? dealResourceStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.systemKey}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="deal-resource-event-logs-systemstatus"
                name="systemstatus"
                data-cy="systemstatus"
                label="Systemstatus"
                type="select"
              >
                <option value="" key="0" />
                {dealResourceStatuses
                  ? dealResourceStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.systemKey}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deal-resource-event-logs" replace color="info">
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

export default DealResourceEventLogsUpdate;
