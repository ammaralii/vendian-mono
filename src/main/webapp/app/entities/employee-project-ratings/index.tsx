import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeProjectRatings from './employee-project-ratings';
import EmployeeProjectRatingsDetail from './employee-project-ratings-detail';
import EmployeeProjectRatingsUpdate from './employee-project-ratings-update';
import EmployeeProjectRatingsDeleteDialog from './employee-project-ratings-delete-dialog';

const EmployeeProjectRatingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeProjectRatings />} />
    <Route path="new" element={<EmployeeProjectRatingsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeProjectRatingsDetail />} />
      <Route path="edit" element={<EmployeeProjectRatingsUpdate />} />
      <Route path="delete" element={<EmployeeProjectRatingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeProjectRatingsRoutes;
