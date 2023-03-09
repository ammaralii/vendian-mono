import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeavesCopy } from 'app/shared/model/leaves-copy.model';
import { getEntity, updateEntity, createEntity, reset } from './leaves-copy.reducer';

export const LeavesCopyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leavesCopyEntity = useAppSelector(state => state.leavesCopy.entity);
  const loading = useAppSelector(state => state.leavesCopy.loading);
  const updating = useAppSelector(state => state.leavesCopy.updating);
  const updateSuccess = useAppSelector(state => state.leavesCopy.updateSuccess);

  const handleClose = () => {
    navigate('/leaves-copy' + location.search);
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
      ...leavesCopyEntity,
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
          ...leavesCopyEntity,
          createdat: convertDateTimeFromServer(leavesCopyEntity.createdat),
          updatedat: convertDateTimeFromServer(leavesCopyEntity.updatedat),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leavesCopy.home.createOrEditLabel" data-cy="LeavesCopyCreateUpdateHeading">
            Create or edit a Leaves Copy
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="leaves-copy-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Annualtotal" id="leaves-copy-annualtotal" name="annualtotal" data-cy="annualtotal" type="text" />
              <ValidatedField label="Annualused" id="leaves-copy-annualused" name="annualused" data-cy="annualused" type="text" />
              <ValidatedField
                label="Annualadjustments"
                id="leaves-copy-annualadjustments"
                name="annualadjustments"
                data-cy="annualadjustments"
                type="text"
              />
              <ValidatedField label="Casualtotal" id="leaves-copy-casualtotal" name="casualtotal" data-cy="casualtotal" type="text" />
              <ValidatedField label="Casualused" id="leaves-copy-casualused" name="casualused" data-cy="casualused" type="text" />
              <ValidatedField label="Sicktotal" id="leaves-copy-sicktotal" name="sicktotal" data-cy="sicktotal" type="text" />
              <ValidatedField label="Sickused" id="leaves-copy-sickused" name="sickused" data-cy="sickused" type="text" />
              <ValidatedField
                label="Annualtotalnextyear"
                id="leaves-copy-annualtotalnextyear"
                name="annualtotalnextyear"
                data-cy="annualtotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Annualusednextyear"
                id="leaves-copy-annualusednextyear"
                name="annualusednextyear"
                data-cy="annualusednextyear"
                type="text"
              />
              <ValidatedField
                label="Casualtotalnextyear"
                id="leaves-copy-casualtotalnextyear"
                name="casualtotalnextyear"
                data-cy="casualtotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Casualusednextyear"
                id="leaves-copy-casualusednextyear"
                name="casualusednextyear"
                data-cy="casualusednextyear"
                type="text"
              />
              <ValidatedField
                label="Sicktotalnextyear"
                id="leaves-copy-sicktotalnextyear"
                name="sicktotalnextyear"
                data-cy="sicktotalnextyear"
                type="text"
              />
              <ValidatedField
                label="Sickusednextyear"
                id="leaves-copy-sickusednextyear"
                name="sickusednextyear"
                data-cy="sickusednextyear"
                type="text"
              />
              <ValidatedField label="Carryforward" id="leaves-copy-carryforward" name="carryforward" data-cy="carryforward" type="text" />
              <ValidatedField
                label="Createdat"
                id="leaves-copy-createdat"
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
                id="leaves-copy-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leaves-copy" replace color="info">
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

export default LeavesCopyUpdate;
