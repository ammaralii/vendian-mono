import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserGoals from './user-goals';
import UserGoalsDetail from './user-goals-detail';
import UserGoalsUpdate from './user-goals-update';
import UserGoalsDeleteDialog from './user-goals-delete-dialog';

const UserGoalsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserGoals />} />
    <Route path="new" element={<UserGoalsUpdate />} />
    <Route path=":id">
      <Route index element={<UserGoalsDetail />} />
      <Route path="edit" element={<UserGoalsUpdate />} />
      <Route path="delete" element={<UserGoalsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserGoalsRoutes;
