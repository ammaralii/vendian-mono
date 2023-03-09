import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkLogDetails from './work-log-details';
import WorkLogDetailsDetail from './work-log-details-detail';
import WorkLogDetailsUpdate from './work-log-details-update';
import WorkLogDetailsDeleteDialog from './work-log-details-delete-dialog';

const WorkLogDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkLogDetails />} />
    <Route path="new" element={<WorkLogDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<WorkLogDetailsDetail />} />
      <Route path="edit" element={<WorkLogDetailsUpdate />} />
      <Route path="delete" element={<WorkLogDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkLogDetailsRoutes;
