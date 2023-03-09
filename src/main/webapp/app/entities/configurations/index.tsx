import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Configurations from './configurations';
import ConfigurationsDetail from './configurations-detail';
import ConfigurationsUpdate from './configurations-update';
import ConfigurationsDeleteDialog from './configurations-delete-dialog';

const ConfigurationsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Configurations />} />
    <Route path="new" element={<ConfigurationsUpdate />} />
    <Route path=":id">
      <Route index element={<ConfigurationsDetail />} />
      <Route path="edit" element={<ConfigurationsUpdate />} />
      <Route path="delete" element={<ConfigurationsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConfigurationsRoutes;
