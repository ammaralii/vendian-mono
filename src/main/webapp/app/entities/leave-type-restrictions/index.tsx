import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypeRestrictions from './leave-type-restrictions';
import LeaveTypeRestrictionsDetail from './leave-type-restrictions-detail';
import LeaveTypeRestrictionsUpdate from './leave-type-restrictions-update';
import LeaveTypeRestrictionsDeleteDialog from './leave-type-restrictions-delete-dialog';

const LeaveTypeRestrictionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypeRestrictions />} />
    <Route path="new" element={<LeaveTypeRestrictionsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypeRestrictionsDetail />} />
      <Route path="edit" element={<LeaveTypeRestrictionsUpdate />} />
      <Route path="delete" element={<LeaveTypeRestrictionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypeRestrictionsRoutes;
