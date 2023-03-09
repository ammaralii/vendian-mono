import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NotificationTemplates from './notification-templates';
import NotificationTemplatesDetail from './notification-templates-detail';
import NotificationTemplatesUpdate from './notification-templates-update';
import NotificationTemplatesDeleteDialog from './notification-templates-delete-dialog';

const NotificationTemplatesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NotificationTemplates />} />
    <Route path="new" element={<NotificationTemplatesUpdate />} />
    <Route path=":id">
      <Route index element={<NotificationTemplatesDetail />} />
      <Route path="edit" element={<NotificationTemplatesUpdate />} />
      <Route path="delete" element={<NotificationTemplatesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotificationTemplatesRoutes;
