import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectLeadership from './project-leadership';
import ProjectLeadershipDetail from './project-leadership-detail';
import ProjectLeadershipUpdate from './project-leadership-update';
import ProjectLeadershipDeleteDialog from './project-leadership-delete-dialog';

const ProjectLeadershipRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectLeadership />} />
    <Route path="new" element={<ProjectLeadershipUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectLeadershipDetail />} />
      <Route path="edit" element={<ProjectLeadershipUpdate />} />
      <Route path="delete" element={<ProjectLeadershipDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectLeadershipRoutes;
