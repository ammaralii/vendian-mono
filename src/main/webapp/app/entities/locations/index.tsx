import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locations from './locations';
import LocationsDetail from './locations-detail';
import LocationsUpdate from './locations-update';
import LocationsDeleteDialog from './locations-delete-dialog';

const LocationsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locations />} />
    <Route path="new" element={<LocationsUpdate />} />
    <Route path=":id">
      <Route index element={<LocationsDetail />} />
      <Route path="edit" element={<LocationsUpdate />} />
      <Route path="delete" element={<LocationsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocationsRoutes;
