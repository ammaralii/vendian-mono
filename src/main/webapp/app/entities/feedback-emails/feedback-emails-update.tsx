import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';
import { getEntities as getFeedbackRespondents } from 'app/entities/feedback-respondents/feedback-respondents.reducer';
import { IFeedbackEmails } from 'app/shared/model/feedback-emails.model';
import { getEntity, updateEntity, createEntity, reset } from './feedback-emails.reducer';

export const FeedbackEmailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const feedbackRespondents = useAppSelector(state => state.feedbackRespondents.entities);
  const feedbackEmailsEntity = useAppSelector(state => state.feedbackEmails.entity);
  const loading = useAppSelector(state => state.feedbackEmails.loading);
  const updating = useAppSelector(state => state.feedbackEmails.updating);
  const updateSuccess = useAppSelector(state => state.feedbackEmails.updateSuccess);

  const handleClose = () => {
    navigate('/feedback-emails' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFeedbackRespondents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.sentat = convertDateTimeToServer(values.sentat);
    values.createdat = convertDateTimeToServer(values.createdat);
    values.updatedat = convertDateTimeToServer(values.updatedat);

    const entity = {
      ...feedbackEmailsEntity,
      ...values,
      respondent: feedbackRespondents.find(it => it.id.toString() === values.respondent.toString()),
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
          sentat: displayDefaultDateTime(),
          createdat: displayDefaultDateTime(),
          updatedat: displayDefaultDateTime(),
        }
      : {
          ...feedbackEmailsEntity,
          sentat: convertDateTimeFromServer(feedbackEmailsEntity.sentat),
          createdat: convertDateTimeFromServer(feedbackEmailsEntity.createdat),
          updatedat: convertDateTimeFromServer(feedbackEmailsEntity.updatedat),
          respondent: feedbackEmailsEntity?.respondent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.feedbackEmails.home.createOrEditLabel" data-cy="FeedbackEmailsCreateUpdateHeading">
            Create or edit a Feedback Emails
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
                <ValidatedField name="id" required readOnly id="feedback-emails-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="To"
                id="feedback-emails-to"
                name="to"
                data-cy="to"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField
                label="Body"
                id="feedback-emails-body"
                name="body"
                data-cy="body"
                type="text"
                validate={{
                  maxLength: { value: 65535, message: 'This field cannot be longer than 65535 characters.' },
                }}
              />
              <ValidatedField label="Status" id="feedback-emails-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Sentat"
                id="feedback-emails-sentat"
                name="sentat"
                data-cy="sentat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Createdat"
                id="feedback-emails-createdat"
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
                id="feedback-emails-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="feedback-emails-respondent" name="respondent" data-cy="respondent" label="Respondent" type="select">
                <option value="" key="0" />
                {feedbackRespondents
                  ? feedbackRespondents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/feedback-emails" replace color="info">
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

export default FeedbackEmailsUpdate;
