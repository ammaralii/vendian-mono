import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveDetails from './leave-details';
import LeaveDetailsDetail from './leave-details-detail';
import LeaveDetailsUpdate from './leave-details-update';
import LeaveDetailsDeleteDialog from './leave-details-delete-dialog';

const LeaveDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveDetails />} />
    <Route path="new" element={<LeaveDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveDetailsDetail />} />
      <Route path="edit" element={<LeaveDetailsUpdate />} />
      <Route path="delete" element={<LeaveDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveDetailsRoutes;
