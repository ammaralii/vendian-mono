import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRatings } from 'app/shared/model/ratings.model';
import { getEntities as getRatings } from 'app/entities/ratings/ratings.reducer';
import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';
import { getEntities as getRaterAttributeMappings } from 'app/entities/rater-attribute-mappings/rater-attribute-mappings.reducer';
import { IRatingAttributes } from 'app/shared/model/rating-attributes.model';
import { getEntity, updateEntity, createEntity, reset } from './rating-attributes.reducer';

export const RatingAttributesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ratings = useAppSelector(state => state.ratings.entities);
  const raterAttributeMappings = useAppSelector(state => state.raterAttributeMappings.entities);
  const ratingAttributesEntity = useAppSelector(state => state.ratingAttributes.entity);
  const loading = useAppSelector(state => state.ratingAttributes.loading);
  const updating = useAppSelector(state => state.ratingAttributes.updating);
  const updateSuccess = useAppSelector(state => state.ratingAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/rating-attributes' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRatings({}));
    dispatch(getRaterAttributeMappings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...ratingAttributesEntity,
      ...values,
      rating: ratings.find(it => it.id.toString() === values.rating.toString()),
      raterattributemapping: raterAttributeMappings.find(it => it.id.toString() === values.raterattributemapping.toString()),
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
        }
      : {
          ...ratingAttributesEntity,
          createdat: convertDateTimeFromServer(ratingAttributesEntity.createdat),
          updatedat: convertDateTimeFromServer(ratingAttributesEntity.updatedat),
          rating: ratingAttributesEntity?.rating?.id,
          raterattributemapping: ratingAttributesEntity?.raterattributemapping?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.ratingAttributes.home.createOrEditLabel" data-cy="RatingAttributesCreateUpdateHeading">
            Create or edit a Rating Attributes
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
                <ValidatedField name="id" required readOnly id="rating-attributes-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField
                label="Ra Rating"
                id="rating-attributes-raRating"
                name="raRating"
                data-cy="raRating"
                openActionLabel="Open"
              />
              <ValidatedBlobField label="Comment" id="rating-attributes-comment" name="comment" data-cy="comment" openActionLabel="Open" />
              <ValidatedField
                label="Createdat"
                id="rating-attributes-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updatedat"
                id="rating-attributes-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="rating-attributes-rating" name="rating" data-cy="rating" label="Rating" type="select">
                <option value="" key="0" />
                {ratings
                  ? ratings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="rating-attributes-raterattributemapping"
                name="raterattributemapping"
                data-cy="raterattributemapping"
                label="Raterattributemapping"
                type="select"
              >
                <option value="" key="0" />
                {raterAttributeMappings
                  ? raterAttributeMappings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rating-attributes" replace color="info">
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

export default RatingAttributesUpdate;
