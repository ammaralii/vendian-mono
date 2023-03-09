import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Competencies from './competencies';
import CompetenciesDetail from './competencies-detail';
import CompetenciesUpdate from './competencies-update';
import CompetenciesDeleteDialog from './competencies-delete-dialog';

const CompetenciesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Competencies />} />
    <Route path="new" element={<CompetenciesUpdate />} />
    <Route path=":id">
      <Route index element={<CompetenciesDetail />} />
      <Route path="edit" element={<CompetenciesUpdate />} />
      <Route path="delete" element={<CompetenciesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetenciesRoutes;
