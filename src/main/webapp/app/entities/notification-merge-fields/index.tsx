import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NotificationMergeFields from './notification-merge-fields';
import NotificationMergeFieldsDetail from './notification-merge-fields-detail';
import NotificationMergeFieldsUpdate from './notification-merge-fields-update';
import NotificationMergeFieldsDeleteDialog from './notification-merge-fields-delete-dialog';

const NotificationMergeFieldsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NotificationMergeFields />} />
    <Route path="new" element={<NotificationMergeFieldsUpdate />} />
    <Route path=":id">
      <Route index element={<NotificationMergeFieldsDetail />} />
      <Route path="edit" element={<NotificationMergeFieldsUpdate />} />
      <Route path="delete" element={<NotificationMergeFieldsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotificationMergeFieldsRoutes;
