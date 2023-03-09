import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ZEmployeeProjects from './z-employee-projects';
import ZEmployeeProjectsDetail from './z-employee-projects-detail';
import ZEmployeeProjectsUpdate from './z-employee-projects-update';
import ZEmployeeProjectsDeleteDialog from './z-employee-projects-delete-dialog';

const ZEmployeeProjectsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ZEmployeeProjects />} />
    <Route path="new" element={<ZEmployeeProjectsUpdate />} />
    <Route path=":id">
      <Route index element={<ZEmployeeProjectsDetail />} />
      <Route path="edit" element={<ZEmployeeProjectsUpdate />} />
      <Route path="delete" element={<ZEmployeeProjectsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZEmployeeProjectsRoutes;
