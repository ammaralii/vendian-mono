import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PerformanceCycles from './performance-cycles';
import PerformanceCyclesDetail from './performance-cycles-detail';
import PerformanceCyclesUpdate from './performance-cycles-update';
import PerformanceCyclesDeleteDialog from './performance-cycles-delete-dialog';

const PerformanceCyclesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PerformanceCycles />} />
    <Route path="new" element={<PerformanceCyclesUpdate />} />
    <Route path=":id">
      <Route index element={<PerformanceCyclesDetail />} />
      <Route path="edit" element={<PerformanceCyclesUpdate />} />
      <Route path="delete" element={<PerformanceCyclesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PerformanceCyclesRoutes;
