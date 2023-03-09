import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserAttributeApprovalRules from './user-attribute-approval-rules';
import UserAttributeApprovalRulesDetail from './user-attribute-approval-rules-detail';
import UserAttributeApprovalRulesUpdate from './user-attribute-approval-rules-update';
import UserAttributeApprovalRulesDeleteDialog from './user-attribute-approval-rules-delete-dialog';

const UserAttributeApprovalRulesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserAttributeApprovalRules />} />
    <Route path="new" element={<UserAttributeApprovalRulesUpdate />} />
    <Route path=":id">
      <Route index element={<UserAttributeApprovalRulesDetail />} />
      <Route path="edit" element={<UserAttributeApprovalRulesUpdate />} />
      <Route path="delete" element={<UserAttributeApprovalRulesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserAttributeApprovalRulesRoutes;
