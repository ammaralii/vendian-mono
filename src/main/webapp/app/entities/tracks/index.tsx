import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tracks from './tracks';
import TracksDetail from './tracks-detail';
import TracksUpdate from './tracks-update';
import TracksDeleteDialog from './tracks-delete-dialog';

const TracksRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tracks />} />
    <Route path="new" element={<TracksUpdate />} />
    <Route path=":id">
      <Route index element={<TracksDetail />} />
      <Route path="edit" element={<TracksUpdate />} />
      <Route path="delete" element={<TracksDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TracksRoutes;
