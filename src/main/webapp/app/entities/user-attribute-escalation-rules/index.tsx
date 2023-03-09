import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserAttributeEscalationRules from './user-attribute-escalation-rules';
import UserAttributeEscalationRulesDetail from './user-attribute-escalation-rules-detail';
import UserAttributeEscalationRulesUpdate from './user-attribute-escalation-rules-update';
import UserAttributeEscalationRulesDeleteDialog from './user-attribute-escalation-rules-delete-dialog';

const UserAttributeEscalationRulesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserAttributeEscalationRules />} />
    <Route path="new" element={<UserAttributeEscalationRulesUpdate />} />
    <Route path=":id">
      <Route index element={<UserAttributeEscalationRulesDetail />} />
      <Route path="edit" element={<UserAttributeEscalationRulesUpdate />} />
      <Route path="delete" element={<UserAttributeEscalationRulesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserAttributeEscalationRulesRoutes;
