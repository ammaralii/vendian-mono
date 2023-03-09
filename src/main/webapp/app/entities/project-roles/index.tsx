import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectRoles from './project-roles';
import ProjectRolesDetail from './project-roles-detail';
import ProjectRolesUpdate from './project-roles-update';
import ProjectRolesDeleteDialog from './project-roles-delete-dialog';

const ProjectRolesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectRoles />} />
    <Route path="new" element={<ProjectRolesUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectRolesDetail />} />
      <Route path="edit" element={<ProjectRolesUpdate />} />
      <Route path="delete" element={<ProjectRolesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectRolesRoutes;
