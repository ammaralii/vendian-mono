import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRatingCategories } from 'app/shared/model/rating-categories.model';
import { getEntities as getRatingCategories } from 'app/entities/rating-categories/rating-categories.reducer';
import { IPcRatingAttributes } from 'app/shared/model/pc-rating-attributes.model';
import { getEntities as getPcRatingAttributes } from 'app/entities/pc-rating-attributes/pc-rating-attributes.reducer';
import { IPcRatingAttributesCategories } from 'app/shared/model/pc-rating-attributes-categories.model';
import { getEntity, updateEntity, createEntity, reset } from './pc-rating-attributes-categories.reducer';

export const PcRatingAttributesCategoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ratingCategories = useAppSelector(state => state.ratingCategories.entities);
  const pcRatingAttributes = useAppSelector(state => state.pcRatingAttributes.entities);
  const pcRatingAttributesCategoriesEntity = useAppSelector(state => state.pcRatingAttributesCategories.entity);
  const loading = useAppSelector(state => state.pcRatingAttributesCategories.loading);
  const updating = useAppSelector(state => state.pcRatingAttributesCategories.updating);
  const updateSuccess = useAppSelector(state => state.pcRatingAttributesCategories.updateSuccess);

  const handleClose = () => {
    navigate('/pc-rating-attributes-categories' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRatingCategories({}));
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
      ...pcRatingAttributesCategoriesEntity,
      ...values,
      category: ratingCategories.find(it => it.id.toString() === values.category.toString()),
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
          ...pcRatingAttributesCategoriesEntity,
          effDate: convertDateTimeFromServer(pcRatingAttributesCategoriesEntity.effDate),
          createdAt: convertDateTimeFromServer(pcRatingAttributesCategoriesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(pcRatingAttributesCategoriesEntity.updatedAt),
          endDate: convertDateTimeFromServer(pcRatingAttributesCategoriesEntity.endDate),
          category: pcRatingAttributesCategoriesEntity?.category?.id,
          ratingAttribute: pcRatingAttributesCategoriesEntity?.ratingAttribute?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="vendianMonoApp.pcRatingAttributesCategories.home.createOrEditLabel"
            data-cy="PcRatingAttributesCategoriesCreateUpdateHeading"
          >
            Create or edit a Pc Rating Attributes Categories
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
                  id="pc-rating-attributes-categories-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Eff Date"
                id="pc-rating-attributes-categories-effDate"
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
                id="pc-rating-attributes-categories-createdAt"
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
                id="pc-rating-attributes-categories-updatedAt"
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
                id="pc-rating-attributes-categories-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Version"
                id="pc-rating-attributes-categories-version"
                name="version"
                data-cy="version"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                id="pc-rating-attributes-categories-category"
                name="category"
                data-cy="category"
                label="Category"
                type="select"
                required
              >
                <option value="" key="0" />
                {ratingCategories
                  ? ratingCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="pc-rating-attributes-categories-ratingAttribute"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/pc-rating-attributes-categories"
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

export default PcRatingAttributesCategoriesUpdate;
