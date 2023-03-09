import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealResourceStatus from './deal-resource-status';
import DealResourceStatusDetail from './deal-resource-status-detail';
import DealResourceStatusUpdate from './deal-resource-status-update';
import DealResourceStatusDeleteDialog from './deal-resource-status-delete-dialog';

const DealResourceStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealResourceStatus />} />
    <Route path="new" element={<DealResourceStatusUpdate />} />
    <Route path=":id">
      <Route index element={<DealResourceStatusDetail />} />
      <Route path="edit" element={<DealResourceStatusUpdate />} />
      <Route path="delete" element={<DealResourceStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealResourceStatusRoutes;
