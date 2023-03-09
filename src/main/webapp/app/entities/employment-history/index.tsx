import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmploymentHistory from './employment-history';
import EmploymentHistoryDetail from './employment-history-detail';
import EmploymentHistoryUpdate from './employment-history-update';
import EmploymentHistoryDeleteDialog from './employment-history-delete-dialog';

const EmploymentHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmploymentHistory />} />
    <Route path="new" element={<EmploymentHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<EmploymentHistoryDetail />} />
      <Route path="edit" element={<EmploymentHistoryUpdate />} />
      <Route path="delete" element={<EmploymentHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmploymentHistoryRoutes;
