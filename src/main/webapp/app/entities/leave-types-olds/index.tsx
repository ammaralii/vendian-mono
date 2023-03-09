import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypesOlds from './leave-types-olds';
import LeaveTypesOldsDetail from './leave-types-olds-detail';
import LeaveTypesOldsUpdate from './leave-types-olds-update';
import LeaveTypesOldsDeleteDialog from './leave-types-olds-delete-dialog';

const LeaveTypesOldsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypesOlds />} />
    <Route path="new" element={<LeaveTypesOldsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypesOldsDetail />} />
      <Route path="edit" element={<LeaveTypesOldsUpdate />} />
      <Route path="delete" element={<LeaveTypesOldsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypesOldsRoutes;
