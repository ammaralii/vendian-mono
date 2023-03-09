import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FeedbackEmails from './feedback-emails';
import FeedbackEmailsDetail from './feedback-emails-detail';
import FeedbackEmailsUpdate from './feedback-emails-update';
import FeedbackEmailsDeleteDialog from './feedback-emails-delete-dialog';

const FeedbackEmailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FeedbackEmails />} />
    <Route path="new" element={<FeedbackEmailsUpdate />} />
    <Route path=":id">
      <Route index element={<FeedbackEmailsDetail />} />
      <Route path="edit" element={<FeedbackEmailsUpdate />} />
      <Route path="delete" element={<FeedbackEmailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FeedbackEmailsRoutes;
