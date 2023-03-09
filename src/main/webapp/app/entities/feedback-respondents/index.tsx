import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedbackRespondents from './feedback-respondents';
import FeedbackRespondentsDetail from './feedback-respondents-detail';
import FeedbackRespondentsUpdate from './feedback-respondents-update';
import FeedbackRespondentsDeleteDialog from './feedback-respondents-delete-dialog';

const FeedbackRespondentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedbackRespondents />} />
    <Route path="new" element={<FeedbackRespondentsUpdate />} />
    <Route path=":id">
      <Route index element={<FeedbackRespondentsDetail />} />
      <Route path="edit" element={<FeedbackRespondentsUpdate />} />
      <Route path="delete" element={<FeedbackRespondentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedbackRespondentsRoutes;
