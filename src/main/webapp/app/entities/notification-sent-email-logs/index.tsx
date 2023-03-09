import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NotificationSentEmailLogs from './notification-sent-email-logs';
import NotificationSentEmailLogsDetail from './notification-sent-email-logs-detail';
import NotificationSentEmailLogsUpdate from './notification-sent-email-logs-update';
import NotificationSentEmailLogsDeleteDialog from './notification-sent-email-logs-delete-dialog';

const NotificationSentEmailLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NotificationSentEmailLogs />} />
    <Route path="new" element={<NotificationSentEmailLogsUpdate />} />
    <Route path=":id">
      <Route index element={<NotificationSentEmailLogsDetail />} />
      <Route path="edit" element={<NotificationSentEmailLogsUpdate />} />
      <Route path="delete" element={<NotificationSentEmailLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotificationSentEmailLogsRoutes;
