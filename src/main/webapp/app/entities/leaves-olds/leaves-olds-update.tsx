import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeavesOlds } from 'app/shared/model/leaves-olds.model';
import { getEntity, updateEntity, createEntity, reset } from './leaves-olds.reducer';

export const LeavesOldsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leavesOldsEntity = useAppSelector(state => state.leavesOlds.entity);
  const loading = useAppSelector(state => state.leavesOlds.loading);
  const updating = useAppSelector(state => state.leavesOlds.updating);
  const updateSuccess = useAppSelector(state => state.leavesOlds.updateSuccess);

  const handleClose = () => {
    navigate('/leaves-olds' + location.search);
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
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...leavesOldsEntity,
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...leavesOldsEntity,
          createdat: convertDateTimeFromServer(leavesOldsEntity.createdat),
          updatedat: convertDateTimeFromServer(leavesOldsEntity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leavesOlds.home.createOrEditLabel" data-cy="LeavesOldsCreateUpdateHeading">
            Create or edit a Leaves Olds
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="leaves-olds-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Annualtotal" id="leaves-olds-annualtotal" name="annualtotal" data-cy="annualtotal" type="text" />
              <ValidatedField label="Annualused" id="leaves-olds-annualused" name="annualused" data-cy="annualused" type="text" />
              <ValidatedField
                label="Annualadjustments"
                id="leaves-olds-annualadjustments"
                name="annualadjustments"
                data-cy="annualadjustments"
                type="text"
              />
              <ValidatedField label="Casualtotal" id="leaves-olds-casualtotal" name="casualtotal" data-cy="casualtotal" type="text" />
              <ValidatedField label="Casualused" id="leaves-olds-casualused" name="casualused" data-cy="casualused" type="text" />
              <ValidatedField label="Sicktotal" id="leaves-olds-sicktotal" name="sicktotal" data-cy="sicktotal" type="text" />
              <ValidatedField label="Sickused" id="leaves-olds-sickused" name="sickused" data-cy="sickused" type="text" />
              <ValidatedField
                label="Annualtotalnextyear"
                id="leaves-olds-annualtotalnextyear"
                name="annualtotalnextyear"
                data-cy="annualtotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Annualusednextyear"
                id="leaves-olds-annualusednextyear"
                name="annualusednextyear"
                data-cy="annualusednextyear"
                type="text"
              />
              <ValidatedField
                label="Casualtotalnextyear"
                id="leaves-olds-casualtotalnextyear"
                name="casualtotalnextyear"
                data-cy="casualtotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Casualusednextyear"
                id="leaves-olds-casualusednextyear"
                name="casualusednextyear"
                data-cy="casualusednextyear"
                type="text"
              />
              <ValidatedField
                label="Sicktotalnextyear"
                id="leaves-olds-sicktotalnextyear"
                name="sicktotalnextyear"
                data-cy="sicktotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Sickusednextyear"
                id="leaves-olds-sickusednextyear"
                name="sickusednextyear"
                data-cy="sickusednextyear"
                type="text"
              />
              <ValidatedField label="Carryforward" id="leaves-olds-carryforward" name="carryforward" data-cy="carryforward" type="text" />
              <ValidatedField
                label="Createdat"
                id="leaves-olds-createdat"
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
                id="leaves-olds-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leaves-olds" replace color="info">
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

export default LeavesOldsUpdate;
