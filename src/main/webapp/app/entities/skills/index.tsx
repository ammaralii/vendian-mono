import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Skills from './skills';
import SkillsDetail from './skills-detail';
import SkillsUpdate from './skills-update';
import SkillsDeleteDialog from './skills-delete-dialog';

const SkillsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Skills />} />
    <Route path="new" element={<SkillsUpdate />} />
    <Route path=":id">
      <Route index element={<SkillsDetail />} />
      <Route path="edit" element={<SkillsUpdate />} />
      <Route path="delete" element={<SkillsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SkillsRoutes;
