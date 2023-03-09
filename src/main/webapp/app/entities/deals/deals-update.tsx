import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeals } from 'app/shared/model/deals.model';
import { getEntity, updateEntity, createEntity, reset } from './deals.reducer';

export const DealsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealsEntity = useAppSelector(state => state.deals.entity);
  const loading = useAppSelector(state => state.deals.loading);
  const updating = useAppSelector(state => state.deals.updating);
  const updateSuccess = useAppSelector(state => state.deals.updateSuccess);

  const handleClose = () => {
    navigate('/deals' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.expectedstartdate = convertDateTimeToServer(values.expectedstartdate);
    values.closedat = convertDateTimeToServer(values.closedat);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.deletedat = convertDateTimeToServer(values.deletedat);

    const entity = {
      ...dealsEntity,
      ...values,
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
          expectedstartdate: displayDefaultDateTime(),
          closedat: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          deletedat: displayDefaultDateTime(),
        }
      : {
          ...dealsEntity,
          expectedstartdate: convertDateTimeFromServer(dealsEntity.expectedstartdate),
          closedat: convertDateTimeFromServer(dealsEntity.closedat),
          createdat: convertDateTimeFromServer(dealsEntity.createdat),
          updatedat: convertDateTimeFromServer(dealsEntity.updatedat),
          deletedat: convertDateTimeFromServer(dealsEntity.deletedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.deals.home.createOrEditLabel" data-cy="DealsCreateUpdateHeading">
            Create or edit a Deals
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="deals-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Dealnumber"
                id="deals-dealnumber"
                name="dealnumber"
                data-cy="dealnumber"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Dealname"
                id="deals-dealname"
                name="dealname"
                data-cy="dealname"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Businessunit"
                id="deals-businessunit"
                name="businessunit"
                data-cy="businessunit"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Clientname"
                id="deals-clientname"
                name="clientname"
                data-cy="clientname"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Dealowner"
                id="deals-dealowner"
                name="dealowner"
                data-cy="dealowner"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Proposaltype"
                id="deals-proposaltype"
                name="proposaltype"
                data-cy="proposaltype"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Projectid" id="deals-projectid" name="projectid" data-cy="projectid" type="text" />
              <ValidatedField
                label="Expectedstartdate"
                id="deals-expectedstartdate"
                name="expectedstartdate"
                data-cy="expectedstartdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Stage"
                id="deals-stage"
                name="stage"
                data-cy="stage"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Probability"
                id="deals-probability"
                name="probability"
                data-cy="probability"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Projectduration"
                id="deals-projectduration"
                name="projectduration"
                data-cy="projectduration"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Type"
                id="deals-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 9, message: 'This field cannot be longer than 9 characters.' },
                }}
              />
              <ValidatedField
                label="Status"
                id="deals-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Closedat"
                id="deals-closedat"
                name="closedat"
                data-cy="closedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="deals-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deals-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Deletedat"
                id="deals-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Resourcingenteredinvendians"
                id="deals-resourcingenteredinvendians"
                name="resourcingenteredinvendians"
                data-cy="resourcingenteredinvendians"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deals" replace color="info">
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

export default DealsUpdate;
