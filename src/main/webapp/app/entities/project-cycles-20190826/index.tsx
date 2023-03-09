import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectCycles20190826 from './project-cycles-20190826';
import ProjectCycles20190826Detail from './project-cycles-20190826-detail';
import ProjectCycles20190826Update from './project-cycles-20190826-update';
import ProjectCycles20190826DeleteDialog from './project-cycles-20190826-delete-dialog';

const ProjectCycles20190826Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectCycles20190826 />} />
    <Route path="new" element={<ProjectCycles20190826Update />} />
    <Route path=":id">
      <Route index element={<ProjectCycles20190826Detail />} />
      <Route path="edit" element={<ProjectCycles20190826Update />} />
      <Route path="delete" element={<ProjectCycles20190826DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectCycles20190826Routes;
