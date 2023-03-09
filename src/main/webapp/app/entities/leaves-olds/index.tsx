import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeavesOlds from './leaves-olds';
import LeavesOldsDetail from './leaves-olds-detail';
import LeavesOldsUpdate from './leaves-olds-update';
import LeavesOldsDeleteDialog from './leaves-olds-delete-dialog';

const LeavesOldsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeavesOlds />} />
    <Route path="new" element={<LeavesOldsUpdate />} />
    <Route path=":id">
      <Route index element={<LeavesOldsDetail />} />
      <Route path="edit" element={<LeavesOldsUpdate />} />
      <Route path="delete" element={<LeavesOldsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeavesOldsRoutes;
