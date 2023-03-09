import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocuments } from 'app/shared/model/documents.model';
import { getEntities as getDocuments } from 'app/entities/documents/documents.reducer';
import { IEmployees } from 'app/shared/model/employees.model';
import { getEntities as getEmployees } from 'app/entities/employees/employees.reducer';
import { IEmployeeComments } from 'app/shared/model/employee-comments.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-comments.reducer';

export const EmployeeCommentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documents = useAppSelector(state => state.documents.entities);
  const employees = useAppSelector(state => state.employees.entities);
  const employeeCommentsEntity = useAppSelector(state => state.employeeComments.entity);
  const loading = useAppSelector(state => state.employeeComments.loading);
  const updating = useAppSelector(state => state.employeeComments.updating);
  const updateSuccess = useAppSelector(state => state.employeeComments.updateSuccess);

  const handleClose = () => {
    navigate('/employee-comments' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDocuments({}));
    dispatch(getEmployees({}));
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
      ...employeeCommentsEntity,
      ...values,
      document: documents.find(it => it.id.toString() === values.document.toString()),
      commenter: employees.find(it => it.id.toString() === values.commenter.toString()),
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
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...employeeCommentsEntity,
          createdat: convertDateTimeFromServer(employeeCommentsEntity.createdat),
          updatedat: convertDateTimeFromServer(employeeCommentsEntity.updatedat),
          document: employeeCommentsEntity?.document?.id,
          commenter: employeeCommentsEntity?.commenter?.id,
          employee: employeeCommentsEntity?.employee?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.employeeComments.home.createOrEditLabel" data-cy="EmployeeCommentsCreateUpdateHeading">
            Create or edit a Employee Comments
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
                <ValidatedField name="id" required readOnly id="employee-comments-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField
                label="Title"
                id="employee-comments-title"
                name="title"
                data-cy="title"
                openActionLabel="Open"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedBlobField
                label="Content"
                id="employee-comments-content"
                name="content"
                data-cy="content"
                openActionLabel="Open"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedBlobField
                label="Dated"
                id="employee-comments-dated"
                name="dated"
                data-cy="dated"
                openActionLabel="Open"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Createdat"
                id="employee-comments-createdat"
                name="createdat"
                data-cy="createdat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Updatedat"
                id="employee-comments-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="employee-comments-document" name="document" data-cy="document" label="Document" type="select">
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-comments-commenter"
                name="commenter"
                data-cy="commenter"
                label="Commenter"
                type="select"
                required
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="employee-comments-employee" name="employee" data-cy="employee" label="Employee" type="select" required>
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-comments" replace color="info">
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

export default EmployeeCommentsUpdate;
