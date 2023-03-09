import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedbackRequests from './feedback-requests';
import FeedbackRequestsDetail from './feedback-requests-detail';
import FeedbackRequestsUpdate from './feedback-requests-update';
import FeedbackRequestsDeleteDialog from './feedback-requests-delete-dialog';

const FeedbackRequestsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedbackRequests />} />
    <Route path="new" element={<FeedbackRequestsUpdate />} />
    <Route path=":id">
      <Route index element={<FeedbackRequestsDetail />} />
      <Route path="edit" element={<FeedbackRequestsUpdate />} />
      <Route path="delete" element={<FeedbackRequestsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedbackRequestsRoutes;
