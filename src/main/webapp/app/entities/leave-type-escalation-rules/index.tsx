import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveTypeEscalationRules from './leave-type-escalation-rules';
import LeaveTypeEscalationRulesDetail from './leave-type-escalation-rules-detail';
import LeaveTypeEscalationRulesUpdate from './leave-type-escalation-rules-update';
import LeaveTypeEscalationRulesDeleteDialog from './leave-type-escalation-rules-delete-dialog';

const LeaveTypeEscalationRulesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveTypeEscalationRules />} />
    <Route path="new" element={<LeaveTypeEscalationRulesUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveTypeEscalationRulesDetail />} />
      <Route path="edit" element={<LeaveTypeEscalationRulesUpdate />} />
      <Route path="delete" element={<LeaveTypeEscalationRulesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveTypeEscalationRulesRoutes;
