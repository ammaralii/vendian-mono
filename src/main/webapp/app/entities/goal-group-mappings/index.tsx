import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GoalGroupMappings from './goal-group-mappings';
import GoalGroupMappingsDetail from './goal-group-mappings-detail';
import GoalGroupMappingsUpdate from './goal-group-mappings-update';
import GoalGroupMappingsDeleteDialog from './goal-group-mappings-delete-dialog';

const GoalGroupMappingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GoalGroupMappings />} />
    <Route path="new" element={<GoalGroupMappingsUpdate />} />
    <Route path=":id">
      <Route index element={<GoalGroupMappingsDetail />} />
      <Route path="edit" element={<GoalGroupMappingsUpdate />} />
      <Route path="delete" element={<GoalGroupMappingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GoalGroupMappingsRoutes;
