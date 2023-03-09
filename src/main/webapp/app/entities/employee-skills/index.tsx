import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeSkills from './employee-skills';
import EmployeeSkillsDetail from './employee-skills-detail';
import EmployeeSkillsUpdate from './employee-skills-update';
import EmployeeSkillsDeleteDialog from './employee-skills-delete-dialog';

const EmployeeSkillsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeSkills />} />
    <Route path="new" element={<EmployeeSkillsUpdate />} />
    <Route path=":id">
      <Route index element={<EmployeeSkillsDetail />} />
      <Route path="edit" element={<EmployeeSkillsUpdate />} />
      <Route path="delete" element={<EmployeeSkillsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeSkillsRoutes;
