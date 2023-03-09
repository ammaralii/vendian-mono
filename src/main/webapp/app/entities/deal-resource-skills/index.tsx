import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DealResourceSkills from './deal-resource-skills';
import DealResourceSkillsDetail from './deal-resource-skills-detail';
import DealResourceSkillsUpdate from './deal-resource-skills-update';
import DealResourceSkillsDeleteDialog from './deal-resource-skills-delete-dialog';

const DealResourceSkillsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DealResourceSkills />} />
    <Route path="new" element={<DealResourceSkillsUpdate />} />
    <Route path=":id">
      <Route index element={<DealResourceSkillsDetail />} />
      <Route path="edit" element={<DealResourceSkillsUpdate />} />
      <Route path="delete" element={<DealResourceSkillsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DealResourceSkillsRoutes;
