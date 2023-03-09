import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeProjects from './employee-projects';
import EmployeeProjectsDetail from './employee-projects-detail';
import EmployeeProjectsUpdate from './employee-projects-update';
import EmployeeProjectsDeleteDialog from './employee-projects-delete-dialog';

const EmployeeProjectsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeProjects />} />
    <Route path="new" element={<EmployeeProjectsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeProjectsDetail />} />
      <Route path="edit" element={<EmployeeProjectsUpdate />} />
      <Route path="delete" element={<EmployeeProjectsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeProjectsRoutes;
