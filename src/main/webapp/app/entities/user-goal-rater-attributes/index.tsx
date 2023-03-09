import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserGoalRaterAttributes from './user-goal-rater-attributes';
import UserGoalRaterAttributesDetail from './user-goal-rater-attributes-detail';
import UserGoalRaterAttributesUpdate from './user-goal-rater-attributes-update';
import UserGoalRaterAttributesDeleteDialog from './user-goal-rater-attributes-delete-dialog';

const UserGoalRaterAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserGoalRaterAttributes />} />
    <Route path="new" element={<UserGoalRaterAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<UserGoalRaterAttributesDetail />} />
      <Route path="edit" element={<UserGoalRaterAttributesUpdate />} />
      <Route path="delete" element={<UserGoalRaterAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserGoalRaterAttributesRoutes;
