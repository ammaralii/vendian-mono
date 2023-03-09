import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GoalGroups from './goal-groups';
import GoalGroupsDetail from './goal-groups-detail';
import GoalGroupsUpdate from './goal-groups-update';
import GoalGroupsDeleteDialog from './goal-groups-delete-dialog';

const GoalGroupsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GoalGroups />} />
    <Route path="new" element={<GoalGroupsUpdate />} />
    <Route path=":id">
      <Route index element={<GoalGroupsDetail />} />
      <Route path="edit" element={<GoalGroupsUpdate />} />
      <Route path="delete" element={<GoalGroupsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GoalGroupsRoutes;
