import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDealRequirements } from 'app/shared/model/deal-requirements.model';
import { getEntities as getDealRequirements } from 'app/entities/deal-requirements/deal-requirements.reducer';
import { IDealResources } from 'app/shared/model/deal-resources.model';
import { getEntities as getDealResources } from 'app/entities/deal-resources/deal-resources.reducer';
import { IDealResourceStatus } from 'app/shared/model/deal-resource-status.model';
import { getEntities as getDealResourceStatuses } from 'app/entities/deal-resource-status/deal-resource-status.reducer';
import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-requirement-matching-resources.reducer';

export const DealRequirementMatchingResourcesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealRequirements = useAppSelector(state => state.dealRequirements.entities);
  const dealResources = useAppSelector(state => state.dealResources.entities);
  const dealResourceStatuses = useAppSelector(state => state.dealResourceStatus.entities);
  const dealRequirementMatchingResourcesEntity = useAppSelector(state => state.dealRequirementMatchingResources.entity);
  const loading = useAppSelector(state => state.dealRequirementMatchingResources.loading);
  const updating = useAppSelector(state => state.dealRequirementMatchingResources.updating);
  const updateSuccess = useAppSelector(state => state.dealRequirementMatchingResources.updateSuccess);

  const handleClose = () => {
    navigate('/deal-requirement-matching-resources' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDealRequirements({}));
    dispatch(getDealResources({}));
    dispatch(getDealResourceStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...dealRequirementMatchingResourcesEntity,
      ...values,
      dealrequirement: dealRequirements.find(it => it.id.toString() === values.dealrequirement.toString()),
      resource: dealResources.find(it => it.id.toString() === values.resource.toString()),
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
          updatedat: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...dealRequirementMatchingResourcesEntity,
          createdat: convertDateTimeFromServer(dealRequirementMatchingResourcesEntity.createdat),
          updatedat: convertDateTimeFromServer(dealRequirementMatchingResourcesEntity.updatedat),
          deletedat: convertDateTimeFromServer(dealRequirementMatchingResourcesEntity.deletedat),
          dealrequirement: dealRequirementMatchingResourcesEntity?.dealrequirement?.id,
          resource: dealRequirementMatchingResourcesEntity?.resource?.id,
          resourcestatus: dealRequirementMatchingResourcesEntity?.resourcestatus?.id,
          systemstatus: dealRequirementMatchingResourcesEntity?.systemstatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="vendianMonoApp.dealRequirementMatchingResources.home.createOrEditLabel"
            data-cy="DealRequirementMatchingResourcesCreateUpdateHeading"
          >
            Create or edit a Deal Requirement Matching Resources
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="deal-requirement-matching-resources-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Comments"
                id="deal-requirement-matching-resources-comments"
                name="comments"
                data-cy="comments"
                type="text"
                validate={{
                  maxLength: { value: 500, message: 'This field cannot be longer than 500 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="deal-requirement-matching-resources-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deal-requirement-matching-resources-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Deletedat"
                id="deal-requirement-matching-resources-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="deal-requirement-matching-resources-dealrequirement"
                name="dealrequirement"
                data-cy="dealrequirement"
                label="Dealrequirement"
                type="select"
              >
                <option value="" key="0" />
                {dealRequirements
                  ? dealRequirements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="deal-requirement-matching-resources-resource"
                name="resource"
                data-cy="resource"
                label="Resource"
                type="select"
              >
                <option value="" key="0" />
                {dealResources
                  ? dealResources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="deal-requirement-matching-resources-resourcestatus"
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
                id="deal-requirement-matching-resources-systemstatus"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/deal-requirement-matching-resources"
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

export default DealRequirementMatchingResourcesUpdate;
