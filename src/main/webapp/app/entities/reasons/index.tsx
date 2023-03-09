import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reasons from './reasons';
import ReasonsDetail from './reasons-detail';
import ReasonsUpdate from './reasons-update';
import ReasonsDeleteDialog from './reasons-delete-dialog';

const ReasonsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reasons />} />
    <Route path="new" element={<ReasonsUpdate />} />
    <Route path=":id">
      <Route index element={<ReasonsDetail />} />
      <Route path="edit" element={<ReasonsUpdate />} />
      <Route path="delete" element={<ReasonsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReasonsRoutes;
