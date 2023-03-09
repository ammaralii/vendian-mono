import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HrPerformanceCycles from './hr-performance-cycles';
import HrPerformanceCyclesDetail from './hr-performance-cycles-detail';
import HrPerformanceCyclesUpdate from './hr-performance-cycles-update';
import HrPerformanceCyclesDeleteDialog from './hr-performance-cycles-delete-dialog';

const HrPerformanceCyclesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HrPerformanceCycles />} />
    <Route path="new" element={<HrPerformanceCyclesUpdate />} />
    <Route path=":id">
      <Route index element={<HrPerformanceCyclesDetail />} />
      <Route path="edit" element={<HrPerformanceCyclesUpdate />} />
      <Route path="delete" element={<HrPerformanceCyclesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HrPerformanceCyclesRoutes;
