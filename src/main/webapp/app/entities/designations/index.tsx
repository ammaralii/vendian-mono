import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Designations from './designations';
import DesignationsDetail from './designations-detail';
import DesignationsUpdate from './designations-update';
import DesignationsDeleteDialog from './designations-delete-dialog';

const DesignationsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Designations />} />
    <Route path="new" element={<DesignationsUpdate />} />
    <Route path=":id">
      <Route index element={<DesignationsDetail />} />
      <Route path="edit" element={<DesignationsUpdate />} />
      <Route path="delete" element={<DesignationsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DesignationsRoutes;
