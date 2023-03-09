import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocuments } from 'app/shared/model/documents.model';
import { getEntities as getDocuments } from 'app/entities/documents/documents.reducer';
import { IDesignations } from 'app/shared/model/designations.model';
import { getEntities as getDesignations } from 'app/entities/designations/designations.reducer';
import { IDesignationJobDescriptions } from 'app/shared/model/designation-job-descriptions.model';
import { getEntity, updateEntity, createEntity, reset } from './designation-job-descriptions.reducer';

export const DesignationJobDescriptionsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documents = useAppSelector(state => state.documents.entities);
  const designations = useAppSelector(state => state.designations.entities);
  const designationJobDescriptionsEntity = useAppSelector(state => state.designationJobDescriptions.entity);
  const loading = useAppSelector(state => state.designationJobDescriptions.loading);
  const updating = useAppSelector(state => state.designationJobDescriptions.updating);
  const updateSuccess = useAppSelector(state => state.designationJobDescriptions.updateSuccess);

  const handleClose = () => {
    navigate('/designation-job-descriptions' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDocuments({}));
    dispatch(getDesignations({}));
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
      ...designationJobDescriptionsEntity,
      ...values,
      document: documents.find(it => it.id.toString() === values.document.toString()),
      designation: designations.find(it => it.id.toString() === values.designation.toString()),
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
          ...designationJobDescriptionsEntity,
          createdat: convertDateTimeFromServer(designationJobDescriptionsEntity.createdat),
          updatedat: convertDateTimeFromServer(designationJobDescriptionsEntity.updatedat),
          document: designationJobDescriptionsEntity?.document?.id,
          designation: designationJobDescriptionsEntity?.designation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.designationJobDescriptions.home.createOrEditLabel" data-cy="DesignationJobDescriptionsCreateUpdateHeading">
            Create or edit a Designation Job Descriptions
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
                <ValidatedField name="id" required readOnly id="designation-job-descriptions-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="designation-job-descriptions-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="designation-job-descriptions-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="designation-job-descriptions-document"
                name="document"
                data-cy="document"
                label="Document"
                type="select"
                required
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="designation-job-descriptions-designation"
                name="designation"
                data-cy="designation"
                label="Designation"
                type="select"
                required
              >
                <option value="" key="0" />
                {designations
                  ? designations.map(otherEntity => (
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
                to="/designation-job-descriptions"
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

export default DesignationJobDescriptionsUpdate;
