import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PerformanceCycleEmployeeRating from './performance-cycle-employee-rating';
import PerformanceCycleEmployeeRatingDetail from './performance-cycle-employee-rating-detail';
import PerformanceCycleEmployeeRatingUpdate from './performance-cycle-employee-rating-update';
import PerformanceCycleEmployeeRatingDeleteDialog from './performance-cycle-employee-rating-delete-dialog';

const PerformanceCycleEmployeeRatingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PerformanceCycleEmployeeRating />} />
    <Route path="new" element={<PerformanceCycleEmployeeRatingUpdate />} />
    <Route path=":id">
      <Route index element={<PerformanceCycleEmployeeRatingDetail />} />
      <Route path="edit" element={<PerformanceCycleEmployeeRatingUpdate />} />
      <Route path="delete" element={<PerformanceCycleEmployeeRatingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PerformanceCycleEmployeeRatingRoutes;
