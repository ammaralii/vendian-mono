import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDealResources } from 'app/shared/model/deal-resources.model';
import { getEntities as getDealResources } from 'app/entities/deal-resources/deal-resources.reducer';
import { ISkills } from 'app/shared/model/skills.model';
import { getEntities as getSkills } from 'app/entities/skills/skills.reducer';
import { IDealResourceSkills } from 'app/shared/model/deal-resource-skills.model';
import { getEntity, updateEntity, createEntity, reset } from './deal-resource-skills.reducer';

export const DealResourceSkillsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dealResources = useAppSelector(state => state.dealResources.entities);
  const skills = useAppSelector(state => state.skills.entities);
  const dealResourceSkillsEntity = useAppSelector(state => state.dealResourceSkills.entity);
  const loading = useAppSelector(state => state.dealResourceSkills.loading);
  const updating = useAppSelector(state => state.dealResourceSkills.updating);
  const updateSuccess = useAppSelector(state => state.dealResourceSkills.updateSuccess);

  const handleClose = () => {
    navigate('/deal-resource-skills' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDealResources({}));
    dispatch(getSkills({}));
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
      ...dealResourceSkillsEntity,
      ...values,
      resource: dealResources.find(it => it.id.toString() === values.resource.toString()),
      skill: skills.find(it => it.id.toString() === values.skill.toString()),
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
          ...dealResourceSkillsEntity,
          createdat: convertDateTimeFromServer(dealResourceSkillsEntity.createdat),
          updatedat: convertDateTimeFromServer(dealResourceSkillsEntity.updatedat),
          deletedat: convertDateTimeFromServer(dealResourceSkillsEntity.deletedat),
          resource: dealResourceSkillsEntity?.resource?.id,
          skill: dealResourceSkillsEntity?.skill?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.dealResourceSkills.home.createOrEditLabel" data-cy="DealResourceSkillsCreateUpdateHeading">
            Create or edit a Deal Resource Skills
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
                <ValidatedField name="id" required readOnly id="deal-resource-skills-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Createdat"
                id="deal-resource-skills-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="deal-resource-skills-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Deletedat"
                id="deal-resource-skills-deletedat"
                name="deletedat"
                data-cy="deletedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="deal-resource-skills-resource" name="resource" data-cy="resource" label="Resource" type="select">
                <option value="" key="0" />
                {dealResources
                  ? dealResources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="deal-resource-skills-skill" name="skill" data-cy="skill" label="Skill" type="select">
                <option value="" key="0" />
                {skills
                  ? skills.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deal-resource-skills" replace color="info">
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

export default DealResourceSkillsUpdate;
