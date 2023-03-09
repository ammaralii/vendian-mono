import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmploymentStatuses from './employment-statuses';
import EmploymentStatusesDetail from './employment-statuses-detail';
import EmploymentStatusesUpdate from './employment-statuses-update';
import EmploymentStatusesDeleteDialog from './employment-statuses-delete-dialog';

const EmploymentStatusesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmploymentStatuses />} />
    <Route path="new" element={<EmploymentStatusesUpdate />} />
    <Route path=":id">
      <Route index element={<EmploymentStatusesDetail />} />
      <Route path="edit" element={<EmploymentStatusesUpdate />} />
      <Route path="delete" element={<EmploymentStatusesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmploymentStatusesRoutes;
