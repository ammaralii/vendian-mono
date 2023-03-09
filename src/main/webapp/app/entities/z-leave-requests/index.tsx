import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ZLeaveRequests from './z-leave-requests';
import ZLeaveRequestsDetail from './z-leave-requests-detail';
import ZLeaveRequestsUpdate from './z-leave-requests-update';
import ZLeaveRequestsDeleteDialog from './z-leave-requests-delete-dialog';

const ZLeaveRequestsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ZLeaveRequests />} />
    <Route path="new" element={<ZLeaveRequestsUpdate />} />
    <Route path=":id">
      <Route index element={<ZLeaveRequestsDetail />} />
      <Route path="edit" element={<ZLeaveRequestsUpdate />} />
      <Route path="delete" element={<ZLeaveRequestsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZLeaveRequestsRoutes;
