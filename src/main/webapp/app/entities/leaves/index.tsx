import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leaves from './leaves';
import LeavesDetail from './leaves-detail';
import LeavesUpdate from './leaves-update';
import LeavesDeleteDialog from './leaves-delete-dialog';

const LeavesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leaves />} />
    <Route path="new" element={<LeavesUpdate />} />
    <Route path=":id">
      <Route index element={<LeavesDetail />} />
      <Route path="edit" element={<LeavesUpdate />} />
      <Route path="delete" element={<LeavesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeavesRoutes;
