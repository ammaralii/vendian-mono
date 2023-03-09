import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserRelationApprovalRules from './user-relation-approval-rules';
import UserRelationApprovalRulesDetail from './user-relation-approval-rules-detail';
import UserRelationApprovalRulesUpdate from './user-relation-approval-rules-update';
import UserRelationApprovalRulesDeleteDialog from './user-relation-approval-rules-delete-dialog';

const UserRelationApprovalRulesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserRelationApprovalRules />} />
    <Route path="new" element={<UserRelationApprovalRulesUpdate />} />
    <Route path=":id">
      <Route index element={<UserRelationApprovalRulesDetail />} />
      <Route path="edit" element={<UserRelationApprovalRulesUpdate />} />
      <Route path="delete" element={<UserRelationApprovalRulesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserRelationApprovalRulesRoutes;
