import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealResources from './deal-resources';
import DealResourcesDetail from './deal-resources-detail';
import DealResourcesUpdate from './deal-resources-update';
import DealResourcesDeleteDialog from './deal-resources-delete-dialog';

const DealResourcesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealResources />} />
    <Route path="new" element={<DealResourcesUpdate />} />
    <Route path=":id">
      <Route index element={<DealResourcesDetail />} />
      <Route path="edit" element={<DealResourcesUpdate />} />
      <Route path="delete" element={<DealResourcesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealResourcesRoutes;
