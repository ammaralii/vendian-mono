import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QuestionsProcessingEventLogs from './questions-processing-event-logs';
import QuestionsProcessingEventLogsDetail from './questions-processing-event-logs-detail';
import QuestionsProcessingEventLogsUpdate from './questions-processing-event-logs-update';
import QuestionsProcessingEventLogsDeleteDialog from './questions-processing-event-logs-delete-dialog';

const QuestionsProcessingEventLogsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QuestionsProcessingEventLogs />} />
    <Route path="new" element={<QuestionsProcessingEventLogsUpdate />} />
    <Route path=":id">
      <Route index element={<QuestionsProcessingEventLogsDetail />} />
      <Route path="edit" element={<QuestionsProcessingEventLogsUpdate />} />
      <Route path="delete" element={<QuestionsProcessingEventLogsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QuestionsProcessingEventLogsRoutes;
