import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedbackResponses from './feedback-responses';
import FeedbackResponsesDetail from './feedback-responses-detail';
import FeedbackResponsesUpdate from './feedback-responses-update';
import FeedbackResponsesDeleteDialog from './feedback-responses-delete-dialog';

const FeedbackResponsesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedbackResponses />} />
    <Route path="new" element={<FeedbackResponsesUpdate />} />
    <Route path=":id">
      <Route index element={<FeedbackResponsesDetail />} />
      <Route path="edit" element={<FeedbackResponsesUpdate />} />
      <Route path="delete" element={<FeedbackResponsesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedbackResponsesRoutes;
