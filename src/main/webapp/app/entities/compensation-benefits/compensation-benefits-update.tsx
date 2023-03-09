import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBenefits } from 'app/shared/model/benefits.model';
import { getEntities as getBenefits } from 'app/entities/benefits/benefits.reducer';
import { IEmployeeCompensation } from 'app/shared/model/employee-compensation.model';
import { getEntities as getEmployeeCompensations } from 'app/entities/employee-compensation/employee-compensation.reducer';
import { ICompensationBenefits } from 'app/shared/model/compensation-benefits.model';
import { getEntity, updateEntity, createEntity, reset } from './compensation-benefits.reducer';

export const CompensationBenefitsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const benefits = useAppSelector(state => state.benefits.entities);
  const employeeCompensations = useAppSelector(state => state.employeeCompensation.entities);
  const compensationBenefitsEntity = useAppSelector(state => state.compensationBenefits.entity);
  const loading = useAppSelector(state => state.compensationBenefits.loading);
  const updating = useAppSelector(state => state.compensationBenefits.updating);
  const updateSuccess = useAppSelector(state => state.compensationBenefits.updateSuccess);

  const handleClose = () => {
    navigate('/compensation-benefits' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBenefits({}));
    dispatch(getEmployeeCompensations({}));
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
      ...compensationBenefitsEntity,
      ...values,
      benefit: benefits.find(it => it.id.toString() === values.benefit.toString()),
      employeecompensation: employeeCompensations.find(it => it.id.toString() === values.employeecompensation.toString()),
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
          ...compensationBenefitsEntity,
          createdat: convertDateTimeFromServer(compensationBenefitsEntity.createdat),
          updatedat: convertDateTimeFromServer(compensationBenefitsEntity.updatedat),
          benefit: compensationBenefitsEntity?.benefit?.id,
          employeecompensation: compensationBenefitsEntity?.employeecompensation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.compensationBenefits.home.createOrEditLabel" data-cy="CompensationBenefitsCreateUpdateHeading">
            Create or edit a Compensation Benefits
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
                <ValidatedField name="id" required readOnly id="compensation-benefits-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Amount" id="compensation-benefits-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField
                label="Isdeleted"
                id="compensation-benefits-isdeleted"
                name="isdeleted"
                data-cy="isdeleted"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Createdat"
                id="compensation-benefits-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="compensation-benefits-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="compensation-benefits-benefit" name="benefit" data-cy="benefit" label="Benefit" type="select" required>
                <option value="" key="0" />
                {benefits
                  ? benefits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="compensation-benefits-employeecompensation"
                name="employeecompensation"
                data-cy="employeecompensation"
                label="Employeecompensation"
                type="select"
                required
              >
                <option value="" key="0" />
                {employeeCompensations
                  ? employeeCompensations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/compensation-benefits" replace color="info">
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

export default CompensationBenefitsUpdate;
