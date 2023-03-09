import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeals } from 'app/shared/model/deals.model';
import { getEntities as getDeals } from 'app/entities/deals/deals.reducer';
import { IDealRequirements } from 'app/shared/model/deal-requirements.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-requirements.reducer';

export const DealRequirementsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const deals = useAppSelector(state => state.deals.entities);
  const dealRequirementsEntity = useAppSelector(state => state.dealRequirements.entity);
  const loading = useAppSelector(state => state.dealRequirements.loading);
  const updating = useAppSelector(state => state.dealRequirements.updating);
  const updateSuccess = useAppSelector(state => state.dealRequirements.updateSuccess);

  const handleClose = () => {
    navigate('/deal-requirements' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDeals({}));
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
      ...dealRequirementsEntity,
      ...values,
      deal: deals.find(it => it.id.toString() === values.deal.toString()),
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
          ...dealRequirementsEntity,
          createdat: convertDateTimeFromServer(dealRequirementsEntity.createdat),
          updatedat: convertDateTimeFromServer(dealRequirementsEntity.updatedat),
          deletedat: convertDateTimeFromServer(dealRequirementsEntity.deletedat),
          deal: dealRequirementsEntity?.deal?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.dealRequirements.home.createOrEditLabel" data-cy="DealRequirementsCreateUpdateHeading">
            Create or edit a Deal Requirements
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
                <ValidatedField name="id" required readOnly id="deal-requirements-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Dealreqidentifier"
                id="deal-requirements-dealreqidentifier"
                name="dealreqidentifier"
                data-cy="dealreqidentifier"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Competencyname"
                id="deal-requirements-competencyname"
                name="competencyname"
                data-cy="competencyname"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Skills" id="deal-requirements-skills" name="skills" data-cy="skills" type="text" />
              <ValidatedField
                label="Resourcerequired"
                id="deal-requirements-resourcerequired"
                name="resourcerequired"
                data-cy="resourcerequired"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Minexperiencelevel"
                id="deal-requirements-minexperiencelevel"
                name="minexperiencelevel"
                data-cy="minexperiencelevel"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Maxexperiencelevel"
                id="deal-requirements-maxexperiencelevel"
                name="maxexperiencelevel"
                data-cy="maxexperiencelevel"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="deal-requirements-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deal-requirements-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Deletedat"
                id="deal-requirements-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="deal-requirements-deal" name="deal" data-cy="deal" label="Deal" type="select">
                <option value="" key="0" />
                {deals
                  ? deals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deal-requirements" replace color="info">
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

export default DealRequirementsUpdate;
