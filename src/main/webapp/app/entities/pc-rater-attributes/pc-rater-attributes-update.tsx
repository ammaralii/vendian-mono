import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRatingAttributeMappings } from 'app/shared/model/rating-attribute-mappings.model';
import { getEntities as getRatingAttributeMappings } from 'app/entities/rating-attribute-mappings/rating-attribute-mappings.reducer';
import { IRatingOptions } from 'app/shared/model/rating-options.model';
import { getEntities as getRatingOptions } from 'app/entities/rating-options/rating-options.reducer';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { getEntities as getPcRatings } from 'app/entities/pc-ratings/pc-ratings.reducer';
import { IPcRaterAttributes } from 'app/shared/model/pc-rater-attributes.model';
import { getEntity, updateEntity, createEntity, reset } from './pc-rater-attributes.reducer';

export const PcRaterAttributesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ratingAttributeMappings = useAppSelector(state => state.ratingAttributeMappings.entities);
  const ratingOptions = useAppSelector(state => state.ratingOptions.entities);
  const pcRatings = useAppSelector(state => state.pcRatings.entities);
  const pcRaterAttributesEntity = useAppSelector(state => state.pcRaterAttributes.entity);
  const loading = useAppSelector(state => state.pcRaterAttributes.loading);
  const updating = useAppSelector(state => state.pcRaterAttributes.updating);
  const updateSuccess = useAppSelector(state => state.pcRaterAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/pc-rater-attributes' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRatingAttributeMappings({}));
    dispatch(getRatingOptions({}));
    dispatch(getPcRatings({}));
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
      ...pcRaterAttributesEntity,
      ...values,
      ratingAttributeMapping: ratingAttributeMappings.find(it => it.id.toString() === values.ratingAttributeMapping.toString()),
      ratingOption: ratingOptions.find(it => it.id.toString() === values.ratingOption.toString()),
      rating: pcRatings.find(it => it.id.toString() === values.rating.toString()),
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
          ...pcRaterAttributesEntity,
          createdAt: convertDateTimeFromServer(pcRaterAttributesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(pcRaterAttributesEntity.updatedAt),
          deletedAt: convertDateTimeFromServer(pcRaterAttributesEntity.deletedAt),
          ratingAttributeMapping: pcRaterAttributesEntity?.ratingAttributeMapping?.id,
          ratingOption: pcRaterAttributesEntity?.ratingOption?.id,
          rating: pcRaterAttributesEntity?.rating?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.pcRaterAttributes.home.createOrEditLabel" data-cy="PcRaterAttributesCreateUpdateHeading">
            Create or edit a Pc Rater Attributes
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
                <ValidatedField name="id" required readOnly id="pc-rater-attributes-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField
                label="Pc Rating"
                id="pc-rater-attributes-pcRating"
                name="pcRating"
                data-cy="pcRating"
                openActionLabel="Open"
              />
              <ValidatedBlobField
                label="Comment"
                id="pc-rater-attributes-comment"
                name="comment"
                data-cy="comment"
                openActionLabel="Open"
              />
              <ValidatedField
                label="Created At"
                id="pc-rater-attributes-createdAt"
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
                id="pc-rater-attributes-updatedAt"
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
                id="pc-rater-attributes-deletedAt"
                name="deletedAt"
                data-cy="deletedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="pc-rater-attributes-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="pc-rater-attributes-ratingAttributeMapping"
                name="ratingAttributeMapping"
                data-cy="ratingAttributeMapping"
                label="Rating Attribute Mapping"
                type="select"
                required
              >
                <option value="" key="0" />
                {ratingAttributeMappings
                  ? ratingAttributeMappings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="pc-rater-attributes-ratingOption"
                name="ratingOption"
                data-cy="ratingOption"
                label="Rating Option"
                type="select"
              >
                <option value="" key="0" />
                {ratingOptions
                  ? ratingOptions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="pc-rater-attributes-rating" name="rating" data-cy="rating" label="Rating" type="select" required>
                <option value="" key="0" />
                {pcRatings
                  ? pcRatings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pc-rater-attributes" replace color="info">
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

export default PcRaterAttributesUpdate;
