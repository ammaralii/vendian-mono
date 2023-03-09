import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BloodGroups from './blood-groups';
import BloodGroupsDetail from './blood-groups-detail';
import BloodGroupsUpdate from './blood-groups-update';
import BloodGroupsDeleteDialog from './blood-groups-delete-dialog';

const BloodGroupsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BloodGroups />} />
    <Route path="new" element={<BloodGroupsUpdate />} />
    <Route path=":id">
      <Route index element={<BloodGroupsDetail />} />
      <Route path="edit" element={<BloodGroupsUpdate />} />
      <Route path="delete" element={<BloodGroupsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BloodGroupsRoutes;
