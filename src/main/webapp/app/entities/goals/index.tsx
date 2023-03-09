import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Goals from './goals';
import GoalsDetail from './goals-detail';
import GoalsUpdate from './goals-update';
import GoalsDeleteDialog from './goals-delete-dialog';

const GoalsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Goals />} />
    <Route path="new" element={<GoalsUpdate />} />
    <Route path=":id">
      <Route index element={<GoalsDetail />} />
      <Route path="edit" element={<GoalsUpdate />} />
      <Route path="delete" element={<GoalsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GoalsRoutes;
