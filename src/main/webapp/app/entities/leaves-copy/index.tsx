import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeavesCopy from './leaves-copy';
import LeavesCopyDetail from './leaves-copy-detail';
import LeavesCopyUpdate from './leaves-copy-update';
import LeavesCopyDeleteDialog from './leaves-copy-delete-dialog';

const LeavesCopyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeavesCopy />} />
    <Route path="new" element={<LeavesCopyUpdate />} />
    <Route path=":id">
      <Route index element={<LeavesCopyDetail />} />
      <Route path="edit" element={<LeavesCopyUpdate />} />
      <Route path="delete" element={<LeavesCopyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeavesCopyRoutes;
