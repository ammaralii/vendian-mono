import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeaveEscalationCriterias from './leave-escalation-criterias';
import LeaveEscalationCriteriasDetail from './leave-escalation-criterias-detail';
import LeaveEscalationCriteriasUpdate from './leave-escalation-criterias-update';
import LeaveEscalationCriteriasDeleteDialog from './leave-escalation-criterias-delete-dialog';

const LeaveEscalationCriteriasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeaveEscalationCriterias />} />
    <Route path="new" element={<LeaveEscalationCriteriasUpdate />} />
    <Route path=":id">
      <Route index element={<LeaveEscalationCriteriasDetail />} />
      <Route path="edit" element={<LeaveEscalationCriteriasUpdate />} />
      <Route path="delete" element={<LeaveEscalationCriteriasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeaveEscalationCriteriasRoutes;
