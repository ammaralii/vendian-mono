import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectCycles from './project-cycles';
import ProjectCyclesDetail from './project-cycles-detail';
import ProjectCyclesUpdate from './project-cycles-update';
import ProjectCyclesDeleteDialog from './project-cycles-delete-dialog';

const ProjectCyclesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectCycles />} />
    <Route path="new" element={<ProjectCyclesUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectCyclesDetail />} />
      <Route path="edit" element={<ProjectCyclesUpdate />} />
      <Route path="delete" element={<ProjectCyclesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectCyclesRoutes;
