import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcRatings from './pc-ratings';
import PcRatingsDetail from './pc-ratings-detail';
import PcRatingsUpdate from './pc-ratings-update';
import PcRatingsDeleteDialog from './pc-ratings-delete-dialog';

const PcRatingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcRatings />} />
    <Route path="new" element={<PcRatingsUpdate />} />
    <Route path=":id">
      <Route index element={<PcRatingsDetail />} />
      <Route path="edit" element={<PcRatingsUpdate />} />
      <Route path="delete" element={<PcRatingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcRatingsRoutes;
