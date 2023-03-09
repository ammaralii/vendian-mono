import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeJobInfo from './employee-job-info';
import EmployeeJobInfoDetail from './employee-job-info-detail';
import EmployeeJobInfoUpdate from './employee-job-info-update';
import EmployeeJobInfoDeleteDialog from './employee-job-info-delete-dialog';

const EmployeeJobInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeJobInfo />} />
    <Route path="new" element={<EmployeeJobInfoUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeJobInfoDetail />} />
      <Route path="edit" element={<EmployeeJobInfoUpdate />} />
      <Route path="delete" element={<EmployeeJobInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeJobInfoRoutes;
