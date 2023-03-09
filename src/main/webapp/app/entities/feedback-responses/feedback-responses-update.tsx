import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';
import { getEntities as getFeedbackRespondents } from 'app/entities/feedback-respondents/feedback-respondents.reducer';
import { IFeedbackQuestions } from 'app/shared/model/feedback-questions.model';
import { getEntities as getFeedbackQuestions } from 'app/entities/feedback-questions/feedback-questions.reducer';
import { IFeedbackResponses } from 'app/shared/model/feedback-responses.model';
import { getEntity, updateEntity, createEntity, reset } from './feedback-responses.reducer';

export const FeedbackResponsesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const feedbackRespondents = useAppSelector(state => state.feedbackRespondents.entities);
  const feedbackQuestions = useAppSelector(state => state.feedbackQuestions.entities);
  const feedbackResponsesEntity = useAppSelector(state => state.feedbackResponses.entity);
  const loading = useAppSelector(state => state.feedbackResponses.loading);
  const updating = useAppSelector(state => state.feedbackResponses.updating);
  const updateSuccess = useAppSelector(state => state.feedbackResponses.updateSuccess);

  const handleClose = () => {
    navigate('/feedback-responses' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFeedbackRespondents({}));
    dispatch(getFeedbackQuestions({}));
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
      ...feedbackResponsesEntity,
      ...values,
      respondent: feedbackRespondents.find(it => it.id.toString() === values.respondent.toString()),
      question: feedbackQuestions.find(it => it.id.toString() === values.question.toString()),
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
          ...feedbackResponsesEntity,
          createdat: convertDateTimeFromServer(feedbackResponsesEntity.createdat),
          updatedat: convertDateTimeFromServer(feedbackResponsesEntity.updatedat),
          respondent: feedbackResponsesEntity?.respondent?.id,
          question: feedbackResponsesEntity?.question?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="vendianMonoApp.feedbackResponses.home.createOrEditLabel" data-cy="FeedbackResponsesCreateUpdateHeading">
            Create or edit a Feedback Responses
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
                <ValidatedField name="id" required readOnly id="feedback-responses-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedBlobField label="Answer" id="feedback-responses-answer" name="answer" data-cy="answer" openActionLabel="Open" />
              <ValidatedBlobField label="Rating" id="feedback-responses-rating" name="rating" data-cy="rating" openActionLabel="Open" />
              <ValidatedField
                label="Createdat"
                id="feedback-responses-createdat"
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
                id="feedback-responses-updatedat"
                name="updatedat"
                data-cy="updatedat"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField id="feedback-responses-respondent" name="respondent" data-cy="respondent" label="Respondent" type="select">
                <option value="" key="0" />
                {feedbackRespondents
                  ? feedbackRespondents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="feedback-responses-question" name="question" data-cy="question" label="Question" type="select">
                <option value="" key="0" />
                {feedbackQuestions
                  ? feedbackQuestions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/feedback-responses" replace color="info">
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

export default FeedbackResponsesUpdate;
