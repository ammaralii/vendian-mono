import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Universities from './universities';
import UniversitiesDetail from './universities-detail';
import UniversitiesUpdate from './universities-update';
import UniversitiesDeleteDialog from './universities-delete-dialog';

const UniversitiesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Universities />} />
    <Route path="new" element={<UniversitiesUpdate />} />
    <Route path=":id">
      <Route index element={<UniversitiesDetail />} />
      <Route path="edit" element={<UniversitiesUpdate />} />
      <Route path="delete" element={<UniversitiesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UniversitiesRoutes;
