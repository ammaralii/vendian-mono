import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaterAttributes } from 'app/shared/model/rater-attributes.model';
import { getEntities as getRaterAttributes } from 'app/entities/rater-attributes/rater-attributes.reducer';
import { IAttributes } from 'app/shared/model/attributes.model';
import { getEntities as getAttributes } from 'app/entities/attributes/attributes.reducer';
import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';
import { getEntity, updateEntity, createEntity, reset } from './rater-attribute-mappings.reducer';

export const RaterAttributeMappingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const raterAttributes = useAppSelector(state => state.raterAttributes.entities);
  const attributes = useAppSelector(state => state.attributes.entities);
  const raterAttributeMappingsEntity = useAppSelector(state => state.raterAttributeMappings.entity);
  const loading = useAppSelector(state => state.raterAttributeMappings.loading);
  const updating = useAppSelector(state => state.raterAttributeMappings.updating);
  const updateSuccess = useAppSelector(state => state.raterAttributeMappings.updateSuccess);

  const handleClose = () => {
    navigate('/rater-attribute-mappings' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRaterAttributes({}));
    dispatch(getAttributes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.effdate = convertDateTimeToServer(values.effdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...raterAttributeMappingsEntity,
      ...values,
      raterattribute: raterAttributes.find(it => it.id.toString() === values.raterattribute.toString()),
      attributesFor: attributes.find(it => it.id.toString() === values.attributesFor.toString()),
      attributesBy: attributes.find(it => it.id.toString() === values.attributesBy.toString()),
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
          effdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...raterAttributeMappingsEntity,
          effdate: convertDateTimeFromServer(raterAttributeMappingsEntity.effdate),
          enddate: convertDateTimeFromServer(raterAttributeMappingsEntity.enddate),
          createdat: convertDateTimeFromServer(raterAttributeMappingsEntity.createdat),
          updatedat: convertDateTimeFromServer(raterAttributeMappingsEntity.updatedat),
          raterattribute: raterAttributeMappingsEntity?.raterattribute?.id,
          attributesFor: raterAttributeMappingsEntity?.attributesFor?.id,
          attributesBy: raterAttributeMappingsEntity?.attributesBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.raterAttributeMappings.home.createOrEditLabel" data-cy="RaterAttributeMappingsCreateUpdateHeading">
            Create or edit a Rater Attribute Mappings
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
                <ValidatedField name="id" required readOnly id="rater-attribute-mappings-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Effdate"
                id="rater-attribute-mappings-effdate"
                name="effdate"
                data-cy="effdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="rater-attribute-mappings-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="rater-attribute-mappings-createdat"
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
                id="rater-attribute-mappings-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                id="rater-attribute-mappings-raterattribute"
                name="raterattribute"
                data-cy="raterattribute"
                label="Raterattribute"
                type="select"
              >
                <option value="" key="0" />
                {raterAttributes
                  ? raterAttributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="rater-attribute-mappings-attributesFor"
                name="attributesFor"
                data-cy="attributesFor"
                label="Attributes For"
                type="select"
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
              <ValidatedField
                id="rater-attribute-mappings-attributesBy"
                name="attributesBy"
                data-cy="attributesBy"
                label="Attributes By"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rater-attribute-mappings" replace color="info">
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

export default RaterAttributeMappingsUpdate;
