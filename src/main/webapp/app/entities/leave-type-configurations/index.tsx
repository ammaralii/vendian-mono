import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypeConfigurations from './leave-type-configurations';
import LeaveTypeConfigurationsDetail from './leave-type-configurations-detail';
import LeaveTypeConfigurationsUpdate from './leave-type-configurations-update';
import LeaveTypeConfigurationsDeleteDialog from './leave-type-configurations-delete-dialog';

const LeaveTypeConfigurationsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypeConfigurations />} />
    <Route path="new" element={<LeaveTypeConfigurationsUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypeConfigurationsDetail />} />
      <Route path="edit" element={<LeaveTypeConfigurationsUpdate />} />
      <Route path="delete" element={<LeaveTypeConfigurationsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypeConfigurationsRoutes;
