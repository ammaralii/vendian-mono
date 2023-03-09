import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypes from './leave-types';
import LeaveTypesDetail from './leave-types-detail';
import LeaveTypesUpdate from './leave-types-update';
import LeaveTypesDeleteDialog from './leave-types-delete-dialog';

const LeaveTypesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypes />} />
    <Route path="new" element={<LeaveTypesUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypesDetail />} />
      <Route path="edit" element={<LeaveTypesUpdate />} />
      <Route path="delete" element={<LeaveTypesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypesRoutes;
