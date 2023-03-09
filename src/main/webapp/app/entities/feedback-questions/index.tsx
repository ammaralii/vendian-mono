import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedbackQuestions from './feedback-questions';
import FeedbackQuestionsDetail from './feedback-questions-detail';
import FeedbackQuestionsUpdate from './feedback-questions-update';
import FeedbackQuestionsDeleteDialog from './feedback-questions-delete-dialog';

const FeedbackQuestionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedbackQuestions />} />
    <Route path="new" element={<FeedbackQuestionsUpdate />} />
    <Route path=":id">
      <Route index element={<FeedbackQuestionsDetail />} />
      <Route path="edit" element={<FeedbackQuestionsUpdate />} />
      <Route path="delete" element={<FeedbackQuestionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedbackQuestionsRoutes;
