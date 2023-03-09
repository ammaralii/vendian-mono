import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { IPcRatingAttributes } from 'app/shared/model/pc-rating-attributes.model';
import { getEntities as getPcRatingAttributes } from 'app/entities/pc-rating-attributes/pc-rating-attributes.reducer';
import { IRatingAttributeMappings } from 'app/shared/model/rating-attribute-mappings.model';
import { getEntity, updateEntity, createEntity, reset } from './rating-attribute-mappings.reducer';

export const RatingAttributeMappingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributes = useAppSelector(state => state.attributes.entities);
  const pcRatingAttributes = useAppSelector(state => state.pcRatingAttributes.entities);
  const ratingAttributeMappingsEntity = useAppSelector(state => state.ratingAttributeMappings.entity);
  const loading = useAppSelector(state => state.ratingAttributeMappings.loading);
  const updating = useAppSelector(state => state.ratingAttributeMappings.updating);
  const updateSuccess = useAppSelector(state => state.ratingAttributeMappings.updateSuccess);

  const handleClose = () => {
    navigate('/rating-attribute-mappings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAttributes({}));
    dispatch(getPcRatingAttributes({}));
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
      ...ratingAttributeMappingsEntity,
      ...values,
      attribute: attributes.find(it => it.id.toString() === values.attribute.toString()),
      ratingAttribute: pcRatingAttributes.find(it => it.id.toString() === values.ratingAttribute.toString()),
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
          ...ratingAttributeMappingsEntity,
          effDate: convertDateTimeFromServer(ratingAttributeMappingsEntity.effDate),
          createdAt: convertDateTimeFromServer(ratingAttributeMappingsEntity.createdAt),
          updatedAt: convertDateTimeFromServer(ratingAttributeMappingsEntity.updatedAt),
          endDate: convertDateTimeFromServer(ratingAttributeMappingsEntity.endDate),
          attribute: ratingAttributeMappingsEntity?.attribute?.id,
          ratingAttribute: ratingAttributeMappingsEntity?.ratingAttribute?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.ratingAttributeMappings.home.createOrEditLabel" data-cy="RatingAttributeMappingsCreateUpdateHeading">
            Create or edit a Rating Attribute Mappings
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
                <ValidatedField name="id" required readOnly id="rating-attribute-mappings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Eff Date"
                id="rating-attribute-mappings-effDate"
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
                id="rating-attribute-mappings-createdAt"
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
                id="rating-attribute-mappings-updatedAt"
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
                id="rating-attribute-mappings-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="rating-attribute-mappings-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="rating-attribute-mappings-attribute"
                name="attribute"
                data-cy="attribute"
                label="Attribute"
                type="select"
                required
              >
                <option value="" key="0" />
                {attributes
                  ? attributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="rating-attribute-mappings-ratingAttribute"
                name="ratingAttribute"
                data-cy="ratingAttribute"
                label="Rating Attribute"
                type="select"
                required
              >
                <option value="" key="0" />
                {pcRatingAttributes
                  ? pcRatingAttributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rating-attribute-mappings" replace color="info">
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

export default RatingAttributeMappingsUpdate;
