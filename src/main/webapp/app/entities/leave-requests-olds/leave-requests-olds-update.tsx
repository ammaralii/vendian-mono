import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeaveTypesOlds } from 'app/shared/model/leave-types-olds.model';
import { getEntities as getLeaveTypesOlds } from 'app/entities/leave-types-olds/leave-types-olds.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { ILeaveRequestsOlds } from 'app/shared/model/leave-requests-olds.model';
import { getEntity, updateEntity, createEntity, reset } from './leave-requests-olds.reducer';

export const LeaveRequestsOldsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leaveTypesOlds = useAppSelector(state => state.leaveTypesOlds.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const leaveRequestsOldsEntity = useAppSelector(state => state.leaveRequestsOlds.entity);
  const loading = useAppSelector(state => state.leaveRequestsOlds.loading);
  const updating = useAppSelector(state => state.leaveRequestsOlds.updating);
  const updateSuccess = useAppSelector(state => state.leaveRequestsOlds.updateSuccess);

  const handleClose = () => {
    navigate('/leave-requests-olds' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeaveTypesOlds({}));
    dispatch(getEmployees({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startdate = convertDateTimeToServer(values.startdate);
    values.enddate = convertDateTimeToServer(values.enddate);
    values.requestdate = convertDateTimeToServer(values.requestdate);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);
    values.actiondate = convertDateTimeToServer(values.actiondate);

    const entity = {
      ...leaveRequestsOldsEntity,
      ...values,
      leavetype: leaveTypesOlds.find(it => it.id.toString() === values.leavetype.toString()),
      manager: employees.find(it => it.id.toString() === values.manager.toString()),
      employee: employees.find(it => it.id.toString() === values.employee.toString()),
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
          startdate: displayDefaultDateTime(),
          enddate: displayDefaultDateTime(),
          requestdate: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
          actiondate: displayDefaultDateTime(),
        }
      : {
          ...leaveRequestsOldsEntity,
          startdate: convertDateTimeFromServer(leaveRequestsOldsEntity.startdate),
          enddate: convertDateTimeFromServer(leaveRequestsOldsEntity.enddate),
          requestdate: convertDateTimeFromServer(leaveRequestsOldsEntity.requestdate),
          createdat: convertDateTimeFromServer(leaveRequestsOldsEntity.createdat),
          updatedat: convertDateTimeFromServer(leaveRequestsOldsEntity.updatedat),
          actiondate: convertDateTimeFromServer(leaveRequestsOldsEntity.actiondate),
          leavetype: leaveRequestsOldsEntity?.leavetype?.id,
          manager: leaveRequestsOldsEntity?.manager?.id,
          employee: leaveRequestsOldsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.leaveRequestsOlds.home.createOrEditLabel" data-cy="LeaveRequestsOldsCreateUpdateHeading">
            Create or edit a Leave Requests Olds
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
                <ValidatedField name="id" required readOnly id="leave-requests-olds-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Startdate"
                id="leave-requests-olds-startdate"
                name="startdate"
                data-cy="startdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Enddate"
                id="leave-requests-olds-enddate"
                name="enddate"
                data-cy="enddate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Requesternote"
                id="leave-requests-olds-requesternote"
                name="requesternote"
                data-cy="requesternote"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Managernote"
                id="leave-requests-olds-managernote"
                name="managernote"
                data-cy="managernote"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Currentstatus"
                id="leave-requests-olds-currentstatus"
                name="currentstatus"
                data-cy="currentstatus"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField
                label="Leavescanceled"
                id="leave-requests-olds-leavescanceled"
                name="leavescanceled"
                data-cy="leavescanceled"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Requestdate"
                id="leave-requests-olds-requestdate"
                name="requestdate"
                data-cy="requestdate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Linkstring"
                id="leave-requests-olds-linkstring"
                name="linkstring"
                data-cy="linkstring"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Linkused" id="leave-requests-olds-linkused" name="linkused" data-cy="linkused" check type="checkbox" />
              <ValidatedField
                label="Createdat"
                id="leave-requests-olds-createdat"
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
                id="leave-requests-olds-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Ishalfday"
                id="leave-requests-olds-ishalfday"
                name="ishalfday"
                data-cy="ishalfday"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Actiondate"
                id="leave-requests-olds-actiondate"
                name="actiondate"
                data-cy="actiondate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Lmstatus"
                id="leave-requests-olds-lmstatus"
                name="lmstatus"
                data-cy="lmstatus"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'This field cannot be longer than 255 characters.' },
                }}
              />
              <ValidatedField label="Pmstatus" id="leave-requests-olds-pmstatus" name="pmstatus" data-cy="pmstatus" type="text" />
              <ValidatedField label="Isbench" id="leave-requests-olds-isbench" name="isbench" data-cy="isbench" check type="checkbox" />
              <ValidatedField
                label="Isescalated"
                id="leave-requests-olds-isescalated"
                name="isescalated"
                data-cy="isescalated"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Iscommented"
                id="leave-requests-olds-iscommented"
                name="iscommented"
                data-cy="iscommented"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Isreminded"
                id="leave-requests-olds-isreminded"
                name="isreminded"
                data-cy="isreminded"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Isnotified"
                id="leave-requests-olds-isnotified"
                name="isnotified"
                data-cy="isnotified"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Isremindedhr"
                id="leave-requests-olds-isremindedhr"
                name="isremindedhr"
                data-cy="isremindedhr"
                check
                type="checkbox"
              />
              <ValidatedField label="Isdm" id="leave-requests-olds-isdm" name="isdm" data-cy="isdm" check type="checkbox" />
              <ValidatedField id="leave-requests-olds-leavetype" name="leavetype" data-cy="leavetype" label="Leavetype" type="select">
                <option value="" key="0" />
                {leaveTypesOlds
                  ? leaveTypesOlds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="leave-requests-olds-manager" name="manager" data-cy="manager" label="Manager" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="leave-requests-olds-employee" name="employee" data-cy="employee" label="Employee" type="select">
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leave-requests-olds" replace color="info">
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

export default LeaveRequestsOldsUpdate;
