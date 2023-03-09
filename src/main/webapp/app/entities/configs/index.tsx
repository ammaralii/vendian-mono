import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Configs from './configs';
import ConfigsDetail from './configs-detail';
import ConfigsUpdate from './configs-update';
import ConfigsDeleteDialog from './configs-delete-dialog';

const ConfigsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Configs />} />
    <Route path="new" element={<ConfigsUpdate />} />
    <Route path=":id">
      <Route index element={<ConfigsDetail />} />
      <Route path="edit" element={<ConfigsUpdate />} />
      <Route path="delete" element={<ConfigsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConfigsRoutes;
